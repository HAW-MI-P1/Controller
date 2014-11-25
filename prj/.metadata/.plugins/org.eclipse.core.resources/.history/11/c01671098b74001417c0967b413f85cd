package de.haw.controller;

import de.haw.model.Person;
import org.json.JSONObject;

import java.util.Collection;

/**
 * Created by Fenja on 19.11.2014.
 */
public interface Controller {

    public Collection<Person> search(String naturalLanguage);
    public JSONObject parse(String naturalLanguage);
    public Collection<Person> collect(JSONObject requests);
    public Collection<Person> save(int searchID, String naturalLanguage, JSONObject requests, Collection<Person> result);
}
