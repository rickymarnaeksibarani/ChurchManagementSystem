package ChurchManagementSystem.CMS.core.CustomResponse;

import lombok.Getter;

@Getter
public class PaginateResponse<T> {
    private final T data;
    private final Page page;  // Changed 'int' to 'Page'

    // Constructor should match the fields in the order you want to pass them
    public PaginateResponse(T data, Page page) {
        this.data = data;
        this.page = page;
    }

    // Nested Page class
    @Getter
    public static class Page {
        private final long size;
        private final long total;
        private final long current;

        public Page(long size, long total, long current) {
            this.size = size;
            this.total = total;
            this.current = current;
        }
    }
}
