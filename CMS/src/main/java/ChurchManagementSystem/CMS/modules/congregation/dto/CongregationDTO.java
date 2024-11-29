package ChurchManagementSystem.CMS.modules.congregation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CongregationDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("age")
    private int age;

    @JsonProperty("birthDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birthDate;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("address")
    private String address;


}
