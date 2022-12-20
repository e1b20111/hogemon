package oit.is.team2.hogemon.model;

import java.util.ArrayList;

//import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SkillMapper {

  @Select("SELECT * from skill;")
  ArrayList<Skill> selectAllSkills();

  @Select("SELECT * from skill where skillname =#{skillname};")
  Skill selectSkillByname(String skillname);

}
