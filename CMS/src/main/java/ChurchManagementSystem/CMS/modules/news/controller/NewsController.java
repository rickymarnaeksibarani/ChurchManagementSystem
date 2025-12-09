package ChurchManagementSystem.CMS.modules.news.controller;

import ChurchManagementSystem.CMS.core.customResponse.ApiResponse;
import ChurchManagementSystem.CMS.core.exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.news.dto.NewsDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsRequestDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsResponDto;
import ChurchManagementSystem.CMS.modules.news.dto.SendDataDto;
import ChurchManagementSystem.CMS.modules.news.entity.NewsEntity;
import ChurchManagementSystem.CMS.modules.news.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllNews(
           NewsRequestDto requestDto) {
        try {
            PaginationUtil<NewsEntity, SendDataDto> result = newsService.getAllNews(requestDto);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data board", result), HttpStatus.OK);
        }catch (CustomRequestException er){
            return er.GlobalCustomRequestException(er.getMessage(), er.getStatus());
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<NewsResponDto> getNewsById(@PathVariable Long id) throws Exception {
        NewsResponDto news = newsService.getNewsById(id);
        return ApiResponse.<NewsResponDto>builder()
                .message("Success get data")
                .status(HttpStatus.OK)
                .result(news)
                .build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<NewsResponDto> createNews(
            @RequestPart("request") @Valid NewsDto request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {
        NewsResponDto news = newsService.createNews(request, image);
        return ApiResponse.<NewsResponDto>builder()
                .result(news)
                .status(HttpStatus.CREATED)
                .message("Success create data")
                .build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<NewsResponDto> updateNewsById(
            @PathVariable Long id,
            @RequestPart @Valid NewsDto request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {

        NewsResponDto news = newsService.updateNews(id, request, image);
        return ApiResponse.<NewsResponDto>builder()
                .message("Success update data")
                .status(HttpStatus.OK)
                .result(news)
                .build();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> deleteById(@PathVariable Long id) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        newsService.deleteById(id);
        return ApiResponse.<String>builder()
                .result("Deleted")
                .status(HttpStatus.NO_CONTENT)
                .message("Success delete data")
                .build();
    }

    @GetMapping("/image/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        return newsService.getImageFile(filename);
    }

}
