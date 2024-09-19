package ChurchManagementSystem.CMS.modules.congregration.service;


import ChurchManagementSystem.CMS.modules.congregration.dto.CongregrationDTO;
import ChurchManagementSystem.CMS.modules.congregration.dto.CongregrationRequestDto;
import ChurchManagementSystem.CMS.modules.congregration.entities.CongregrationEntity;
import ChurchManagementSystem.CMS.modules.congregration.repository.CongregrationRepository;
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
public class CongregrationService {
    @Autowired
    private CongregrationRepository congregrationRepository;

    //Getting

    public PaginationUtil<CongregrationEntity, CongregrationEntity> getAllCongregrationByPagination(CongregrationRequestDto searchRequest){
        Specification<CongregrationEntity> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchRequest.getSearchTerm() != null) {
                predicates.add(
                        builder.like(builder.upper(root.get("name")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%")
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Page<CongregrationEntity> pagedResult = congregrationRepository.findAll(spec, paging);
        return new PaginationUtil<>(pagedResult, CongregrationEntity.class);
    }
    //Getting by ID

    public CongregrationEntity getCongregrationById(Long idCongregration){
        CongregrationEntity result = congregrationRepository.findById(idCongregration).orElse(null);
        if (result == null){
            throw new CustomRequestException("ID " + idCongregration + " not found ", HttpStatus.NOT_FOUND);
        }
        return result;
    }
    //Created
    public CongregrationEntity createCongregration(CongregrationDTO request){
        try {
            CongregrationEntity data = CongregrationEntity.builder()
                    .name(request.getName())
                    .birthDate(request.getBirthDate())
                    .age(request.getAge())
                    .phoneNumber(request.getPhoneNumber())
                    .build();
            return congregrationRepository.save(data);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public CongregrationEntity updateCongregration(Long idCongregration, CongregrationDTO request){
        try {
            CongregrationEntity congregration = congregrationRepository.findById(idCongregration).orElseThrow(()-> new CustomRequestException("People does not exists", HttpStatus.CONFLICT));
            congregration.setName(request.getName());
            congregration.setAge(request.getAge());
            congregration.setBirthDate(request.getBirthDate());
            congregration.setPhoneNumber(request.getPhoneNumber());
            return congregrationRepository.save(congregration);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteCongregration(Long idCongregration){
        CongregrationEntity findData = congregrationRepository.findById(idCongregration).orElseThrow(()->new CustomRequestException("People does not exists", HttpStatus.CONFLICT));
        congregrationRepository.delete(findData);
    }
}
