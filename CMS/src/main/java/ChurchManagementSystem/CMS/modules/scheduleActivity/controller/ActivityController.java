package ChurchManagementSystem.CMS.modules.scheduleActivity.controller;

import ChurchManagementSystem.CMS.core.CustomResponse.ApiResponse;
import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.scheduleActivity.dto.ActivityDto;
import ChurchManagementSystem.CMS.modules.scheduleActivity.dto.ActivityRequestDto;
import ChurchManagementSystem.CMS.modules.scheduleActivity.entities.ActivityEntity;
import ChurchManagementSystem.CMS.modules.scheduleActivity.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/api/v1/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    //Getting
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchActivity(ActivityRequestDto searchDTO){
        try {
            PaginationUtil<ActivityEntity, ActivityEntity> result = activityService.getAllActivityByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data activity", result), HttpStatus.OK);
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    // Get activity upcoming & history
    @GetMapping(value = "/upcoming", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUpcomingActivity(
            @RequestParam("currentDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date currentDate,
            @RequestParam(value = "sortDate", defaultValue = "ASC") String sortDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try {
            PaginationUtil<ActivityEntity, ActivityEntity> result = activityService.getUpcomingActivities(currentDate, page, size, sortDate);
            ApiResponse<PaginationUtil<ActivityEntity, ActivityEntity>> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved upcoming activities", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHistoryActivity(
            @RequestParam("currentDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date currentDate,
            @RequestParam(value = "sortDate", defaultValue = "DESC") String sortDate,
            @RequestParam(value = "page", defaultValue = "1")int page,
            @RequestParam(value = "size", defaultValue = "10")int size
    ) {
        try {
            PaginationUtil<ActivityEntity, ActivityEntity> result = activityService.getHistoryActivity(currentDate, page, size, sortDate);
            ApiResponse<PaginationUtil<ActivityEntity, ActivityEntity>> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved activity history", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/sorted-by-date", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginationUtil<ActivityEntity, ActivityEntity>> getActivitiesSortedByDate(
            @RequestParam(value = "sortDate", defaultValue = "ASC") String sortDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        PaginationUtil<ActivityEntity, ActivityEntity> sortedActivities =
                activityService.getActivityBySortDate(sortDate, page, size);
        return ResponseEntity.ok(sortedActivities);
    }

    //Getting by Id
    @GetMapping(value = "/{idActivity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getActivityById(
            @PathVariable("idActivity") Long idActivity
    ){
        try {
            ActivityEntity result = activityService.getActivityById(idActivity);
            ApiResponse<ActivityEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrievedd data Activity", result);
            return new ResponseEntity<>(response, response.getStatus());
        }catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //create
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createActivity(
            @Valid @RequestBody ActivityDto request
    ){
        try {
            ActivityEntity result = activityService.createActivity(request);
            ApiResponse<ActivityEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data activity!", result);
            return new ResponseEntity<>(response, response.getStatus());
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Update by Id
    @PutMapping(value = "/{idActivity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateActivity(
            @PathVariable("idActivity") Long idActivity,
            @Valid @RequestBody ActivityDto request
    ){
        try {
            ActivityEntity result = activityService.updateActivity(idActivity, request);
            ApiResponse<ActivityEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data activity!", result);
            return new ResponseEntity<>(response, response.getStatus());
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Delete by Id
    @DeleteMapping(value = "/{idActivity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteActivity(
            @PathVariable("idActivity") Long idActivity
    ){
        try{
            activityService.deleteActivity(idActivity);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK,"Success delete data", "DELETED");
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
