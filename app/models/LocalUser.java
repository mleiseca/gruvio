package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mleiseca on 2/25/14.
 */
@Entity
public class LocalUser extends Model{
    @Id
    public String id;
    public String name;
    public String email;
    public String password;
    public String provider;
    public String firstName;
    public String lastName;

    public static Finder<String, LocalUser> find = new Finder<> (String.class,LocalUser.class);


    /**
     * Retrieve a User using an email.
     */
    public static LocalUser findById(String id) {
        return find.where().eq("id", id).findUnique();
    }
}
