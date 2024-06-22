package com.bonappetit.model.dtos;

import com.bonappetit.model.enums.CategoryName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddRecipeDTO {
    @NotNull
    @Size(min = 3, max = 20,message = "Name length must be between 2 and 40 characters!")
    private String name;

    @NotNull
    @Size(min = 3, max = 20,message = "Ingredients length must be between 2 and 80 characters!")
    private String ingredients;

    @NotNull(message = "You must select a category!")
    private CategoryName category;

    public AddRecipeDTO() {
    }

    public String getName() {
        return name;
    }

    public AddRecipeDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getIngredients() {
        return ingredients;
    }

    public AddRecipeDTO setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public CategoryName getCategory() {
        return category;
    }

    public AddRecipeDTO setCategory(CategoryName category) {
        this.category = category;
        return this;
    }
}
