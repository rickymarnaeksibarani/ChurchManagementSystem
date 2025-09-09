package ChurchManagementSystem.CMS.modules.news.service;

import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.news.dto.NewsDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsRequestDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsResponDto;
import ChurchManagementSystem.CMS.modules.news.entity.NewsEntity;
import ChurchManagementSystem.CMS.modules.news.repository.NewsRepository;
import jakarta.annotation.PostConstruct;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;
    private final String UPLOAD_DIR = "CMS/src/main/resources/upload/images";

    @PostConstruct
    private void init(){
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)){
            try {
                Files.createDirectories(uploadPath);
            }catch (Exception e){
                throw new RuntimeException("Could not initialize upload directory", e);
            }
        }
    }

    private NewsResponDto toNewsRespone(NewsEntity newsEntity){
        return NewsResponDto.builder()
                .id(newsEntity.getId())
                .title(newsEntity.getTitle())
                .content(newsEntity.getContent())
                .category(newsEntity.getCategory())
                .imagePath(newsEntity.getImagePath())
                .build();
    }

    @Transactional(readOnly = true)
    public PaginationUtil<NewsEntity, NewsEntity> getAllNews(Integer page, Integer perPage, NewsRequestDto searchRequest) {
        try {
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
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public NewsResponDto getNewsById(Long id){
        NewsEntity news = newsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        return toNewsRespone(news);
    }

    @Transactional
    public NewsResponDto createNews(NewsDto request, MultipartFile image) throws IOException {
        try {
            NewsEntity news = new NewsEntity();
            NewsEntity payload = newsAppPayload(request, news);
            if (image != null && !image.isEmpty()) {
                String imagePath = saveImage(image);
                payload.setImagePath(imagePath);
            }
            newsRepository.save(payload);
            return toNewsRespone(payload);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public NewsResponDto updateNews(Long id, NewsDto request, MultipartFile image) throws Exception{
        try {
            NewsEntity news = newsRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + " not found"));
            NewsEntity payload = newsAppPayload(request, news);
            if (image != null && !image.isEmpty()) {
                String imagePath = saveImage(image);
                news.setImagePath(imagePath);
            }
            newsRepository.saveAndFlush(payload);
            return toNewsRespone(payload);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @Transactional
    public void deleteById(Long id){
        NewsEntity data = newsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        if (data.getImagePath() != null && !data.getImagePath().isEmpty()) {
            deleteImage(data.getImagePath());
        }
        newsRepository.deleteById(id);
    }

    private NewsEntity newsAppPayload(NewsDto request, NewsEntity news){
        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        news.setCategory(request.getCategory());
        return news;
    }

    //todo: solve news all feature
    private String saveImage(MultipartFile image) throws IOException {
        try {
            String contentType = image.getContentType();
            if (contentType == null || !isValidImageType(contentType)) {
                throw new IllegalArgumentException("Only JPG and PNG files are allowed");
            }
            String generatedString = generateRandomString();
            String originalFilename = Objects.requireNonNull(image.getOriginalFilename()).replace(" ", "_");
            String filename = System.currentTimeMillis() + "_" + generatedString + "_" + originalFilename;
            Path filePath = Paths.get(UPLOAD_DIR, filename);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, image.getBytes());
            return filePath.toString();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private boolean isValidImageType(String contentType) {
        return "image/png".equals(contentType) || "image/jpeg".equals(contentType) || "image/jpg".equals(contentType);
    }

    private String generateRandomString() {
        Random random = new Random();
        return random.ints(97, 123) // ASCII range for lowercase letters
                .limit(11) // Generate an 11-character string
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private void deleteImage(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println("Could not delete file: " + imagePath + " - " + e.getMessage());
        }
    }
}
