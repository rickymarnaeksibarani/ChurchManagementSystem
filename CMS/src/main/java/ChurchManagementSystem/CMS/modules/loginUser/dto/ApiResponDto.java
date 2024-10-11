package ChurchManagementSystem.CMS.modules.loginUser.dto;

import lombok.Data;

@Data
public class ApiResponDto<T>{
    private String status;
    private T respone;
        public ApiResponDto(String status, T response) {
        this.status = status;
        this.respone = response;
    }
}
