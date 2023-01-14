package oit.is.team2.hogemon.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.team2.hogemon.model.Monster;
import oit.is.team2.hogemon.model.MonsterMapper;
//import oit.is.team2.hogemon.model.result;
import oit.is.team2.hogemon.model.resultMapper;
import oit.is.team2.hogemon.model.User;
import oit.is.team2.hogemon.model.UserMapper;
import oit.is.team2.hogemon.model.Skill;
import oit.is.team2.hogemon.model.SkillMapper;
import oit.is.team2.hogemon.model.Match;
import oit.is.team2.hogemon.model.MatchMapper;

@Service
public class AsyncMatch {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncMatch.class);

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

  @Transactional
  public Match syncDamageMatch(String skillname, String pname) {

    // ダメージ関連処理（現在はskillごとに決められたダメージ)
    // requestparamで送られてきたskillnameに対応したskill
    Skill skill = SMapper.selectSkillByName(skillname);
    // {skill}やステータスに応じたダメージ処理

    // 最新のデータ取得
    Match matchinfo = MaMapper.selectLastData();
    matchinfo.setAttackp(pname);
    // 初手が1Pの時の処理
    if (matchinfo.getP1name().equals(pname)) {
      // 初回のみmatchinfoのid=1のデータを更新する。2回目以降はinsertしていく。
      if (matchinfo.getDamage() == 0) {
        MaMapper.updateFirstDamage(skillname, skill.getDamage(), pname);
      } else {
        // ダメージとスキル値とHPの更新と追加
        matchinfo.setDamage(skill.getDamage());
        matchinfo.setP2monsterhp(matchinfo.getP2monsterhp() - skill.getDamage());
        matchinfo.setSkill(skillname);
        MaMapper.insertMatch(matchinfo);
      }
      // 初手が2Pの時の処理
    } else if (matchinfo.getP2name().equals(pname)) {
      // 初回のみmatchinfoのid=1のデータを更新する。2回目以降はinsertしていく。
      if (matchinfo.getDamage() == 0) {
        MaMapper.updateFirstDamageByP2(skillname, skill.getDamage(), pname);
      } else {
        // ダメージとスキル値とHPの更新と追加
        matchinfo.setDamage(skill.getDamage());
        matchinfo.setP1monsterhp(matchinfo.getP1monsterhp() - skill.getDamage());
        matchinfo.setSkill(skillname);
        MaMapper.insertMatch(matchinfo);
      }
    }

    // CPUとの対戦時
    if (matchinfo.getP2name().equals("CPU")) {
      battlCPU();
    }
    matchinfo = MaMapper.selectLastData();

    // 非同期でDB更新したことを共有する際に利用する
    this.dbUpdated = true;

    // return Match;
    return matchinfo;

  }

  // CPUとの対戦処理
  public void battlCPU() {
    Match lastdata = MaMapper.selectLastData();
    Monster CPUmonster = MMapper.selectMonsterById(lastdata.getP2monsterid());
    Skill skill = new Skill();
    // CPU側の処理
    Random rand = new Random();
    int num = rand.nextInt(4);
    switch (num) {
      case 0:
        skill = SMapper.selectSkillByName(CPUmonster.getSkill1());
        break;
      case 1:
        skill = SMapper.selectSkillByName(CPUmonster.getSkill2());
        break;
      case 2:
        skill = SMapper.selectSkillByName(CPUmonster.getSkill3());
        break;
      case 3:
        skill = SMapper.selectSkillByName(CPUmonster.getSkill4());
        break;
    }
    lastdata.setP1monsterhp(lastdata.getP1monsterhp() - skill.getDamage());
    lastdata.setDamage(skill.getDamage());
    lastdata.setSkill(skill.getSkillname());
    lastdata.setAttackp("CPU");
    MaMapper.insertMatch(lastdata);
  }

  @Transactional
  public void syncEntryMatch(String p1user, Monster mymonster, User p2user) {
    if (MaMapper.selectFirstP1MonsterId() == 0) {
      MaMapper.updateFirstPlayer(p1user, p2user.getUsername(), mymonster.getId(), mymonster.getMonstername(),
          mymonster.getHp());
      if (p2user.getUsername().equals("CPU")) {
        Random rand = new Random();
        int num = rand.nextInt(MMapper.countMonsters()) + 1;
        Monster cpumonster = MMapper.selectMonsterById(num);
        MaMapper.updateSecondPlayer(num, cpumonster.getMonstername(), cpumonster.getHp());
      }
    } else {
      MaMapper.updateSecondPlayer(mymonster.getId(), mymonster.getMonstername(), mymonster.getHp());
    }

    // データベースが更新されたことの把握
    this.dbUpdated = true;
    return;
  }

  public ArrayList<Match> syncShowMatches() {
    return MaMapper.selectAllMatches();
  }

  @Async
  public void asyncShowMatches(SseEmitter emitter) {

    dbUpdated = true;
    try {
      while (true) {// 無限ループ
        // DBが更新されていなければ0.01s休み
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(10);
          continue;
        }
        // DBが更新されていれば更新後のフルーツリストを取得してsendし，0.01s休み，dbUpdatedをfalseにする
        ArrayList<Match> matches = this.syncShowMatches();
        emitter.send(matches);
        TimeUnit.MILLISECONDS.sleep(10);
        dbUpdated = false;
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowMatches complete");
  }

}
