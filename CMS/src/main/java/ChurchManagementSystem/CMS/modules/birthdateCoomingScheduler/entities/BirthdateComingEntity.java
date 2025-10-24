package ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.entities;

import com.google.errorprone.annotations.Immutable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Table(name = "materialized_view_birthdate_coming", schema = "public")
@Entity
@Immutable
public class BirthdateComingEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "tipe")
    private String tipe; // "MAJELIS" atau "JEMAAT"

    @Column(name = "next_birthday")
    private LocalDate nextBirthday;
}
