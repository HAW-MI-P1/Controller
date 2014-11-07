package ControllerPkg;

import java.util.*;

public interface IDBController 
{
    public void save(int searchID, String naturalLanguage, JsonObject requests, Collection<Person> result);
}