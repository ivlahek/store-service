package hr.ivlahek.sample.store.client.page;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PageResponseDto<T> {

    private int number;
    private int size;
    private int totalPages;
    private int numberOfElements;
    private long totalElements;

    private boolean previousPage;
    private boolean first;
    private boolean nextPage;
    private boolean last;

    private List<T> content;


    public static final class PageResponseDtoBuilder {
        private int number;
        private int size;
        private int totalPages;
        private int numberOfElements;
        private long totalElements;
        private boolean previousPage;
        private boolean first;
        private boolean nextPage;
        private boolean last;
        private List content;

        private PageResponseDtoBuilder() {
        }

        public static PageResponseDtoBuilder aPageResponseDto() {
            return new PageResponseDtoBuilder();
        }

        public PageResponseDtoBuilder withNumber(int number) {
            this.number = number;
            return this;
        }

        public PageResponseDtoBuilder withSize(int size) {
            this.size = size;
            return this;
        }

        public PageResponseDtoBuilder withTotalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public PageResponseDtoBuilder withNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
            return this;
        }

        public PageResponseDtoBuilder withTotalElements(long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public PageResponseDtoBuilder withPreviousPage(boolean previousPage) {
            this.previousPage = previousPage;
            return this;
        }

        public PageResponseDtoBuilder withFirst(boolean first) {
            this.first = first;
            return this;
        }

        public PageResponseDtoBuilder withNextPage(boolean nextPage) {
            this.nextPage = nextPage;
            return this;
        }

        public PageResponseDtoBuilder withLast(boolean last) {
            this.last = last;
            return this;
        }

        public PageResponseDtoBuilder withContent(List content) {
            this.content = content;
            return this;
        }

        public PageResponseDto build() {
            PageResponseDto pageResponseDto = new PageResponseDto();
            pageResponseDto.setNumber(number);
            pageResponseDto.setSize(size);
            pageResponseDto.setTotalPages(totalPages);
            pageResponseDto.setNumberOfElements(numberOfElements);
            pageResponseDto.setTotalElements(totalElements);
            pageResponseDto.setPreviousPage(previousPage);
            pageResponseDto.setFirst(first);
            pageResponseDto.setNextPage(nextPage);
            pageResponseDto.setLast(last);
            pageResponseDto.setContent(content);
            return pageResponseDto;
        }
    }
}
