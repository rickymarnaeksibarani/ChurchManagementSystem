package ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.service;

import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.dto.BirthdateCoomingResponeDto;
import ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.entities.BirthdateCoomingEntity;
import ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.repositories.BirthdateCoomingRepository;
import ChurchManagementSystem.CMS.modules.congregation.dto.CongregationRequestDto;
import ChurchManagementSystem.CMS.modules.congregation.entities.CongregationEntity;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BirthdateCoomingService {
    private final BirthdateCoomingRepository birthdateCoomingRepository;

    public PaginationUtil<BirthdateCoomingEntity, BirthdateCoomingResponeDto> getAll(
            Integer page, Integer size
    ){
        try {
            Pageable paging = PageRequest.of(page - 1, size);
            Page<BirthdateCoomingEntity> pages = birthdateCoomingRepository.findAll(paging);
            return new PaginationUtil<>(pages, BirthdateCoomingResponeDto.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
