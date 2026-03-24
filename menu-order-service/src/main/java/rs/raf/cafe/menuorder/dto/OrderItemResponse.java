package rs.raf.cafe.menuorder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {

    private Long id;
    private String menuItemName;
    private int quantity;
    private String customizationNotes;
    private BigDecimal itemPrice;
}
