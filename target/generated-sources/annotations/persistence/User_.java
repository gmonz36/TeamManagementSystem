package persistence;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-27T14:53:14")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> firstname;
    public static volatile SingularAttribute<User, byte[]> password;
    public static volatile SingularAttribute<User, byte[]> salt;
    public static volatile SingularAttribute<User, String> userId;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> lastname;

}