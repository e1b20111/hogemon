package oit.is.team2.hogemon.model;

import java.util.ArrayList;
//import oit.is.team2.hogemon.model.User;

import org.springframework.stereotype.Component;

@Component
public class Room {
  ArrayList<User> users = new ArrayList<>();
  int roomNo = 1;

  public void addUser(User user) {
    // 同名のユーザが居たら何もせずにreturn
    for (User s : this.users) {
      if (s.getUsername().equals(user.getUsername())) {
        return;
      }
    }
    // 同名のユーザが居なかった場合はusersにnameを追加する
    this.users.add(user);
  }

  // 以降はフィールドのgetter/setter
  // これらがないとThymeleafで値を取得できない
  public int getRoomNo() {
    return roomNo;
  }

  public void setRoomNo(int roomNo) {
    this.roomNo = roomNo;
  }

  public ArrayList<User> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<User> users) {
    this.users = users;
  }

}
