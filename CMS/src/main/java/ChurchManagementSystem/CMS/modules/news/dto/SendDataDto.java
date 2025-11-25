package ChurchManagementSystem.CMS.modules.news.dto;

import ChurchManagementSystem.CMS.core.enums.Category;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class SendDataDto {
    private Long id;
    private String title;
    private String imagePath;
    private String content;
    private LocalDateTime publishDate;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
