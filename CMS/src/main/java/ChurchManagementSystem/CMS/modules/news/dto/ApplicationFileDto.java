package ChurchManagementSystem.CMS.modules.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationFileDto {
    String filename;
    String path;
    String mimeType;
    String size;
}
