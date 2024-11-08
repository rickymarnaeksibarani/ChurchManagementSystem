package ChurchManagementSystem.CMS.modules.board.service;

import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.board.dto.BoardDto;
import ChurchManagementSystem.CMS.modules.board.dto.BoardRequestDto;
import ChurchManagementSystem.CMS.modules.board.dto.BoardResponDto;
import ChurchManagementSystem.CMS.modules.board.entity.BoardEntity;
import ChurchManagementSystem.CMS.modules.board.repository.BoardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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


    private BoardResponDto boardResponDto(BoardEntity board) throws JsonProcessingException {
        return BoardResponDto.builder()
                .id(board.getId())
                .name(board.getName())
                .birthDate(board.getBirthDate())
                .age(board.getAge())
                .address(board.getAddress())
                .phoneNumber(board.getPhoneNumber())
                .fungsi(board.getFungsi())
                .status(board.getStatus())
                .build();
    }

    @Transactional
    public BoardResponDto createBoard(BoardDto request) throws Exception{
        BoardEntity boardEntity = new BoardEntity();
        BoardEntity payload = boardPayload(request, boardEntity);
        payload.setName(request.getName());
        payload.setAge(request.getAge());
        payload.setBirthDate(request.getBirthDate());
        payload.setAddress(request.getAddress());
        payload.setPhoneNumber(request.getPhoneNumber());
        payload.setFungsi(request.getFungsi());
        payload.setStatus(request.getStatus());
        boardRepository.save(payload);
        return boardResponDto(payload);
    }

    @Transactional
    public BoardResponDto updateBoard(Long idBoard, BoardDto request) throws Exception{
        BoardEntity board = boardRepository.findById(idBoard).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        BoardEntity payload = boardPayload(request, board);
        payload.setName(request.getName());
        payload.setAge(request.getAge());
        payload.setBirthDate(request.getBirthDate());
        payload.setAddress(request.getAddress());
        payload.setPhoneNumber(request.getPhoneNumber());
        payload.setFungsi(request.getFungsi());
        payload.setStatus(request.getStatus());
        boardRepository.saveAndFlush(payload);
        return boardResponDto(payload);
    }

    //Getting by pagination
    public PaginationUtil<BoardEntity, BoardEntity> getAllBoardByPagination(BoardRequestDto searchRequest){
        Specification<BoardEntity> specification = (root, query, builder)->{
            List<Predicate> predicates = new ArrayList<>();
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

    @Transactional
    public void deleteBoard(Long idBoard){
        BoardEntity findData = boardRepository.findById(idBoard).orElseThrow(()->new CustomRequestException("People does not exists", HttpStatus.CONFLICT));
        boardRepository.delete(findData);
    }
    private BoardEntity boardPayload(BoardDto request, BoardEntity boards) throws Exception {
        boards.setName(request.getName());
        boards.setBirthDate(request.getBirthDate());
        boards.setAge(request.getAge());
        boards.setAddress(request.getAddress());
        boards.setAddress(request.getAddress());
        boards.setPhoneNumber(request.getPhoneNumber());
        boards.setFungsi(request.getFungsi());
        boards.setStatus(request.getStatus());
        return boards;
    }
}
