package datn.be.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ProductRequest {
    private String name;
    private String slug;
    private String status;
    private Integer indexSeo;
    private Integer number;
    private Integer sale;
    private Integer price;
    private Long categoryId;
    private String avatar;
    private String description;
    private String contents;
    private Set<Long> productsLabels;

    @Override
    public String toString() {
        return "ProductRequest{" +
                "name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", indexSeo=" + indexSeo +
                ", sale=" + sale +
                ", avatar='" + avatar + '\'' +
                ", categoryId=" + categoryId +
                ", productsLabels=" + productsLabels +
                '}';
    }
}
