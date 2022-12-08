INSERT INTO monsterbox (monstername,hp,attack,defence,skill1,skill2,skill3,skill4) VALUES ('スライム',10,5,3,'溶解液','とける','たいあたり','自己再生');
INSERT INTO monsterbox (monstername) VALUES ('ドラゴン');

INSERT INTO result (usermonsterName,enemymonsterName,matchresult) VALUES ('スライム','ドラゴン','win');

INSERT INTO userInfo (userName) VALUES ('CPU');
INSERT INTO userInfo (userName) VALUES ('ジャクソン');

INSERT INTO matchInfo (mymonsterid,enemymonsterid,mymonsterhp,enemymonsterhp,skill,damege) VALUES (1,2,10,100,'たいあたり',5);
