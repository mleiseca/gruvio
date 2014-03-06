package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by mleiseca on 2/28/14.
 */
@Entity
public class GrooveVerificationCheck extends Model {

    @Id
    public String id;

    public String grooveId;
    public Date checkedAt;
    public Boolean checkWasSuccessful;

}
