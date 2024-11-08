package ChurchManagementSystem.CMS.modules.board.dto;

import ChurchManagementSystem.CMS.core.enums.Fungsi;
import ChurchManagementSystem.CMS.core.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponDto {
    private long id;
    private String name;
    private Date birthDate;
    private int age;
    private String phoneNumber;
    private String address;
    private Status status;
    private Fungsi fungsi;
}
