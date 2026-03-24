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
public class IngredientResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private boolean available;
}
