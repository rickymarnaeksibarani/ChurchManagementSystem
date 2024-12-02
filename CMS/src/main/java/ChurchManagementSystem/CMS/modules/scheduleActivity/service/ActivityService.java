package ChurchManagementSystem.CMS.modules.scheduleActivity.service;

import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.scheduleActivity.dto.ActivityDto;
import ChurchManagementSystem.CMS.modules.scheduleActivity.dto.ActivityRequestDto;
import ChurchManagementSystem.CMS.modules.scheduleActivity.entities.ActivityEntity;
import ChurchManagementSystem.CMS.modules.scheduleActivity.repository.ActivityRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    public PaginationUtil<ActivityEntity, ActivityEntity> getUpcomingActivities(Date currentDate, int page, int size) {
        Specification<ActivityEntity> spec = (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("activityDate"), currentDate);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "activityDate"));
        Page<ActivityEntity> pagedResult = activityRepository.findAll(spec, paging);
        return new PaginationUtil<>(pagedResult, ActivityEntity.class);
    }
    public PaginationUtil<ActivityEntity, ActivityEntity> getHistoryActivity(Date currentDate, int page, int size){
        Specification<ActivityEntity> specification = (root, query, builder)->
                builder.lessThanOrEqualTo(root.get("activityDate"), currentDate);

        Pageable paging = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "activityDate"));
        Page<ActivityEntity> pagedResult = activityRepository.findAll(specification, paging);
        return new PaginationUtil<>(pagedResult, ActivityEntity.class);
    }
    //Getting by ID
    public ActivityEntity getActivityById(Long idActivity){
        ActivityEntity result = activityRepository.findById(idActivity).orElse(null);
        if (result == null){
            throw new CustomRequestException("ID " + idActivity + " not found ", HttpStatus.NOT_FOUND);
        }
        return result;
    }
    
    //Created
    public ActivityEntity createActivity(ActivityDto request){
        ActivityEntity data = ActivityEntity.builder()
                .activityTitle(request.getActivityTitle())
                .description(request.getDescription())
                .activityTime(request.getActivityTime())
                .activityDate(request.getActivityDate())
                .timeHour(request.getTimeHour())
                .location(request.getLocation())
                .pic(request.getPic())
                .build();
        return activityRepository.save(data);
    }

    @Transactional
    public ActivityEntity updateActivity(Long idActivity, ActivityDto request){
        try {
            ActivityEntity data = activityRepository.findById(idActivity).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " +idActivity + " not found"));
            data.setActivityTitle(request.getActivityTitle());
            data.setDescription(request.getDescription());
            data.setActivityDate(request.getActivityDate());
            data.setActivityTime(request.getActivityTime());
            data.setTimeHour(request.getTimeHour());
            data.setLocation(request.getLocation());
            data.setPic(request.getPic());
            return activityRepository.save(data);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteActivity(Long idActivity){
        ActivityEntity findData = activityRepository.findById(idActivity).orElseThrow(()->new CustomRequestException("Activity does not exists", HttpStatus.CONFLICT));
        activityRepository.delete(findData);
    }
}
