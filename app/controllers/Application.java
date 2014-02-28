package controllers;

import play.mvc.*;

import securesocial.core.Identity;
import securesocial.core.java.SecureSocial;
import views.html.*;


public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }


    @SecureSocial.SecuredAction
    public static Result dashboard() {
        Identity user = (Identity) ctx().args.get(SecureSocial.USER_KEY);

        return ok(dashboard.render(user));
    }
}
