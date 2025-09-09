package ChurchManagementSystem.CMS.modules.news.dto;

import ChurchManagementSystem.CMS.core.enums.Category;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
