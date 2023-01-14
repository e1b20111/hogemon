package oit.is.team2.hogemon.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

@Mapper
public interface MatchMapper {

  @Select("SELECT * from matchInfo;")
  ArrayList<Match> selectAllMatches();

  @Select("SELECT * from matchInfo where id = #{id};")
  Match selectMatchById(int id);

  @Select("SELECT * from matchInfo where attackp = #{attackp};")
  ArrayList<Match> selectMatchByAttackp(String attackp);

  // 戦闘相手がすでにいるかどうかの判断
  // matchInfoの初期値はskillのみnull, 他は0(nullのままだと不具合発生して修正方法分からず)
  // 初めて呼び出されるなら初期値のため0, 2回目以降は0以外で判断
  @Select("SELECT p1monsterid from matchInfo where id = 1;")
  int selectFirstP1MonsterId();

  @Select("SELECT p2monsterid from matchInfo where id = 1;")
  int selectFirstP2MonsterId();

  // 強制的に全ての列の値を変更するため、初期値入力にのみ使う
  // where id = 1で不具合発生, 修正方法分からず
  @Update("UPDATE matchInfo SET p1name = #{p1}, p2name = #{p2}, p1monsterid =#{p1monsterid}, p1monstername=#{p1monstername} ,p1monsterhp=#{p1monsterhp};")
  void updateFirstPlayer(String p1, String p2, int p1monsterid, String p1monstername, int p1monsterhp);

  @Update("UPDATE matchInfo SET p2monsterid =#{p2monsterid}, p2monstername =#{p2monstername}, p2monsterhp=#{p2monsterhp};")
  void updateSecondPlayer(int p2monsterid, String p2monstername, int p2monsterhp);

  // 初回のみid=1のskill, damage, p2monstehpを更新する。
  @Update("UPDATE matchInfo SET p2monsterhp = p2monsterhp - #{damage}, skill =#{skillname}, damage =#{damage}, attackp=#{attackp} where id = 1;")
  void updateFirstDamage(String skillname, int damage, String attackp);

  @Update("UPDATE matchinfo SET p1name = p2name, p2name = p1name, p1monsterid = p2monsterid, p2monsterid = p1monsterid, p1monstername = p2monstername, p2monstername = p1monstername, p1monsterhp = p2monsterhp, p2monsterhp = p1monsterhp - #{damage}, skill=#{skillname}, damage = #{damage}, attackp =#{attackp} where id = 1;")
  void updateFirstDamageByP2(String skillname, int damage, String attackp);

  // 試合情報が格納されたmatch型のデータを追加する。なお、matchidは自動生成され、自動的に振り分けられるようにする。
  @Insert("INSERT INTO matchInfo (p1name,p1monsterid,p1monstername,p1monsterhp,skill, p2name, p2monsterid,p2monstername,p2monsterhp,damage,attackp) VALUES (#{p1name}, #{p1monsterid},#{p1monstername},#{p1monsterhp},#{skill},#{p2name}, #{p2monsterid},#{p2monstername},#{p2monsterhp},#{damage},#{attackp});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")

  void insertMatch(Match match);

  // 最新データの獲得(p2monsterhp と p1monsterhp)
  // データをidの降順に並び替え、1行だけ取り出す。hp情報をMatch型に格納し、他の情報はコントローラーの方で埋める。
  @Select("SELECT * from matchInfo order by id DESC LIMIT 1;")
  Match selectLastData();

  @Delete("DELETE FROM matchinfo;")
  boolean deleteAll();

  @Update("UPDATE matchInfo SET attackp=#{attakpid}")
  void updateAttackPlayer(int attakpid);

  // とりあえず動作させるためのプログラム
  @Insert("INSERT INTO matchInfo (id,p1monsterid,p2monsterid,p1monsterhp,p2monsterhp,damage) VALUES (1,0,0,0,0,0);")
  void insertTest();

}
