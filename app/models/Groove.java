package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by mleiseca on 2/25/14.
 */
@Entity
public class Groove extends Model {

    @Id
    public String id;
    public String name;
    public String description;

    public String verificationMechanism;
    public String verificationFrequency;
    public Date lastVerification;

    public Date createdAt;

    @Column(nullable = false)
    public String localUserId;
}
