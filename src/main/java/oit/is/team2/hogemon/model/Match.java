package oit.is.team2.hogemon.model;

public class Match {
  int id;
  String p1name;
  int p1monsterid;
  int p1monsterhp;
  String skill;
  String p2name;
  int p2monsterid;
  int p2monsterhp;
  int damage;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getP1name() {
    return p1name;
  }

  public void setP1name(String p1name) {
    this.p1name = p1name;
  }

  public int getP1monsterid() {
    return p1monsterid;
  }

  public void setP1monsterid(int p1monsterid) {
    this.p1monsterid = p1monsterid;
  }

  public int getP1monsterhp() {
    return p1monsterhp;
  }

  public void setP1monsterhp(int p1monsterhp) {
    this.p1monsterhp = p1monsterhp;
  }

  public String getSkill() {
    return skill;
  }

  public void setSkill(String skill) {
    this.skill = skill;
  }

  public String getP2name() {
    return p2name;
  }

  public void setP2name(String p2name) {
    this.p2name = p2name;
  }

  public int getP2monsterid() {
    return p2monsterid;
  }

  public void setP2monsterid(int p2monsterid) {
    this.p2monsterid = p2monsterid;
  }

  public int getP2monsterhp() {
    return p2monsterhp;
  }

  public void setP2monsterhp(int p2monsterhp) {
    this.p2monsterhp = p2monsterhp;
  }

  public int getDamage() {
    return damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

}
