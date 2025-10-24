package ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.service;

import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.dto.BirthdateComingResponeDto;
import ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.entities.BirthdateComingEntity;
import ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.repositories.BirthdateComingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BirthdateComingService {
    private final BirthdateComingRepository birthdateCoomingRepository;

    public PaginationUtil<BirthdateComingEntity, BirthdateComingResponeDto> getAll(
            Integer page, Integer size
    ){
        try {
            Pageable paging = PageRequest.of(page - 1, size);
            Page<BirthdateComingEntity> pages = birthdateCoomingRepository.findAll(paging);
            return new PaginationUtil<>(pages, BirthdateComingResponeDto.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
