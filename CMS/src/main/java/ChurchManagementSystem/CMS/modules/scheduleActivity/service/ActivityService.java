package ChurchManagementSystem.CMS.modules.scheduleActivity.service;

import ChurchManagementSystem.CMS.modules.scheduleActivity.dto.ActivityDto;
import ChurchManagementSystem.CMS.modules.scheduleActivity.dto.ActivityRequestDto;
import ChurchManagementSystem.CMS.modules.scheduleActivity.entities.ActivityEntity;
import ChurchManagementSystem.CMS.modules.scheduleActivity.repository.ActivityRepository;
import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    //Getting
    public PaginationUtil<ActivityEntity, ActivityEntity> getAllActivityByPagination(ActivityRequestDto searchRequest){
        Specification<ActivityEntity> spec = (root, query, builder) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (searchRequest.getSearchTerm() != null) {
                predicates.add(
                        builder.like(builder.upper(root.get("activityTitle")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%")
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Page<ActivityEntity> pagedResult = activityRepository.findAll(spec, paging);
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
                .location(request.getLocation())
                .pic(request.getPic())
                .build();
        return activityRepository.save(data);
    }

    @Transactional
    public ActivityEntity updateActivity(Long idActivity, ActivityDto request){
        ActivityEntity activity = activityRepository.findById(idActivity)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + idActivity + " not found"));
        return ActivityEntity.builder()
                .id(activity.getId())
                .activityTitle(request.getActivityTitle())
                .description(request.getDescription())
                .activityDate(request.getActivityDate())
                .activityTime(request.getActivityTime())
                .location(request.getLocation())
                .pic(request.getPic())
                .created_at(activity.getCreated_at())
                .updated_at(activity.getUpdated_at())
                .build();
    }

    @Transactional
    public void deleteActivity(Long idActivity){
        ActivityEntity findData = activityRepository.findById(idActivity).orElseThrow(()->new CustomRequestException("Activity does not exists", HttpStatus.CONFLICT));
        activityRepository.delete(findData);
    }

}
