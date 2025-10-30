package ChurchManagementSystem.CMS.modules.congregation.service;


import ChurchManagementSystem.CMS.modules.congregation.dto.CongregationDTO;
import ChurchManagementSystem.CMS.modules.congregation.dto.CongregationRequestDto;
import ChurchManagementSystem.CMS.modules.congregation.entities.CongregationEntity;
import ChurchManagementSystem.CMS.modules.congregation.repository.CongregationRepository;
import ChurchManagementSystem.CMS.core.exception.CustomRequestException;
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
public class CongregationService {
    @Autowired
    private CongregationRepository congregationRepository;

    //Getting

    public PaginationUtil<CongregationEntity, CongregationEntity> getAllCongregrationByPagination(CongregationRequestDto searchRequest){
        Specification<CongregationEntity> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchRequest.getSearchTerm() != null) {
                predicates.add(
                        builder.like(builder.upper(root.get("name")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%")
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Page<CongregationEntity> pagedResult = congregationRepository.findAll(spec, paging);
        return new PaginationUtil<>(pagedResult, CongregationEntity.class);
    }
    //Getting by ID

    public CongregationEntity getCongregrationById(Long idCongregration){
        CongregationEntity result = congregationRepository.findById(idCongregration).orElse(null);
        if (result == null){
            throw new CustomRequestException("ID " + idCongregration + " not found ", HttpStatus.NOT_FOUND);
        }
        return result;
    }
    //Created
    public CongregationEntity createCongregration(CongregationDTO request){
        try {
            CongregationEntity data = CongregationEntity.builder()
                    .name(request.getName())
                    .birthDate(request.getBirthDate())
                    .phoneNumber(request.getPhoneNumber())
                    .address(request.getAddress())
                    .build();
            return congregationRepository.save(data);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public CongregationEntity updateCongregration(Long idCongregration, CongregationDTO request){
        try {
            CongregationEntity congregration = congregationRepository.findById(idCongregration).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " +idCongregration + " not found"));
            congregration.setName(request.getName());
            congregration.setBirthDate(request.getBirthDate());
            congregration.setPhoneNumber(request.getPhoneNumber());
            congregration.setAddress(request.getAddress());
            return congregationRepository.save(congregration);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteCongregration(Long idCongregration){
        CongregationEntity findData = congregationRepository.findById(idCongregration).orElseThrow(()->new CustomRequestException("People does not exists", HttpStatus.CONFLICT));
        congregationRepository.delete(findData);
    }
}
