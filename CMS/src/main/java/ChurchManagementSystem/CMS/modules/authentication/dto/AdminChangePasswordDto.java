package ChurchManagementSystem.CMS.modules.authentication.dto;

import lombok.Data;

@Data
public class AdminChangePasswordDto {
    private String email;

    private String newPassword;
}
