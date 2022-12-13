package oit.is.team2.hogemon.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {

  @Select("SELECT * from matchInfo;")
  ArrayList<Match> selectAllMatches();

  @Select("SELECT * from matchInfo where id = #{id};")
  Match selectMatchById(int id);

  // 戦闘相手がすでにいるかどうかの判断
  // matchInfoの初期値はskillのみnull, 他は0(nullのままだと不具合発生して修正方法分からず)
  // 初めて呼び出されるなら初期値のため0, 2回目以降は0以外で判断
  @Select("SELECT mymonsterid from matchInfo where id = 1;")
  int selectFirstMyMonsterId();

  @Select("SELECT enemymonsterid from matchInfo where id = 1;")
  int selectFirstEnemyMonsterId();

  // 強制的に全ての列の値を変更するため、初期値入力にのみ使う
  // where id = 1で不具合発生, 修正方法分からず
  @Update("UPDATE matchInfo SET mymonsterid =#{mymonsterid}, mymonsterhp=#{mymonsterhp};")
  void updateFirstPlayer(int mymonsterid, int mymonsterhp);

  @Update("UPDATE matchInfo SET enemymonsterid =#{mymonsterid}, enemymonsterhp=#{mymonsterhp};")
  void updateSecondPlayer(int mymonsterid, int mymonsterhp);

  // 初回のみid=1のskill, damage, enemymonstehpを更新する。
  @Update("UPDATE matchInfo SET enemymonsterhp = enemymonsterhp - #{damage}, skill =#{skillname}, damage =#{damage} where id = 1;")
  void updateFirstDamage(String skillname, int damage);

  // id=1でのskillの値がnullの物を取り出す
  // →2回目移行ではskillの値は埋まっているはずなので初回かどうかの判定に用いる。
  @Select("SELECT skill from matchInfo where id = 1;")
  String selectFirstSkill();

  // 試合情報が格納されたmatch型のデータを追加する。なお、matchidは自動生成され、自動的に振り分けられるようにする。
  @Insert("INSERT INTO matchInfo (mymonsterid,mymonsterhp,skill, enemymonsterid,enemymonsterhp, damage) VALUES (#{mymonsterid},#{mymonsterhp},#{skill},#{enemymonsterid},#{enemymonsterhp},#{damage});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatch(Match match);

  // 最新データの獲得(enemymonsterhp と mymonsterhp)
  // データをidの降順に並び替え、1行だけ取り出す。hp情報をMatch型に格納し、他の情報はコントローラーの方で埋める。
  @Select("SELECT * from matchInfo order by id DESC LIMIT 1;")
  Match selectLastData();

}
