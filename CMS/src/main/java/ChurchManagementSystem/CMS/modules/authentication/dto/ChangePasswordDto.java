package ChurchManagementSystem.CMS.modules.authentication.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;

}
