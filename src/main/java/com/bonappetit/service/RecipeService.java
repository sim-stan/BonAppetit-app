package com.bonappetit.service;

import com.bonappetit.model.dtos.AddRecipeDTO;
import com.bonappetit.model.entity.Category;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.User;
import com.bonappetit.model.enums.CategoryName;
import com.bonappetit.repo.CategoryRepository;
import com.bonappetit.repo.RecipeRepository;
import com.bonappetit.repo.UserRepository;
import com.bonappetit.util.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final LoggedUser loggedUser;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public RecipeService(RecipeRepository recipeRepository, LoggedUser loggedUser, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.loggedUser = loggedUser;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public boolean addRecipe(AddRecipeDTO addRecipeDTO) {

        if (!loggedUser.isLogged()) {
            return false;
        }

        Optional<Category> category=categoryRepository.findByCategoryName(addRecipeDTO.getCategory());
        if (category.isEmpty()){
            return false;
        }

        Optional<User> user=userRepository.findById(loggedUser.getId());

        if (user.isEmpty()){
            return false;
        }

        Recipe recipe=new Recipe();
                recipe.setName(addRecipeDTO.getName());
                recipe.setIngredients(addRecipeDTO.getIngredients());
                recipe.setCategory(category.get());
                recipe.setAddedBy(user.get());

        recipeRepository.save(recipe);
        return true;
    }


    public Map<CategoryName, List<Recipe>> findAllByCategory() {
        Map<CategoryName, List<Recipe>> result = new HashMap<>();

        List<Category> allCategories = categoryRepository.findAll();

        for (Category cat : allCategories) {
            List<Recipe> recipes = recipeRepository.findAllByCategory(cat);

            result.put(cat.getCategoryName(), recipes);
        }

        return result;
    }
    @Transactional
    public void addToFavourites(Long id, long recipeId) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            return;
        }

        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);

        if (recipeOpt.isEmpty()) {
            return;
        }

       List<Recipe> favorites= userOpt.get().getFavouriteRecipes();
        if (favorites.contains(recipeOpt.get())){
            return;
        }

        userOpt.get().addFavourite(recipeOpt.get());

        userRepository.save(userOpt.get());
    }


}
