package oit.is.team2.hogemon.model;

import java.util.ArrayList;

//import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface resultMapper {

  @Select("SELECT * from result;")
  ArrayList<result> selectAllresults();

}
