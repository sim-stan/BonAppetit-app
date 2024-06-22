package com.bonappetit.controller;

import com.bonappetit.model.dtos.AddRecipeDTO;
import com.bonappetit.repo.RecipeRepository;
import com.bonappetit.service.RecipeService;
import com.bonappetit.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RecipeController {


    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;
    private final LoggedUser loggedUser;

    public RecipeController(RecipeService recipeService, RecipeRepository recipeRepository, LoggedUser loggedUser) {
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
        this.loggedUser = loggedUser;
    }


    @ModelAttribute("addRecipeDTO")
    public AddRecipeDTO addRecipeDTO() {

        return new AddRecipeDTO();
    }

    @GetMapping("/recipes/add")
    public String addRecipe() {

        if (!loggedUser.isLogged()) {
            return "redirect:/";
        }

        return "recipe-add";
    }

    @PostMapping("/recipes/add")
    public String addRecipe(@Valid AddRecipeDTO addRecipeDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (!loggedUser.isLogged()) {
            return "redirect:/";
        }


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addRecipeDTO", addRecipeDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addRecipeDTO", bindingResult);

            return "redirect:/recipes/add";
        }

        boolean success = recipeService.addRecipe(addRecipeDTO);

        if (!success) {
            redirectAttributes.addFlashAttribute("hasAddRecipeErrors", addRecipeDTO);
            return "redirect:/recipes/add";
        }

        return "redirect:/home";
    }

    @PostMapping("/add-to-favourites/{recipeId}")
    public String addToFavourites(@PathVariable long recipeId) {
        if (!loggedUser.isLogged()) {
            return "redirect:/";
        }

        recipeService.addToFavourites(loggedUser.getId(), recipeId);

        return "redirect:/home";
    }
}
