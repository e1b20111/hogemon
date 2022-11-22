package oit.is.team2.hogemon.controller;

//import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import oit.is.team2.hogemon.model.Monster;
import oit.is.team2.hogemon.model.MonsterMapper;

/**
 * /sample3へのリクエストを扱うクラス authenticateの設定をしていれば， /sample3へのアクセスはすべて認証が必要になる
 */
@Controller
public class HogemonController {

  @Autowired
  MonsterMapper MMapper;

  @GetMapping("battle")
  public String Battle() {
    return "battle.html";
  }

  @GetMapping("result")
  public String Result() {
    return "result.html";
  }

  @GetMapping("monsterbox")
  public String Monsterbox(ModelMap model) {
    ArrayList<Monster> monster = MMapper.selectAllMonsters();
    model.addAttribute("monster", monster);
    return "monsterbox.html";
  }
}
