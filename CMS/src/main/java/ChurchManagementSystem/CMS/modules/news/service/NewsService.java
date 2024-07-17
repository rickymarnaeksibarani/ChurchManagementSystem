package ChurchManagementSystem.CMS.modules.news.service;

import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.news.dto.ApplicationFileDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsRequestDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsResponDto;
import ChurchManagementSystem.CMS.modules.news.entity.NewsEntity;
import ChurchManagementSystem.CMS.modules.news.repository.NewsRepository;
import ChurchManagementSystem.CMS.modules.storage.StorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.ObjectWriteResponse;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private StorageService storageService;
    @Autowired
    private ObjectMapper objectMapper;
    private final Date date = new Date();
    private final Long time = date.getTime();

    private NewsResponDto toNewsRespone(NewsEntity newsEntity)throws JsonProcessingException{
        List<ApplicationFileDto> img = objectMapper.readValue(newsEntity.getThumbnail(), new TypeReference<>() {});
        return NewsResponDto.builder()
                .title(newsEntity.getTitle())
                .content(newsEntity.getContent())
                .category(newsEntity.getCategory())
                .thumbnail(img)
                .id(newsEntity.getId())
//                .publishDate(newsEntity.getPublishDate())
                .build();
    }

    @Transactional
    public NewsResponDto createNews(NewsDto request) throws Exception{
        boolean existsByTitleName = newsRepository.existsByTitle(request.getTitle());
        if (existsByTitleName){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Title news already exists");
        }
        List<ApplicationFileDto> thumbnail = uploadImage(request.getThumbnail());

        NewsEntity news = new NewsEntity();
        NewsEntity payload = newsAppPayload(request, news, thumbnail);
        newsRepository.save(payload);

        return toNewsRespone(payload);
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
    public NewsResponDto updateNews(Long id, NewsDto request) throws Exception{

        //todo : fixing update image
        List<ApplicationFileDto> thumbnail = uploadImage(request.getThumbnail());

        NewsEntity news = new NewsEntity();
        NewsEntity payload = newsAppPayload(request, news, thumbnail);
        newsRepository.save(payload);

        return toNewsRespone(payload);
    }

    @Transactional
    public void deleteById(Long id) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        NewsEntity data = newsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));

        // Remove old image
        List<ApplicationFileDto> docs = objectMapper.readValue(data.getThumbnail(), new TypeReference<ArrayList<ApplicationFileDto>>() {});
        List<String> docList = docs.stream().map(ApplicationFileDto::getFilename).toList();
        if (!docList.isEmpty()) {
            storageService.deleteAllFileS3(docList);
        }

        newsRepository.deleteById(id);
    }

    private NewsEntity newsAppPayload(NewsDto request, NewsEntity news, List<ApplicationFileDto> thumbnail)throws JsonProcessingException{
        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        news.setCategory(request.getCategory());
        news.setThumbnail(objectMapper.writeValueAsString(thumbnail));
        return news;
    }

    //Upload image
    private List<ApplicationFileDto> uploadImage(List<MultipartFile> thumbnail){
        if (thumbnail == null || thumbnail.isEmpty()) return Collections.emptyList();
        List<ApplicationFileDto> thumbnailPaths = new ArrayList<>();
        String generatedString = genereateRandomString();
        thumbnail.forEach(img ->{
            String contentType = img.getContentType();
            if (!isValidImageType(contentType)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid image format. Only PNG, JPG, and JPEG are allowed.");
            }
            try {
                String imgFileName = System.currentTimeMillis() + generatedString + "_" + Objects.requireNonNull(img.getOriginalFilename()).replace(" ", "_");
                String filePath = LocalDate.now().getYear() + "/img/" + imgFileName;
                ObjectWriteResponse objectWriteResponse = storageService.storeToS3(filePath, img);

                ApplicationFileDto applicationFileDto = new ApplicationFileDto();
                applicationFileDto.setPath(objectWriteResponse.object());
                applicationFileDto.setSize(String.valueOf(img.getSize()));
                applicationFileDto.setMimeType(img.getContentType());
                applicationFileDto.setFilename(img.getOriginalFilename());
                thumbnailPaths.add(applicationFileDto);

            } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        });
        return thumbnailPaths;
    }

    private boolean isValidImageType(String contentType) {
        return "image/png".equals(contentType) || "image/jpeg".equals(contentType) || "image/jpg".equals(contentType);
    }

    private String genereateRandomString(){
        Random random = new Random();
        return random.ints(97, 122+1)
                .limit(11)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
