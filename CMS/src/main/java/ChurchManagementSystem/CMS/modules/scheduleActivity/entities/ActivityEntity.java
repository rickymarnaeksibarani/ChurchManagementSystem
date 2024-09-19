package ChurchManagementSystem.CMS.modules.scheduleActivity.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "master_activity")
public class ActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @UuidGenerator
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID uuid;

    @Column(name = "activity_title")
    private String activityTitle;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "activity_time")
    private Time activityTime;

    @Column(name = "activity_date")
    private Date activityDate;

    @Column(name = "location")
    private String location;

    @Column(name = "pic")
    private String pic;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

}
