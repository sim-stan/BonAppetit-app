package com.bonappetit.init;

import com.bonappetit.model.entity.Category;
import com.bonappetit.model.enums.CategoryName;
import com.bonappetit.repo.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Component
public class CategoryInit implements CommandLineRunner {



    private final Map<CategoryName, String> categoryNameDescription = Map.of(
            CategoryName.MAIN_DISH,"Heart of the meal, substantial and satisfying; main dish delights taste buds.",
            CategoryName.DESSERT,  "Sweet finale, indulgent and delightful; dessert crowns the dining experience with joy.",
            CategoryName.COCKTAIL, "Sip of sophistication, cocktails blend flavors, creating a spirited symphony in every glass."
    );

    private final CategoryRepository categoryRepository;

    public CategoryInit(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.count()>0) {
            return;
        }

        List<Category> toInsert=Arrays.stream(CategoryName.values())
                    .map(categoryName -> new Category(categoryName,categoryNameDescription.get(categoryName))).toList();


            this.categoryRepository.saveAll(toInsert);
        }
}
