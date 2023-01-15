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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.team2.hogemon.model.Room;
import oit.is.team2.hogemon.model.Monster;
import oit.is.team2.hogemon.model.MonsterMapper;
import oit.is.team2.hogemon.model.result;
import oit.is.team2.hogemon.model.resultMapper;
import oit.is.team2.hogemon.model.User;
import oit.is.team2.hogemon.model.UserMapper;
//import oit.is.team2.hogemon.model.Skill;
import oit.is.team2.hogemon.model.SkillMapper;
import oit.is.team2.hogemon.model.Match;
import oit.is.team2.hogemon.model.MatchMapper;
import oit.is.team2.hogemon.service.AsyncMatch;

/**
 * /sample3へのリクエストを扱うクラス authenticateの設定をしていれば， /sample3へのアクセスはすべて認証が必要になる
 */
@Controller
public class HogemonController {

  @Autowired
  private Room room;
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
  @Autowired
  AsyncMatch match;

  @GetMapping("/")
  public String Index(Principal prin) {
    String battleenduser = prin.getName();
    Match nowbattle = MaMapper.selectLastData();
    if (nowbattle.getId() == 1) {
      return "index.html";
    }
    if (nowbattle.getP1monsterhp() <= 0 || nowbattle.getP2monsterhp() <= 0) {
      if (nowbattle.getP1name().equals(battleenduser) || nowbattle.getP2name().equals(battleenduser)) {
        MaMapper.deleteAll();
        MaMapper.insertTest();
      }
    }
    return "index.html";
  }

  @GetMapping("battle")
  public String Battle(ModelMap model, Principal prin) {
    String user = prin.getName();
    User cpu = UMapper.selectUserByName("CPU");
    room.addUser(cpu);
    User myuser = UMapper.selectUserByName(user);
    room.addUser(myuser);
    model.addAttribute("myuser", user);
    model.addAttribute("user", room.getUsers());
    return "battle.html";
  }

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
  public String battle_post(@RequestParam int number, ModelMap model, Principal prin) {
    Monster monster = MMapper.selectMonsterById(number);
    String user = prin.getName();
    User cpu = UMapper.selectUserByName("CPU");
    room.addUser(cpu);
    User myuser = UMapper.selectUserByName(user);
    room.addUser(myuser);
    model.addAttribute("myuser", user);
    model.addAttribute("user", room.getUsers());
    model.addAttribute("monster", monster);
    return "battle.html";
  }

  @GetMapping("match")
  public String match_post(@RequestParam Integer monsterId, @RequestParam Integer userId, ModelMap model,
      Principal prin) {

    String p1User = prin.getName(); // ログインユーザ情報
    Monster p1monster = MMapper.selectMonsterById(monsterId);
    User p2User = UMapper.selectUserById(userId);

    this.match.syncEntryMatch(p1User, p1monster, p2User);

    model.addAttribute("p2user", p2User);
    model.addAttribute("p1monster", p1monster);
    model.addAttribute("p1user", p1User);
    return "match.html";
  }

  @GetMapping("wait")
  public String wait(@RequestParam String skillname, ModelMap model, Principal prin) {

    String user = prin.getName();
    // Mapperが多くてややこしいが、要はmatchinfoのid=1での二つ(p1とp2)のmonster情報を呼び出してくる処理
    // matchinfoとmonsterの区別をするために別の変数とする。
    // monster情報のため、match中には変更しない。
    Monster p1monster = MMapper.selectMonsterById(MaMapper.selectFirstP1MonsterId());
    Monster p2monster = MMapper.selectMonsterById(MaMapper.selectFirstP2MonsterId());
    Match lastdata = MaMapper.selectLastData();
    model.addAttribute("lastdata", lastdata);

    // ダメージ処理等と試合情報データベース関連の処理
    final Match matchinfo = this.match.syncDamageMatch(skillname, user);

    if (matchinfo.getP1name().equals(user)) {
      Monster mymonster = MMapper.selectMonsterById(MaMapper.selectFirstP1MonsterId());
      mymonster.setHp(matchinfo.getP1monsterhp());
      model.addAttribute("P1monster", mymonster);
    } else {
      Monster mymonster = MMapper.selectMonsterById(MaMapper.selectFirstP2MonsterId());
      mymonster.setHp(matchinfo.getP2monsterhp());
      model.addAttribute("P2monster", mymonster);
    }

    if (matchinfo.getP2name().equals("CPU")) {
      model.addAttribute("battleCPU", "CPU");
    }

    // 試合結果の処理
    result P1result = new result();
    P1result.setP1monstername(p1monster.getMonstername());
    P1result.setP2monstername(p2monster.getMonstername());

    if (matchinfo.getP2monsterhp() <= 0) {
      P1result.setMatchresult("Win");
      RMapper.insertResult(P1result);
      model.addAttribute("gameend", P1result);
      return "wait.html";
    }

    if (matchinfo.getP1monsterhp() <= 0) {
      P1result.setMatchresult("LOSE");
      RMapper.insertResult(P1result);
      model.addAttribute("gameend", P1result);
      return "wait.html";
    }

    // データ更新ないしは追加後、試合データ読み込み
    final ArrayList<Match> matches = match.syncShowMatches();
    // Match lastmatch = MaMapper.selectLastData();
    // model.addAttribute("lastmatch", lastmatch);
    model.addAttribute("matchinfo", matches);

    return "wait.html";
  }

  @GetMapping("batreq")
  public SseEmitter BattleRequest(Principal prin) {
    String user = prin.getName();
    final SseEmitter sseEmitter = new SseEmitter();
    this.match.asyncShowBattleRequest(sseEmitter, user);
    return sseEmitter;
  }

  @GetMapping("entry")
  public SseEmitter Entry() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.match.asyncShowMatches(sseEmitter);
    return sseEmitter;
  }

  @GetMapping("battling")
  public SseEmitter Battling() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.match.asyncShowMatches(sseEmitter);
    return sseEmitter;
  }

}
