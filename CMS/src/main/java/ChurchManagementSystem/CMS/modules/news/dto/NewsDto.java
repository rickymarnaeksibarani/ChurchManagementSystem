package ChurchManagementSystem.CMS.modules.news.dto;

import ChurchManagementSystem.CMS.core.enums.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class NewsDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("image")
    private MultipartFile image; // Add this line

    @JsonProperty("content")
    private String content;

    @Enumerated
    private Category category;

    @CreationTimestamp
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @CreationTimestamp
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
