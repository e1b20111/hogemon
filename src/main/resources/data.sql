INSERT INTO monsterbox (monstername,hp,attack,defence,skill1,skill2,skill3,skill4) VALUES ('スライム',40,4,4,'溶解液','スラバーン','たいあたり','へばりつく');
INSERT INTO monsterbox (monstername,hp,attack,defence,skill1,skill2,skill3,skill4) VALUES ('ドラゴン',60,10,6,'火炎放射','たたきつけ','つばさでうつ','だいもんじ');
INSERT INTO monsterbox (monstername,hp,attack,defence,skill1,skill2,skill3,skill4) VALUES ('ゴーレム',100,5,10,'のしかかり','ショックウェーブ','鉄拳','ふみつぶし');


INSERT INTO userInfo (username) VALUES ('user1');
INSERT INTO userInfo (username) VALUES ('user2');
INSERT INTO userInfo (username) VALUES ('user3');
INSERT INTO userInfo (username) VALUES ('user4');
INSERT INTO userInfo (username) VALUES ('CPU');
INSERT INTO userInfo (username) VALUES ('ジャクソン');

INSERT INTO matchInfo (p1monsterid,p2monsterid,p1monsterhp,p2monsterhp,damage) VALUES (0,0,3,50,0);

INSERT INTO skill (skillname, damage) VALUES ('溶解液', 0.8);
INSERT INTO skill (skillname, damage) VALUES ('スラバーン', 10);
INSERT INTO skill (skillname, damage) VALUES ('たいあたり', 8);
INSERT INTO skill (skillname, damage) VALUES ('へばりつく', 0.9);
INSERT INTO skill (skillname, damage) VALUES ('火炎放射', 0.9);
INSERT INTO skill (skillname, damage) VALUES ('たたきつけ', 8);
INSERT INTO skill (skillname, damage) VALUES ('つばさでうつ', 9);
INSERT INTO skill (skillname, damage) VALUES ('だいもんじ', 12);
INSERT INTO skill (skillname, damage) VALUES ('のしかかり', 6);
INSERT INTO skill (skillname, damage) VALUES ('ショックウェーブ', 0.9);
INSERT INTO skill (skillname, damage) VALUES ('鉄拳', 5);
INSERT INTO skill (skillname, damage) VALUES ('ふみつぶし', 8);
