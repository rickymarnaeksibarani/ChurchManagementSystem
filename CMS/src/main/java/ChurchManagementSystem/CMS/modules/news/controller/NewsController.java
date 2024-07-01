package ChurchManagementSystem.CMS.modules.news.controller;

import ChurchManagementSystem.CMS.core.CustomResponse.ApiResponse;
import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.congregration.dto.CongregrationRequestDto;
import ChurchManagementSystem.CMS.modules.congregration.entities.CongregrationEntity;
import ChurchManagementSystem.CMS.modules.news.dto.ApplicationFileDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsRequestDto;
import ChurchManagementSystem.CMS.modules.news.dto.NewsResponDto;
import ChurchManagementSystem.CMS.modules.news.entity.NewsEntity;
import ChurchManagementSystem.CMS.modules.news.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/news")
//@CrossOrigin(origins = "http://localhost:4200")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<NewsResponDto> createNews(
            @RequestPart @Valid NewsDto request,
            @RequestPart(value = "thumbnail", required = false)List<MultipartFile> thumbnail
    )throws Exception{
        if (Objects.nonNull(thumbnail)){
            request.setThumbnail(thumbnail);
        }
        NewsResponDto news = newsService.createNews(request);
        return ApiResponse.<NewsResponDto>builder()
                .result(news)
                .status(HttpStatus.CREATED)
                .message("Success create data")
                .build();
    }

    //Getting by Pagination
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

    //Getting by Id
//    @GetMapping(value = "/{idCongregration}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getCongregrationById(
//            @PathVariable("idCongregration") Long idCongregration
//    ){
//        try {
//            CongregrationEntity result = congregrationService.getCongregrationById(idCongregration);
//            ApiResponse<CongregrationEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrievedd data people", result);
//            return new ResponseEntity<>(response, response.getStatus());
//        }catch (CustomRequestException error){
//            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
//        }
//    }
}
