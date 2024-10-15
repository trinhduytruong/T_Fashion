package datn.be.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedResponse<T> {
    private String status;
    private int errorCode;
    private String message;
    private Data<T> data;

    @Getter
    @Setter
    public static class Data<T> {
        private Meta meta;
        private List<T> data;

        public Data(Meta meta, List<T> dataModels) {
            this.meta = meta;
            this.data = dataModels;
        }
    }

    @Getter
    @Setter
    public static class Meta {
        private long total;
        private int perPage;
        private int currentPage;
        private int lastPage;

        public Meta(long total, int perPage, int currentPage, int lastPage) {
            this.total = total;
            this.perPage = perPage;
            this.currentPage = currentPage;
            this.lastPage = lastPage;
        }
    }

    @Getter
    @Setter
    public static class SingleResponse<T> {
        private String status;
        private int errorCode;
        private String message;
        private Data<T> data;

        @Getter
        @Setter
        public static class Data<T> {
            private T data;

            public Data(T dataModel) {
                this.data = dataModel;
            }
        }
    }
}

