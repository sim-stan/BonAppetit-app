package com.bonappetit.controller;

import com.bonappetit.model.dtos.AddRecipeDTO;
import com.bonappetit.model.dtos.RecipeInfoDTO;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.enums.CategoryName;
import com.bonappetit.service.RecipeService;
import com.bonappetit.service.UserService;
import com.bonappetit.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class HomeController {

    private final LoggedUser loggedUser;
    private final RecipeService recipeService;
    private final UserService userService;

    public HomeController(LoggedUser loggedUser, RecipeService recipeService, UserService userService) {
        this.loggedUser = loggedUser;
        this.recipeService = recipeService;
        this.userService = userService;
    }


    @GetMapping("/")
    public String index() {
        if (loggedUser.isLogged()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    @Transactional
    public String viewHome( Model model) {

        if (!loggedUser.isLogged()) {
            return "redirect:/";
        }
        Map<CategoryName, List<Recipe>> allRecipes =
                recipeService.findAllByCategory();



        List<RecipeInfoDTO> favorites =
                userService.findFavourites(loggedUser.getId())
                        .stream()
                        .map(RecipeInfoDTO::new)
                        .toList();

        List<RecipeInfoDTO> allCocktails=allRecipes.get(CategoryName.COCKTAIL)
                .stream()
                .map(RecipeInfoDTO::new)
                .toList();


        List<RecipeInfoDTO> allDeserts=allRecipes.get(CategoryName.DESSERT)
                .stream()
                .map(RecipeInfoDTO::new)
                .toList();


        List<RecipeInfoDTO> allMainDishes =allRecipes.get(CategoryName.MAIN_DISH)
                .stream()
                .map(RecipeInfoDTO::new)
                .toList();


        model.addAttribute("allDeserts",allDeserts);
        model.addAttribute("allCocktails",allCocktails);
        model.addAttribute("allMainDishes",allMainDishes);
        model.addAttribute("favorites",favorites);




        return "home";


    }

}
