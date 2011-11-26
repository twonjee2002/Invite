package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

@Entity
@Table(name="owners")
public class User extends Model {
    
    @Required
    @MaxSize(15)
    @MinSize(4)
    @Match(value="^\\w*$", message="Not a valid username")
    public String username;
    
    @Required
    @MaxSize(15)
    @MinSize(5)
    public String password;
    
    @Required
    @MaxSize(100)
    public String name;
    
    @Required
    @MaxSize(100)
    public String email;

	//public boolean isAdmin;
   
    public User(String name, String password, String username) {
        this.name = name;
        this.password = password;
        this.username = username;
        this.email = email;
      //  this.isAdmin = isAdmin;
    }

    public String toString()  {
        return "User(" + username + ")";
    }
    
}