package controllers;

import com.avaje.ebean.Ebean;
import models.Groove;
import play.Logger;
import play.mvc.*;

import securesocial.core.Identity;
import securesocial.core.java.SecureSocial;
import views.html.*;

import java.util.List;


public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }


    @SecureSocial.SecuredAction
    public static Result dashboard() {
        Identity user = (Identity) ctx().args.get(SecureSocial.USER_KEY);

        List<Groove> grooves = Ebean.find(Groove.class)
                .where()
                    .eq("localUserId", user.identityId().userId())
                .findList();

        Logger.info("Found " + grooves.size() + " grooves");

        return ok(dashboard.render(user, grooves));
    }
}
