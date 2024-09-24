package ChurchManagementSystem.CMS.modules.scheduleActivity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ActivityDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("activityTitle")
    private String activityTitle;

    @JsonProperty("description")
    private String description;

    @JsonProperty("activityTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time activityTime;

    @JsonProperty("activityDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date activityDate;

    @JsonProperty("location")
    private String location;

    @JsonProperty("pic")
    private String pic;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updateAt;

}
