package oit.is.team2.hogemon.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.team2.hogemon.model.Monster;
import oit.is.team2.hogemon.model.MonsterMapper;
import oit.is.team2.hogemon.model.result;
import oit.is.team2.hogemon.model.resultMapper;
import oit.is.team2.hogemon.model.User;
import oit.is.team2.hogemon.model.UserMapper;
import oit.is.team2.hogemon.model.Skill;
import oit.is.team2.hogemon.model.SkillMapper;
import oit.is.team2.hogemon.model.Match;
import oit.is.team2.hogemon.model.MatchMapper;

/**
 * /sample3へのリクエストを扱うクラス authenticateの設定をしていれば， /sample3へのアクセスはすべて認証が必要になる
 */
@Controller
public class HogemonController {

  @Autowired
  MonsterMapper MMapper;
  @Autowired
  resultMapper RMapper;
  @Autowired
  UserMapper UMapper;
  @Autowired
  SkillMapper SMapper;
  @Autowired
  MatchMapper MaMapper;

  @GetMapping("battle")
  public String Battle(ModelMap model) {
    ArrayList<User> user = UMapper.selectAllUsers();
    model.addAttribute("user", user);
    return "battle.html";
  }

  // @GetMapping("match")
  // public String Match() {
  // return "match.html";
  // }

  @GetMapping("monsterbox")
  public String Monsterbox(ModelMap model) {
    ArrayList<Monster> monster = MMapper.selectAllMonsters();
    model.addAttribute("monster", monster);
    return "monsterbox.html";
  }

  @GetMapping("result")
  public String result(ModelMap model) {
    ArrayList<result> result = RMapper.selectAllresults();
    model.addAttribute("result", result);
    return "result.html";
  }

  @PostMapping("battle")
  public String battle_post(@RequestParam int number, ModelMap model) {
    Monster monster = MMapper.selectMonsterById(number);
    ArrayList<User> user = UMapper.selectAllUsers();
    model.addAttribute("user", user);
    model.addAttribute("monster", monster);
    return "battle.html";
  }

  @GetMapping("match")
  public String match_post(@RequestParam Integer monsterId, @RequestParam Integer userId, ModelMap model,
      Principal prin) {

    String myUser = prin.getName(); // ログインユーザ情報
    Monster mymonster = MMapper.selectMonsterById(monsterId);
    User enemyUser = UMapper.selectUserById(userId);

    if (MaMapper.selectFirstMyMonsterId() == 0) {
      MaMapper.updateFirstPlayer(monsterId, mymonster.getHp());
    } else {
      MaMapper.updateSecondPlayer(monsterId, mymonster.getHp());
    }
    model.addAttribute("enemyuser", enemyUser);
    model.addAttribute("mymonster", mymonster);
    model.addAttribute("myuser", myUser);
    model.addAttribute("enemyuser", enemyUser);
    return "match.html";
  }

  @GetMapping("wait")
  public String wait(@RequestParam String skillName, ModelMap model) {

    // Mapperが多くてややこしいが、要はmatchinfoのid=1での二つ(myとenemy)のmonster情報を呼び出してくる処理
    // matchinfoとmonsterの区別をするために別の変数とする。
    // monster情報のため、match中には変更しない。
    Monster mymonster = MMapper.selectMonsterById(MaMapper.selectFirstMyMonsterId());
    Monster enemymonster = MMapper.selectMonsterById(MaMapper.selectFirstEnemyMonsterId());
    model.addAttribute("mymonster", mymonster);
    model.addAttribute("enemymonster", enemymonster);

    // ダメージ関連処理（現在はskillごとに決められたダメージ)
    // requestparamで送られてきたskillnameに対応したskill
    Skill skill = SMapper.selectSkillByName(skillName);
    // {skill}やステータスに応じたダメージ処理

    // 最新のデータ取得
    Match matchinfo = MaMapper.selectLastData();
    // 初回のみmatchinfoのid=1のデータを更新する。2回目以降はinsertしていく。
    if (MaMapper.selectFirstSkill() == null) {
      MaMapper.updateFirstDamage(skillName, skill.getDamage());
    } else {
      // ダメージとスキル値とHPの更新と追加
      matchinfo.setDamage(skill.getDamage());
      matchinfo.setEnemymonsterhp(matchinfo.getEnemymonsterhp() - skill.getDamage());
      matchinfo.setSkill(skillName);
      MaMapper.insertMatch(matchinfo);
    }
    matchinfo = MaMapper.selectLastData();

    // データ更新ないしは追加後、試合データ読み込み
    ArrayList<Match> matches = MaMapper.selectAllMatches();
    model.addAttribute("matchinfo", matches);
    model.addAttribute("mymonsterhp", matchinfo.getMymonsterhp());
    model.addAttribute("enemymonsterhp", matchinfo.getEnemymonsterhp());
    model.addAttribute("skill", skill);

    if (matchinfo.getEnemymonsterhp() == 0) {
      model.addAttribute("matchinfo", matches);
    }
    return "wait.html";
  }

}
