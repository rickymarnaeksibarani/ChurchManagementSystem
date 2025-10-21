package ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BirthdateCoomingResponeDto {
    private String name;
    private Integer age;
    private LocalDate birthDate;
    private String tipe; // "MAJELIS" atau "JEMAAT"
    private LocalDate nextBirthday;
}
