package com.bonappetit.model.entity;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 20)
    private String username;
    @NotNull
    private String password;
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "addedBy")
    private List<Recipe> addedRecipes;

    @ManyToMany
    private List<Recipe> favouriteRecipes;

    public User() {
        this.addedRecipes=new ArrayList<>();
        this.favouriteRecipes=new ArrayList<>();

    }

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<Recipe> getAddedRecipes() {
        return addedRecipes;
    }

    public User setAddedRecipes(List<Recipe> addedRecipes) {
        this.addedRecipes = addedRecipes;
        return this;
    }

    public List<Recipe> getFavouriteRecipes() {
        return favouriteRecipes;
    }

    public User setFavouriteRecipes(List<Recipe> favouriteRecipes) {
        this.favouriteRecipes = favouriteRecipes;
        return this;
    }

    public void addFavourite(Recipe recipe) {
        this.favouriteRecipes.add(recipe);
    }
}

