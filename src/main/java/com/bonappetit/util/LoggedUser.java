package com.bonappetit.util;


import com.bonappetit.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
public class LoggedUser {

    private long id;
    private String username;

   public void login(User user){
       this.id= user.getId();
       this.username= user.getUsername();
   }

   public boolean isLogged(){
       return id!=0;
   }

    public long getId() {
        return id;
    }

    public LoggedUser setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LoggedUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public void logout() {

       this.username="";
       this.id=0;
    }
}
