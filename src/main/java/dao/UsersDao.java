package dao;

import models.Users;

import java.util.List;

public interface UsersDao {

//create
       void add(Users users);

//    read
      List<Users> getAll();
      Users findById(int id);
  
//    update

//    delete

    void clearAll();

}
