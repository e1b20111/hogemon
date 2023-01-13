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

  // 戦闘相手がすでにいるかどうかの判断
  // matchInfoの初期値はskillのみnull, 他は0(nullのままだと不具合発生して修正方法分からず)
  // 初めて呼び出されるなら初期値のため0, 2回目以降は0以外で判断
  @Select("SELECT p1monsterid from matchInfo where id = 1;")
  int selectFirstP1MonsterId();

  @Select("SELECT p2monsterid from matchInfo where id = 1;")
  int selectFirstP2MonsterId();

  // 強制的に全ての列の値を変更するため、初期値入力にのみ使う
  // where id = 1で不具合発生, 修正方法分からず
  @Update("UPDATE matchInfo SET p1name = #{p1}, p2name = #{p2}, p1monsterid =#{p1monsterid}, p1monsterhp=#{p1monsterhp};")
  void updateFirstPlayer(String p1, String p2, int p1monsterid, int p1monsterhp);

  @Update("UPDATE matchInfo SET p2monsterid =#{p1monsterid}, p2monsterhp=#{p1monsterhp};")
  void updateSecondPlayer(int p1monsterid, int p1monsterhp);

  // 初回のみid=1のskill, damage, p2monstehpを更新する。
  @Update("UPDATE matchInfo SET p2monsterhp = p2monsterhp - #{damage}, skill =#{skillname}, damage =#{damage} where id = 1;")
  void updateFirstDamage(String skillname, int damage);

  // id=1でのskillの値がnullの物を取り出す
  // →2回目移行ではskillの値は埋まっているはずなので初回かどうかの判定に用いる。
  @Select("SELECT skill from matchInfo where id = 1;")
  String selectFirstSkill();

  // 試合情報が格納されたmatch型のデータを追加する。なお、matchidは自動生成され、自動的に振り分けられるようにする。
  @Insert("INSERT INTO matchInfo (p1name,p1monsterid,p1monsterhp,skill, p2name, p2monsterid,p2monsterhp, damage, attackplayer) VALUES (#{p1name}, #{p1monsterid},#{p1monsterhp},#{skill},#{p2name}, #{p2monsterid},#{p2monsterhp},#{damage},#{attackplayer});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatch(Match match);

  // 最新データの獲得(p2monsterhp と p1monsterhp)
  // データをidの降順に並び替え、1行だけ取り出す。hp情報をMatch型に格納し、他の情報はコントローラーの方で埋める。
  @Select("SELECT * from matchInfo order by id DESC LIMIT 1;")
  Match selectLastData();

  @Delete("DELETE FROM matchinfo;")
  boolean deleteAll();

  // とりあえず動作させるためのプログラム
  @Insert("INSERT INTO matchInfo (id,p1monsterid,p2monsterid,p1monsterhp,p2monsterhp,damage) VALUES (1,0,2,0,20,0);")
  void insertTest();

  @Select("SELECT * from matchInfo where attackplayer = 'p1';")
  Match selectP1Match();

  @Select("SELECT * from matchInfo where attackplayer = 'p2';")
  Match selectP2Match();
}
