/* userやgroupといった名前はSQLでは予約語で使えないため，userNameとしていることに注意 */
CREATE TABLE monsterbox (
    id IDENTITY,
    monstername VARCHAR NOT NULL,
    hp INT,
    attack INT,
    defence INT,
    skill1 VARCHAR,
    skill2 VARCHAR,
    skill3 VARCHAR,
    skill4 VARCHAR
);

CREATE TABLE result (
    p1monstername VARCHAR NOT NULL ,
    p2monstername VARCHAR NOT NULL ,
    matchresult VARCHAR NOT NULL
);

CREATE TABLE userInfo (
    id IDENTITY,
    username VARCHAR NOT NULL
);

CREATE TABLE matchInfo (
    id IDENTITY,
    p1name VARCHAR,
    p1monsterid INT,
    p1monsterhp INT,
    skill VARCHAR,
    p2name VARCHAR,
    p2monsterid INT,
    p2monsterhp INT,
    damage INT,
    attackplayer VARCHAR
);

CREATE TABLE skill (
    skillname VARCHAR,
    damage INT
);
