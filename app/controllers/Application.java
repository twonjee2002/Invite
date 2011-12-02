package controllers;

import play.*;
import play.data.validation.Valid;
import play.mvc.*;

import java.util.*;

import notifiers.Mails;

import models.*;

public class Application extends Controller {
	 @Before
	    static void addUser() {
	        User user = connected();
	        if(user != null) {
	            renderArgs.put("user", user);
	        }
	    }
	
 static User connected() {
     if(renderArgs.get("user") != null) {
         return renderArgs.get("user", User.class);
     }
     String username = session.get("user");
     if(username != null) {
         return User.find("byUsername", username).first();
     } 
     return null;
 }
 public static void index() {
     if(connected() != null) {
         Application.index();
     }
     render();
 }
       public static void register(String myName, String myPassword) {
        render(myName, myPassword);
    }
    public static void login(String username, String password) {
        User user = User.find("byUsernameAndPassword", username, password).first();
        if(user != null) {
            session.put("user", user.username);
            flash.success("Welcome, " + user.name);
            Workshop.active();         
        }
        // Oops
        flash.put("username", username);
        flash.error("Login failed");
        index();
        }
    public static void saveUser(@Valid User user, String verifyPassword, Mails mail) {
        validation.required(verifyPassword);
        validation.equals(verifyPassword, user.password).message("Your password doesn't match");
        if(validation.hasErrors()) {
            render("@register", user, verifyPassword);
        }
        mail.welcome(user);
        user.create();
        user.save();
        session.put("user", user.username);
        flash.success("Welcome, " + user.name + " Please check your mail");
        Workshop.home();
    }
      public static void uploadPicture(Picture picture) {
        picture.save();
        Workshop.design();
    }
    public static void getPicture(long id) {
    	Picture picture = Picture.findById(id);
        notFoundIfNull(picture);
        response.setContentTypeIfNotSet(picture.image.type());
        renderBinary(picture.image.get());
    }
    public static void deletePicture(long id) {
    	   final Picture picture = Picture.findById(id);
    	   picture.image.getFile().delete();
    	   picture.delete();
    	   Workshop.design();
    	}


    
        public static void logout() {
            session.clear();
            index();
}
    }