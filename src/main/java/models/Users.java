package models;

import java.util.Objects;

public class Users {
	
	private String name;
	private String address;
	private int id;

public String getName () {
	return name;
}

public String getAddress () {
	return address;
}

public int getId () {
	return id;
}

public void setName (String name) {
	this.name = name;
}

public void setAddress (String address) {
	this.address = address;
}

public void setId (int id) {
	this.id = id;
}

public Users(String name, String address){
		this.name = name;
		this.address = address;
		
		
		
	}

@Override
public boolean equals (Object o) {
	if (this == o)
		return true;
	if (o == null || getClass () != o.getClass ())
		return false;
	Users users = (Users) o;
	return id == users.id &&
			       Objects.equals (name, users.name) &&
			       Objects.equals (address, users.address);
}

@Override
public int hashCode () {
	return Objects.hash (name, address, id);
}
}

