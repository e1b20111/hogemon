package oit.is.team2.hogemon.model;

import java.util.ArrayList;

//import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT * from userInfo;")
  ArrayList<User> selectAllUsers();

  @Select("SELECT * from userInfo where id = #{id};")
  User selectUserById(int id);

}
