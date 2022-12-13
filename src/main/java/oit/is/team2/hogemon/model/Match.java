package oit.is.team2.hogemon.model;

public class Match {
  int id;
  int mymonsterid;
  int mymonsterhp;
  String skill;
  int enemymonsterid;
  int enemymonsterhp;
  int damage;

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getMymonsterid() {
    return mymonsterid;
  }
  public void setMymonsterid(int mymonsterid) {
    this.mymonsterid = mymonsterid;
  }
  public int getMymonsterhp() {
    return mymonsterhp;
  }
  public void setMymonsterhp(int mymonsterhp) {
    this.mymonsterhp = mymonsterhp;
  }
  public String getSkill() {
    return skill;
  }
  public void setSkill(String skill) {
    this.skill = skill;
  }
  public int getEnemymonsterid() {
    return enemymonsterid;
  }
  public void setEnemymonsterid(int enemymonsterid) {
    this.enemymonsterid = enemymonsterid;
  }
  public int getEnemymonsterhp() {
    return enemymonsterhp;
  }
  public void setEnemymonsterhp(int enemymonsterhp) {
    this.enemymonsterhp = enemymonsterhp;
  }
  public int getDamage() {
    return damage;
  }
  public void setDamage(int damage) {
    this.damage = damage;
  }

}
