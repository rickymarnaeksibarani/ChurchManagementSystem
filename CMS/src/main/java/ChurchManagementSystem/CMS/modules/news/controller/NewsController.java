package ChurchManagementSystem.CMS.modules.news.controller;

import ChurchManagementSystem.CMS.core.CustomResponse.ApiResponse;
import ChurchManagementSystem.CMS.modules.news.dto.NewsDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsRequestDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsResponDto;
import ChurchManagementSystem.CMS.modules.news.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Object> getAllNews(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            NewsRequestDto requestDto) {
        Object allNews = newsService.getAllNews(page, size, requestDto);

        return ApiResponse.builder()
                .result(allNews)
                .status(HttpStatus.OK)
                .message("Get All Data")
                .build();
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
}
