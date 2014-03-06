package controllers;

import play.data.Form;
import play.mvc.*;
import static play.data.Form.*;


import models.*;
import securesocial.core.Identity;
import securesocial.core.SocialUser;
import securesocial.core.java.SecureSocial;
import views.html.grooveform;

import java.util.UUID;


/**
 * Created by mleiseca on 2/28/14.
 */
public class Grooves extends Controller {

    final static Form<Groove> grooveForm = form(Groove.class);

    @SecureSocial.SecuredAction
    public static Result blank(){
        return ok(grooveform.render(grooveForm.fill(new Groove())));
    }

    @SecureSocial.SecuredAction
    public static Result create(){
        SocialUser user = (SocialUser) ctx().args.get(SecureSocial.USER_KEY);

        Groove result = grooveForm.bindFromRequest().get();
        result.id = UUID.randomUUID().toString();
        result.localUserId = user.identityId().userId();
        result.save();

        return ok(grooveform.render(grooveForm.fill(new Groove())));
    }


//
//    /**
//     * Defines a form wrapping the Contact class.
//     */
//    final static Form<Contact> contactForm = form(Contact.class);
//
//    /**
//     * Display a blank form.
//     */
//    public static Result blank() {
//        return ok(form.render(contactForm));
//    }
//
//    public static Result edit() {
//        Contact existingContact = new Contact(
//                "Fake", "Contact", "Fake company",
//                new Contact.Information(
//                        "Personal", "fakecontact@gmail.com", "01.23.45.67.89", "98.76.54.32.10"
//                ),
//                new Contact.Information(
//                        "Professional", "fakecontact@company.com", "01.23.45.67.89"
//                ),
//                new Contact.Information(
//                        "Previous", "fakecontact@oldcompany.com"
//                )
//        );
//        return ok(form.render(contactForm.fill(existingContact)));
//    }
//
//    /**
//     * Handle the form submission.
//     */
//    public static Result submit() {
//        Form<Contact> filledForm = contactForm.bindFromRequest();
//
//        if(filledForm.hasErrors()) {
//            return badRequest(form.render(filledForm));
//        } else {
//            Contact created = filledForm.get();
//            return ok(summary.render(created));
//        }
//    }
//

}
