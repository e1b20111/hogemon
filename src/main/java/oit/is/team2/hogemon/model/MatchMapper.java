package oit.is.team2.hogemon.model;

import java.util.ArrayList;

//import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
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

}
