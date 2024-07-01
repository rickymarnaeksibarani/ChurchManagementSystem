package ChurchManagementSystem.CMS.core.CustomResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagingResponse {
    private Integer currentPage;
    private long totalItems;
    private Integer totalPage;
    private Integer size;
}
