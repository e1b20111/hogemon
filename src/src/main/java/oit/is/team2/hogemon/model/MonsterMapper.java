package oit.is.team2.hogemon.model;

import java.util.ArrayList;

//import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MonsterMapper {

  @Select("SELECT * from monsterbox;")
  ArrayList<Monster> selectAllMonsters();

  @Select("SELECT * from monsterbox where id = #{id};")
  Monster selectMonsterById(int id);

  @Select("SELECT COUNT(*) from monsterbox")
  int countMonsters();

}
