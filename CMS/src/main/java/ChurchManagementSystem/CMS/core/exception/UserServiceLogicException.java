package ChurchManagementSystem.CMS.core.exception;

public class UserServiceLogicException extends Exception{
    public UserServiceLogicException(String message){
        super("Something went wrong. Please try again later!");
    }
}
