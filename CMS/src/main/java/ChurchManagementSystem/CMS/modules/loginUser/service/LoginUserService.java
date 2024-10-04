package ChurchManagementSystem.CMS.modules.loginUser.service;

import ChurchManagementSystem.CMS.core.Exception.UserAlreadyExistsException;
import ChurchManagementSystem.CMS.core.Exception.UserNotFoundException;
import ChurchManagementSystem.CMS.core.Exception.UserServiceLogicException;
import ChurchManagementSystem.CMS.modules.loginUser.dto.ApiResponDto;
import ChurchManagementSystem.CMS.modules.loginUser.dto.UserDetailsRequestDto;
import ChurchManagementSystem.CMS.modules.loginUser.entity.UserEntity;
import ChurchManagementSystem.CMS.modules.loginUser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public abstract class LoginUserService {
    @Autowired
    private UserRepository userRepository;
    public ResponseEntity<ApiResponDto<?>> registerUser(UserDetailsRequestDto newUserDetails)
            throws UserAlreadyExistsException, UserServiceLogicException {
        // Check if user already exists
        if (userRepository.existsByUsername(newUserDetails.getUsername())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        if (userRepository.existsByEmail(newUserDetails.getEmail())){
            throw new UserAlreadyExistsException("Email already exists");
        }
        UserEntity newUser = new UserEntity();
        newUser.setUsername(newUserDetails.getUsername());
        newUser.setEmail(newUserDetails.getEmail());
        newUser.setPhone(newUserDetails.getPhone());
        userRepository.save(newUser);
        // Return success response
        ApiResponDto<String> response = new ApiResponDto<>("succes","User registered successfully");
        return ResponseEntity.ok(response);
    }

    @Autowired
    public LoginUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<ApiResponDto<?>> getAllUsers() throws UserServiceLogicException {
        try {
            List<UserEntity> users = userRepository.findAll();
            List<UserDetailsRequestDto> userDetailsDtoList = users.stream()
                    .map(user -> new UserDetailsRequestDto(
                            user.getEmail(),
                            user.getUsername(),
                            user.getPhone()))
                    .collect(Collectors.toList());
            ApiResponDto<List<UserDetailsRequestDto>> response = new ApiResponDto<>("success", userDetailsDtoList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new UserServiceLogicException("Error fetching users "+e);
        }
    }

    public ResponseEntity<ApiResponDto<?>> updateUser(UserDetailsRequestDto newUserDetails, int id)
            throws UserNotFoundException, UserServiceLogicException {
        return null;
    }

    public ResponseEntity<ApiResponDto<?>> deleteUser(int id)
            throws UserServiceLogicException, UserNotFoundException {
        return null;
    }

}
