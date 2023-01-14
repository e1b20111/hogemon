INSERT INTO monsterbox (monstername,hp,attack,defence,skill1,skill2,skill3,skill4) VALUES ('スライム',20,5,3,'溶解液','とける','たいあたり','自己再生');
INSERT INTO monsterbox (monstername,hp,attack,defence,skill1,skill2,skill3,skill4) VALUES ('ドラゴン',30,10,5,'火炎放射','たたきつけ','つばさでうつ','だいもんじ');
INSERT INTO monsterbox (monstername,hp,attack,defence,skill1,skill2,skill3,skill4) VALUES ('ゴーレム',50,5,5,'のしかかり','ショックウェーブ','鉄拳','ふみつぶし');


INSERT INTO userInfo (username) VALUES ('user1');
INSERT INTO userInfo (username) VALUES ('user2');
INSERT INTO userInfo (username) VALUES ('CPU');
INSERT INTO userInfo (username) VALUES ('ジャクソン');

INSERT INTO matchInfo (p1monsterid,p2monsterid,p1monsterhp,p2monsterhp,damage) VALUES (0,0,3,50,0);

INSERT INTO skill (skillname, damage) VALUES ('溶解液', 5);
INSERT INTO skill (skillname, damage) VALUES ('とける', 7);
INSERT INTO skill (skillname, damage) VALUES ('たいあたり', 8);
INSERT INTO skill (skillname, damage) VALUES ('自己再生', 6);
INSERT INTO skill (skillname, damage) VALUES ('火炎放射', 7);
INSERT INTO skill (skillname, damage) VALUES ('たたきつけ', 4);
INSERT INTO skill (skillname, damage) VALUES ('つばさでうつ', 3);
INSERT INTO skill (skillname, damage) VALUES ('だいもんじ', 10);
INSERT INTO skill (skillname, damage) VALUES ('のしかかり', 7);
INSERT INTO skill (skillname, damage) VALUES ('ショックウェーブ', 4);
INSERT INTO skill (skillname, damage) VALUES ('鉄拳', 3);
INSERT INTO skill (skillname, damage) VALUES ('ふみつぶし', 6);
