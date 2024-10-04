package ChurchManagementSystem.CMS.modules.loginUser;

import ChurchManagementSystem.CMS.core.Exception.UserAlreadyExistsException;
import ChurchManagementSystem.CMS.core.Exception.UserNotFoundException;
import ChurchManagementSystem.CMS.core.Exception.UserServiceLogicException;
import ChurchManagementSystem.CMS.modules.loginUser.dto.ApiResponDto;
import ChurchManagementSystem.CMS.modules.loginUser.dto.UserDetailsRequestDto;
import ChurchManagementSystem.CMS.modules.loginUser.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class loginUserController {
    @Autowired
    public LoginUserService loginUserService;

    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponDto<?>> registerUser(@Valid @RequestBody UserDetailsRequestDto userDetailsRequestDto) throws UserAlreadyExistsException, UserServiceLogicException {
        return loginUserService.registerUser(userDetailsRequestDto);
    }

    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponDto<?>> getAllUsers() throws UserServiceLogicException {
        return loginUserService.getAllUsers();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponDto<?>> updateUser(@Valid @RequestBody UserDetailsRequestDto userDetailsRequestDto, @PathVariable int id)
            throws UserNotFoundException, UserServiceLogicException {
        return loginUserService.updateUser(userDetailsRequestDto, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponDto<?>> deleteUser(@PathVariable int id)
            throws UserNotFoundException, UserServiceLogicException {
        return loginUserService.deleteUser(id);
    }
}
