package rs.raf.cafe.menuorder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.cafe.menuorder.model.MenuItemType;
import rs.raf.cafe.menuorder.model.Season;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private MenuItemType type;
    private Season season;
    private boolean available;
    private List<IngredientResponse> ingredients;
}
