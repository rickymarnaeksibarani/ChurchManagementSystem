package ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.controller;

import ChurchManagementSystem.CMS.core.CustomResponse.HttpResponseDTO;
import ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.service.BirthdateComingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/birthdate-scheduler")
@RequiredArgsConstructor
public class BirthdateComingController {
    private final BirthdateComingService birthdateCoomingService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Object>> getAllBirthdateCooming(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        Object all = birthdateCoomingService.getAll(page, size);
        return new HttpResponseDTO<>(all, HttpStatus.OK)
                .setResponseHeaders("page", page)
                .setResponseHeaders("size", size)
                .toResponse();
    }
}
