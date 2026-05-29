package com.staticvoid.menuandordersservice.dto.filters;

import com.staticvoid.menuandordersservice.model.enums.MenuItemType;
import com.staticvoid.menuandordersservice.model.enums.Season;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuItemFilterDto {

    private String name;
    private MenuItemType type;
    private Season season;
    private Boolean available;
    private Long ingredientId;
}