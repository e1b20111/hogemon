INSERT INTO monsterbox (monstername,hp,attack,defence,skill1,skill2,skill3,skill4) VALUES ('スライム',10,5,3,'溶解液','とける','たいあたり','自己再生');
INSERT INTO monsterbox (monstername,hp,attack,defence,skill1,skill2,skill3,skill4) VALUES ('ドラゴン',20,10,5,'火炎放射','たたきつけ','つばさでうつ','だいもんじ');

INSERT INTO result (p1monstername,p2monstername,matchresult) VALUES ('スライム','ドラゴン','win');

INSERT INTO userInfo (username) VALUES ('user1');
INSERT INTO userInfo (username) VALUES ('user2');
INSERT INTO userInfo (username) VALUES ('CPU');
INSERT INTO userInfo (username) VALUES ('ジャクソン');

INSERT INTO matchInfo (p1monsterid,p2monsterid,p1monsterhp,p2monsterhp,damage) VALUES (0,2,0,20,0);

INSERT INTO skill (skillname, damage) VALUES ('溶解液', 5);
INSERT INTO skill (skillname, damage) VALUES ('とける', 7);
INSERT INTO skill (skillname, damage) VALUES ('たいあたり', 8);
INSERT INTO skill (skillname, damage) VALUES ('自己再生', 6);
INSERT INTO skill (skillname, damage) VALUES ('火炎放射', 7);
INSERT INTO skill (skillname, damage) VALUES ('たたきつけ', 4);
INSERT INTO skill (skillname, damage) VALUES ('つばさでうつ', 3);
INSERT INTO skill (skillname, damage) VALUES ('だいもんじ', 10);
