package service;

import models.LocalToken;
import models.LocalUser;
import org.joda.time.DateTime;
import play.Application;
import play.Logger;
import scala.Option;
import scala.Some;
import securesocial.core.*;
import securesocial.core.java.BaseUserService;
import securesocial.core.java.Token;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by mleiseca on 2/25/14.
 */
public class SqlUserService extends BaseUserService {

    public SqlUserService(Application application) {
        super(application);
    }

    /**
     * Saves the user.  This method gets called when a user logs in.
     * This is your chance to save the user information in your backing store.
     * @param user
     */
    @Override
    public Identity doSave(Identity user) {
        if (Logger.isDebugEnabled()) {
            Logger.debug("save...");
            Logger.debug(String.format("user = %s", user));
        }
        LocalUser localUser = null;
        //localUser = LocalUser.find.byId(user.id().id());
        localUser = LocalUser.find.byId(user.identityId().userId());

        if (localUser == null) {
            Logger.debug("adding new...");
            localUser = new LocalUser();
            //here was localUser.id = user.id().id();
            localUser.id = user.identityId().userId();
            localUser.provider = user.identityId().providerId();
            localUser.firstName = user.firstName();
            localUser.lastName = user.lastName();
            localUser.email = user.email().get();
            if(user.passwordInfo().isDefined()){
                localUser.password = user.passwordInfo().get().password();
            }
            localUser.save();
        } else {
            Logger.debug("existing one...");
            localUser.id = user.identityId().userId();
            localUser.provider = user.identityId().providerId();
            localUser.firstName = user.firstName();
            localUser.lastName = user.lastName();
            localUser.email = user.email().get();
            if(user.passwordInfo().isDefined()){
                localUser.password = user.passwordInfo().get().password();
            }
            localUser.update();
        }
        return user;
    }

    /**
     * Finds an Identity in the backing store.
     * @return an Identity instance or null if no user matches the specified id
     */
    @Override
    public Identity doFind(IdentityId identityId){
        if (Logger.isDebugEnabled()) {
            Logger.debug(String.format("finding by Id = %s", identityId.userId()));

        }
        LocalUser localUser = LocalUser.findByEmail(identityId.userId());
        if(localUser == null) return null;
        SocialUser socialUser = new SocialUser(new IdentityId(localUser.id, localUser.provider),
                localUser.firstName,
                localUser.lastName,
                String.format("%s %s", localUser.firstName, localUser.lastName),
                Option.apply(localUser.email),
                null,
                new AuthenticationMethod("userPassword"),
                null,
                null,
                Some.apply(new PasswordInfo("bcrypt", localUser.password, null))
        );
        if (Logger.isDebugEnabled()) {
            Logger.debug(String.format("socialUser = %s", socialUser));
        }
        return socialUser;
    }


    /**
     * Finds an identity by email and provider id.
     *
     * Note: If you do not plan to use the UsernamePassword provider just provide en empty
     * implementation.
     *
     * @param email - the user email
     * @param providerId - the provider id
     * @return an Identity instance or null if no user matches the specified id
     */
    @Override
    public Identity doFindByEmailAndProvider(String email, String providerId) {
        List<LocalUser> list = LocalUser.find.where().eq("email", email).eq("provider", providerId).findList();
        if(list.size() != 1){
            Logger.debug("found a null in findByEmailAndProvider...");
            return null;
        }
        LocalUser localUser = list.get(0);
        SocialUser socialUser =
                new SocialUser(new IdentityId(localUser.email, localUser.provider),
                        localUser.firstName,
                        localUser.lastName,
                        String.format("%s %s", localUser.firstName, localUser.lastName),
                        Option.apply(localUser.email),
                        null,
                        new AuthenticationMethod("userPassword"),
                        null,
                        null,
                        Some.apply(new PasswordInfo("bcrypt", localUser.password, null))
                );
        return socialUser;
    }


    /**
     * Saves a token
     */
    @Override
    public void doSave(Token token) {
        LocalToken localToken = new LocalToken();
        localToken.uuid = token.uuid;
        localToken.email = token.email;
        applyDates(token, localToken);
        localToken.isSignUp = token.isSignUp;
        localToken.save();
    }

    private static void applyDates(Token token, LocalToken localToken) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            localToken.createdAt = df.parse(token.creationTime.toString("yyyy-MM-dd HH:mm:ss"));
            localToken.expireAt = df.parse(token.expirationTime.toString("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            Logger.error("SqlUserService.doSave(): ", e);
        }
    }

    /**
     * Finds a token by id
     *
     * Note: If you do not plan to use the UsernamePassword provider just provide en empty
     * implementation
     *
     * @return a Token instance or null if no token matches the id
     */
    @Override
    public Token doFindToken(String token) {
        if (Logger.isDebugEnabled()) {
            Logger.debug("findToken...");
            Logger.debug(String.format("token = %s", token));
        }
        LocalToken localToken = LocalToken.find.byId(token);
        if(localToken == null) return null;
        Token result = new Token();
        result.uuid = localToken.uuid;
        result.creationTime = new DateTime(localToken.createdAt);
        result.email = localToken.email;
        result.expirationTime = new DateTime(localToken.expireAt);
        result.isSignUp = localToken.isSignUp;
        if (Logger.isDebugEnabled()) {
            Logger.debug(String.format("foundToken = %s", result));
        }
        return result;
    }

    /**
     * Deletes a token
     *
     * Note: If you do not plan to use the UsernamePassword provider just provide en empty
     * implementation
     *
     * @param uuid the token id
     */
    @Override
    public void doDeleteToken(String uuid) {
        if (Logger.isDebugEnabled()) {
            Logger.debug("deleteToken...");
            Logger.debug(String.format("uuid = %s", uuid));
        }
        LocalToken localToken = LocalToken.find.byId(uuid);
        if(localToken != null) {
            localToken.delete();
        }

    }

    /**
     * Deletes all expired tokens
     *
     * Note: If you do not plan to use the UsernamePassword provider just provide en empty
     * implementation
     *
     */
    @Override
    public void doDeleteExpiredTokens() {
        if (Logger.isDebugEnabled()) {
            Logger.debug("deleteExpiredTokens...");
        }
        List<LocalToken> list = LocalToken.find.where().lt("expireAt", new DateTime().toString()).findList();
        for(LocalToken localToken : list) {
            localToken.delete();
        }
    }

}
