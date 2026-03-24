package rs.raf.cafe.menuorder.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.cafe.menuorder.dto.IngredientRequest;
import rs.raf.cafe.menuorder.dto.IngredientResponse;
import rs.raf.cafe.menuorder.model.Ingredient;
import rs.raf.cafe.menuorder.repository.IngredientRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    @GetMapping
    public ResponseEntity<List<IngredientResponse>> getAllIngredients() {
        List<IngredientResponse> ingredients = ingredientRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ingredients);
    }

    @PostMapping
    public ResponseEntity<IngredientResponse> createIngredient(@Valid @RequestBody IngredientRequest request) {
        Ingredient ingredient = Ingredient.builder()
                .name(request.getName())
                .price(request.getPrice())
                .available(true)
                .build();
        ingredient = ingredientRepository.save(ingredient);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(ingredient));
    }

    private IngredientResponse toResponse(Ingredient ingredient) {
        return IngredientResponse.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .price(ingredient.getPrice())
                .available(ingredient.isAvailable())
                .build();
    }
}
