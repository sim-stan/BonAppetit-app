package com.bonappetit.service;

import com.bonappetit.model.dtos.UserLoginDTO;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.User;
import com.bonappetit.model.dtos.UserRegisterDTO;
import com.bonappetit.repo.UserRepository;
import com.bonappetit.util.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggedUser loggedUser;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, LoggedUser loggedUser, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggedUser = loggedUser;
        this.modelMapper = modelMapper;
    }


    public boolean login(UserLoginDTO userLoginDTO) {

        Optional<User> byUsername = userRepository.findByUsername(userLoginDTO.getUsername());

        if (byUsername.isEmpty()) {
            return false;
        }

        User user = byUsername.get();

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), byUsername.get().getPassword())) {
            return false;
        }

        loggedUser.login(user);

        return true;
    }


    public boolean register(UserRegisterDTO userRegisterDTO) {


        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            return false;
        }

        boolean existsByUsernameOrEmail = this.userRepository.existsByUsernameOrEmail(userRegisterDTO.getUsername(), userRegisterDTO.getEmail());


        if (existsByUsernameOrEmail) {
            return false;
        }

        User user=modelMapper.map(userRegisterDTO,User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public List<Recipe> findFavourites(Long id) {
        return userRepository.findById(id)
                .map(User::getFavouriteRecipes)
                .orElseGet(ArrayList::new);

//        Optional<User> byId = userRepository.findById(id);
//
//        if (byId.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        return byId.get().getFavouriteRecipes();
    }




    public void logout() {

        loggedUser.logout();
    }
}
