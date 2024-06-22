package com.bonappetit.model.entity;

import com.bonappetit.model.enums.CategoryName;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private CategoryName categoryName;

    private String description;

    @OneToMany(mappedBy = "category")
    private List<Recipe> recipes;


    public Category() {

        this.recipes = new ArrayList<>();
    }

    public Category(CategoryName categoryName, String description) {

        super();
        this.categoryName = categoryName;
        this.description = description;

    }

    public long getId() {
        return id;
    }

    public Category setId(long id) {
        this.id = id;
        return this;
    }

    public CategoryName getCategoryName() {
        return categoryName;
    }

    public Category setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Category setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public Category setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        return this;
    }
}
