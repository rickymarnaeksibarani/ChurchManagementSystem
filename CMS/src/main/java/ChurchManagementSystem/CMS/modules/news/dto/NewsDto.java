package ChurchManagementSystem.CMS.modules.news.dto;

import ChurchManagementSystem.CMS.core.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class NewsDto {
    @JsonProperty("id")
    private long id;

    @Nullable
    @JsonProperty("thumbnail")
    private List<MultipartFile> thumbnail; //image .png

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("publishDate")
    private LocalDateTime publishDate;

    @Enumerated
    private Category category;
}
