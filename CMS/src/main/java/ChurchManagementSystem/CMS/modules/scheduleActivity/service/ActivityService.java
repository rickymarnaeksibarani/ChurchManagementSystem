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

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    //Created
    public ActivityEntity createActivity(ActivityDto request){
        boolean existsByActivityTitle = activityRepository.existsByActivityTitle(request.getActivityTitle());
        if (existsByActivityTitle){
            throw new CustomRequestException("Title Activity already exists, please check title", HttpStatus.CONFLICT);
        }
        ActivityEntity data = new ActivityEntity();
        data.setActivityTitle(request.getActivityTitle());
        data.setDescription(request.getDescription());
        data.setActivityTime(request.getActivityTime());
        data.setActivityDate(request.getActivityDate());
        data.setLocation(request.getLocation());
        data.setPic(request.getPic());

        return activityRepository.save(data);
    }

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

    @Transactional
    public ActivityEntity updateActivity(Long idActivity, ActivityDto request){
        ActivityEntity activity = activityRepository.findById(idActivity).orElseThrow(()-> new CustomRequestException("Activity does nit exists", HttpStatus.CONFLICT));
        activity.setActivityTitle(request.getActivityTitle());
        activity.setDescription(request.getDescription());
        activity.setActivityDate(request.getActivityDate());
        activity.setActivityTime(request.getActivityTime());
        activity.setLocation(request.getLocation());
        activity.setPic(request.getPic());
        return activityRepository.save(activity);
    }

    @Transactional
    public void deleteActivity(Long idActivity){
        ActivityEntity findData = activityRepository.findById(idActivity).orElseThrow(()->new CustomRequestException("Activity does not exists", HttpStatus.CONFLICT));
        activityRepository.delete(findData);
    }

}
