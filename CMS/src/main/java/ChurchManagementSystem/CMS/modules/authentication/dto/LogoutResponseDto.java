package ChurchManagementSystem.CMS.modules.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogoutResponseDto {
    private String bearerToken;
}
