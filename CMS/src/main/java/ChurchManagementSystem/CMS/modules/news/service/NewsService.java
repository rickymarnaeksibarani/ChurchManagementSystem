package ChurchManagementSystem.CMS.modules.news.service;

import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.news.dto.NewsDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsRequestDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsResponDto;
import ChurchManagementSystem.CMS.modules.news.entity.NewsEntity;
import ChurchManagementSystem.CMS.modules.news.repository.NewsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;
    private final String UPLOAD_DIR = "src/main/resources/upload/image";

    private NewsResponDto toNewsRespone(NewsEntity newsEntity)throws JsonProcessingException{
        return NewsResponDto.builder()
                .title(newsEntity.getTitle())
                .content(newsEntity.getContent())
                .category(newsEntity.getCategory())
                .id(newsEntity.getId())
                .image(newsEntity.getImagePath())
                .build();
    }

    //Getting
    @Transactional(readOnly = true)
    public PaginationUtil<NewsEntity, NewsEntity> getAllNews(Integer page, Integer perPage, NewsRequestDto searchRequest) {
        Specification<NewsEntity> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchRequest.getSearchTerm() != null) {
                predicates.add(
                        builder.like(builder.upper(root.get("title")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%")
                );
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        PageRequest pageRequest = PageRequest.of(page - 1, perPage, Sort.by(Sort.Order.desc("createdAt")));
        Page<NewsEntity> pagedResult = newsRepository.findAll(specification, pageRequest);
        return new PaginationUtil<>(pagedResult, NewsEntity.class);
    }
    public NewsResponDto getNewsById(Long id) throws Exception {
        NewsEntity news = newsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        return toNewsRespone(news);
    }

    @Transactional
    public NewsResponDto createNews(NewsDto request) throws Exception{
        NewsEntity news = new NewsEntity();
        String imagePath = null;

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            imagePath = saveFile(request.getImage());
        }
        NewsEntity payload = newsAppPayload(request, news, imagePath);
//        if (request.getImage() != null && !request.getImage().isEmpty()) {
//            String fileName = saveFile(request.getImage());
//            payload.setImagePath(fileName); // Assuming you have an imagePath field in NewsEntity
//        }
        newsRepository.save(payload);
        return toNewsRespone(payload);
    }

    @Transactional
    public NewsResponDto updateNews(Long id, NewsDto request) throws Exception{
       NewsEntity news = newsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Id " + id + " not found"));

        String imagePath = null; // Initialize imagePath

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            // Delete the old file if necessary
            deleteFile(news.getImagePath());
            imagePath = saveFile(request.getImage()); // Save the new image and get the path
        }
       NewsEntity payload = newsAppPayload(request, news, imagePath);
       newsRepository.saveAndFlush(payload);
       return toNewsRespone(payload);
    }


    @Transactional
    public void deleteById(Long id) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        NewsEntity data = newsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        deleteFile(data.getImagePath());
        newsRepository.deleteById(id);
    }

    private NewsEntity newsAppPayload(NewsDto request, NewsEntity news, String imagePath)throws JsonProcessingException{
        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        news.setCategory(request.getCategory());
        news.setImagePath(imagePath);
        return news;
    }
    private String saveFile(MultipartFile file) throws IOException {
        // Create the directory if it doesn't exist
        java.nio.file.Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save the file
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }

    private void deleteFile(String fileName) throws IOException {
        java.nio.file.Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
        Files.deleteIfExists(filePath);
    }

}
