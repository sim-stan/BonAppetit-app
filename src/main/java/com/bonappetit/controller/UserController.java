package com.bonappetit.controller;

import com.bonappetit.model.dtos.UserLoginDTO;
import com.bonappetit.model.dtos.UserRegisterDTO;
import com.bonappetit.service.UserService;
import com.bonappetit.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {


    private final UserService userService;
    private final LoggedUser loggedUser;

    public UserController(UserService userService, LoggedUser loggedUser) {
        this.userService = userService;
        this.loggedUser = loggedUser;
    }


    @ModelAttribute("userLoginDTO")
    public UserLoginDTO userLoginDTO() {
        return new UserLoginDTO();
    }


    @ModelAttribute("userRegisterDTO")
    public UserRegisterDTO userRegisterDTO() {
        return new UserRegisterDTO();
    }

    @GetMapping("/login")
    public String viewLogin() {
        if (loggedUser.isLogged()) {
            return "redirect:/home";
        }

        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@Valid UserLoginDTO userLoginDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {


        if (loggedUser.isLogged()) {
            return "redirect:/home";
        }

        if (bindingResult.hasErrors() || !userService.login(userLoginDTO)) {
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userLoginDTO", bindingResult);

            return "redirect:/login";
        }

        boolean hasLogin = this.userService.login(userLoginDTO);

        if (!hasLogin) {

            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
            redirectAttributes.addFlashAttribute("hasLoginError", false);
            return "redirect:/login";
        }
        return "redirect:/home";
    }



    @GetMapping("/register")
    public String viewRegister() {
        if (loggedUser.isLogged()) {
            return "redirect:/home";
        }

        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid UserRegisterDTO userRegisterDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {


        if (loggedUser.isLogged()) {
            return "redirect:/home";
        }

        if (bindingResult.hasErrors() || !userService.register(userRegisterDTO)) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
            redirectAttributes.addFlashAttribute("hasRegisterErrors",false);

            return "redirect:/register";
        }


        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String doLogout(){
        loggedUser.logout();
        return "redirect:/";
    }
}
