package de.haw.db;

import de.haw.db.exception.ConnectionException;
import de.haw.model.Person;

import org.json.JSONObject;

import java.util.Collection;

/**
 * Created by Fenja on 19.11.2014.
 */
public interface DB {
	
	public void connect(String url, String user, String pass) throws ConnectionException;
	
    public Collection<Person> save(int searchID, String naturalLanguage, JSONObject requests, Collection<Person> result);
    
    public DBRecord load(int searchID);

    public void close();
    
}
