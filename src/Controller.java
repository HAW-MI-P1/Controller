package ControllerPkg;

import java.util.*;

public class Controller implements ISearch4Facebook 
{
    public IDBController dbController;
    public IFacebookAPI facebookAPI;
    public IParser parser;

    public Collection<Person> search(String naturalLanguage) 
    {
        throw new UnsupportedOperationException();
    }
}