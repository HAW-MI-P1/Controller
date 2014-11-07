package ControllerPkg;

import java.util.*;

public interface IFacebookAPI 
{

    public Collection<Person> collect(JsonObject requests);
}