package ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BirthdateComingResponeDto {
    private String name;
    private Integer age;
    private LocalDate birthDate;
    private String tipe;
    private LocalDate nextBirthday;
}
