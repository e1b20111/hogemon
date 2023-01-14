package oit.is.team2.hogemon.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface resultMapper {

  @Select("SELECT * from result;")
  ArrayList<result> selectAllresults();

  @Select("SELECT * from result DESC LIMIT 1;")
  result selectLastresult();

  @Insert("INSERT INTO result (p1monstername,p2monstername,matchresult) VALUES (#{p1monstername},#{p2monstername},#{matchresult});")
  void insertResult(result result);

}
