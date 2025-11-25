package ChurchManagementSystem.CMS.modules.news.dto;

import ChurchManagementSystem.CMS.core.enums.Category;
import jakarta.mail.Multipart;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponDto {
    private Long id;
    @Enumerated
    private Category category;
    private String title;
    private String content;
    private String fileName;
    private String fileType;
    private long fileSize;
    private String filePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishDate;

}
