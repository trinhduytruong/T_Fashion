package datn.be.dto.response.DashboardResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DashboardResDto {
    private Long totalUsers;
    private Long totalOrders;
    private Long totalProducts;
    private Long totalArticles;

}