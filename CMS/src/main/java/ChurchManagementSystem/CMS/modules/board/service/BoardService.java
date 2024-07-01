package ChurchManagementSystem.CMS.modules.board.service;

import ChurchManagementSystem.CMS.modules.board.dto.BoardDto;
import ChurchManagementSystem.CMS.modules.board.dto.BoardRequestDto;
import ChurchManagementSystem.CMS.modules.board.entity.BoardEntity;
import ChurchManagementSystem.CMS.modules.board.repository.BoardRepository;
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

public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    //Create
    public BoardEntity createBoard(BoardDto request){
        BoardEntity data = new BoardEntity();
        data.setName(request.getName());
        data.setBirthDate(request.getBirhDate());
        data.setAge(request.getAge());
        data.setAddress(request.getAddress());
        data.setPhoneNumber(request.getPhoneNumber());
        data.setFungsi(request.getFungsi());
        data.setStatus(request.getStatus());
        return boardRepository.save(data);
    }

    //Getting by pagination
    public PaginationUtil<BoardEntity, BoardEntity> getAllBoardByPagination(BoardRequestDto searchRequest){
        Specification<BoardEntity> specification = (root, query, builder)->{
            List<Predicate> predicates = new ArrayList<>();

            //search by name
            if (searchRequest.getSearchTerm() != null) {
                predicates.add(
                        builder.like(builder.upper(root.get("name")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%")
                );
            }

            //search by status
            if (searchRequest.getStatus() != null && !searchRequest.getStatus().isEmpty()) {
                predicates.add(
                        builder.in(root.get("status")).value(searchRequest.getStatus())
                );
            }

            //search by fungsi
            if (searchRequest.getFungsi()!= null && !searchRequest.getFungsi().isEmpty()){
                predicates.add(
                        builder.in(root.get("fungsi")).value(searchRequest.getFungsi())
                );
            }

            return query.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[]{})).getRestriction();
        };
        Pageable paging = PageRequest.of(searchRequest.getPage()-1, searchRequest.getSize());
        Page<BoardEntity> pagedResult = boardRepository.findAll(specification, paging);
        return new PaginationUtil<>(pagedResult, BoardEntity.class);

    }

//    Getting by Id
    public BoardEntity getBoardById(Long idBoard){
        BoardEntity result = boardRepository.findById(idBoard).orElse(null);
        if (result == null){
            throw new CustomRequestException("ID " + idBoard + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    //Update by Id
    @Transactional
    public BoardEntity updateBoard(Long idBoard, BoardDto request){
        BoardEntity board = boardRepository.findById(idBoard).orElseThrow(()-> new CustomRequestException("People does nit exists", HttpStatus.CONFLICT));
        board.setName(request.getName());
        board.setBirthDate(request.getBirhDate());
        board.setAge(request.getAge());
        board.setAddress(request.getAddress());
        board.setPhoneNumber(request.getPhoneNumber());
        board.setFungsi(request.getFungsi());
        board.setStatus(request.getStatus());

        return boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long idBoard){
        BoardEntity findData = boardRepository.findById(idBoard).orElseThrow(()->new CustomRequestException("People does not exists", HttpStatus.CONFLICT));
        boardRepository.delete(findData);
    }
}