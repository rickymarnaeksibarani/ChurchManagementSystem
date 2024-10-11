package ChurchManagementSystem.CMS.modules.loginUser.service;

import ChurchManagementSystem.CMS.core.Exception.UserAlreadyExistsException;
import ChurchManagementSystem.CMS.core.Exception.UserNotFoundException;
import ChurchManagementSystem.CMS.core.Exception.UserServiceLogicException;
import ChurchManagementSystem.CMS.modules.loginUser.dto.ApiResponDto;
import ChurchManagementSystem.CMS.modules.loginUser.dto.ApiResponseStatus;
import ChurchManagementSystem.CMS.modules.loginUser.dto.UserDetailsRequestDto;
import ChurchManagementSystem.CMS.modules.loginUser.entity.UserEntity;
import ChurchManagementSystem.CMS.modules.loginUser.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class UserServiceImpl extends LoginUserService {
    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public ResponseEntity<ApiResponDto<?>> registerUser(UserDetailsRequestDto newUserDetails)
            throws UserAlreadyExistsException, UserServiceLogicException {

        try {
            if (userRepository.findByEmail(newUserDetails.getEmail()) != null){
                throw new UserAlreadyExistsException("Registration failed: User already exists with email " + newUserDetails.getEmail());
            }
            if (userRepository.findByUsername(newUserDetails.getUsername()) != null){
                throw new UserAlreadyExistsException("Registration failed: User already exists with username " + newUserDetails.getUsername());
            }

            UserEntity newUser = new UserEntity(
                    newUserDetails.getUsername(), newUserDetails.getEmail(), newUserDetails.getPhone(), LocalDateTime.now()
            );

            // save() is an in built method given by JpaRepository
            userRepository.save(newUser);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponDto<>(ApiResponseStatus.SUCCESS.name(), "New user account has been successfully created!"));

        }catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException(e.getMessage());
        }catch (Exception e) {
            throw new UserServiceLogicException("Failed to create new user account" + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ApiResponDto<?>> getAllUsers() throws UserServiceLogicException {
        try {
            List<UserEntity> users = userRepository.findAllByOrderByRegDateTimeDesc();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponDto<>(ApiResponseStatus.SUCCESS.name(), users)
                    );

        }catch (Exception e) {
            throw new UserServiceLogicException("Failed to fetch all users: "+e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ApiResponDto<?>> updateUser(UserDetailsRequestDto newUserDetails, int id)
            throws UserNotFoundException, UserServiceLogicException {
        try {
            UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

            user.setEmail(newUserDetails.getEmail());
            user.setUsername(newUserDetails.getUsername());
            user.setPhone(newUserDetails.getPhone());

            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponDto<>(ApiResponseStatus.SUCCESS.name(), "User account updated successfully!")
                    );

        }catch(UserNotFoundException e){
            throw new UserNotFoundException(e.getMessage());
        }catch(Exception e) {
            throw new UserServiceLogicException("Failed to update user account: " +e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ApiResponDto<?>> deleteUser(int id) throws UserServiceLogicException, UserNotFoundException {
        try {
            UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

            userRepository.delete(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponDto<>(ApiResponseStatus.SUCCESS.name(), "User account deleted successfully!")
                    );
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new UserServiceLogicException("Failed to delete user account: " + e.getMessage());
        }
    }

}
