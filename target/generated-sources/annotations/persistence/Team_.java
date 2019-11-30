package persistence;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistence.Student;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-30T11:21:06")
@StaticMetamodel(Team.class)
public class Team_ extends TeamParameters_ {

    public static volatile SingularAttribute<Team, String> teamName;
    public static volatile SingularAttribute<Team, String> liaisonId;
    public static volatile SingularAttribute<Team, String> teamId;
    public static volatile SingularAttribute<Team, Student[]> members;
    public static volatile SingularAttribute<Team, String> dateOfCreation;
    public static volatile SingularAttribute<Team, Boolean> status;

}