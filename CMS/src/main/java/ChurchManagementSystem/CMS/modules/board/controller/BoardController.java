package ChurchManagementSystem.CMS.modules.board.controller;

import ChurchManagementSystem.CMS.modules.board.dto.BoardDto;
import ChurchManagementSystem.CMS.modules.board.dto.BoardRequestDto;
import ChurchManagementSystem.CMS.modules.board.entity.BoardEntity;
import ChurchManagementSystem.CMS.modules.board.service.BoardService;
import ChurchManagementSystem.CMS.core.CustomResponse.ApiResponse;
import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBoard(
            @Valid @RequestBody BoardDto request
    ){
        try {
            BoardEntity result = boardService.createBoard(request);
            ApiResponse<BoardEntity> response =new ApiResponse<>(HttpStatus.CREATED, "Success create data board", result);
            return new ResponseEntity<>(response, response.getStatus());
        }catch (CustomRequestException err){
            return err.GlobalCustomRequestException(err.getMessage(), err.getStatus());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchBoard(BoardRequestDto searchDTO){
        try {
            PaginationUtil<BoardEntity, BoardEntity> result = boardService.getAllBoardByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data board", result), HttpStatus.OK);
        }catch (CustomRequestException err){
            return err.GlobalCustomRequestException(err.getMessage(), err.getStatus());
        }
    }

    //Getting by Id
    @GetMapping(value = "/{idBoard}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBoardById(
            @PathVariable("idBoard") Long idBoard
    ){
        try {
            BoardEntity result = boardService.getBoardById(idBoard);
            ApiResponse<BoardEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrievedd data people", result);
            return new ResponseEntity<>(response, response.getStatus());
        }catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Update by Id
    @PutMapping(value = "/{idBoard}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBoard(
            @PathVariable("idBoard") Long idBoard,
            @Valid @RequestBody BoardDto request
    ){
        try {
            BoardEntity result = boardService.updateBoard(idBoard, request);
            ApiResponse<BoardEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data board!", result);
            return new ResponseEntity<>(response, response.getStatus());
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Delete by Id
    @DeleteMapping(value = "/{idBoard}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBoard(
            @PathVariable("idBoard") Long idBoard
    ){
        try{
            boardService.deleteBoard(idBoard);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK,"Success delete data", "DELETED");
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
