/* userやgroupといった名前はSQLでは予約語で使えないため，userNameとしていることに注意 */
CREATE TABLE monsterbox (
    id IDENTITY,
    monsterName VARCHAR NOT NULL,
    hp INT,
    attack INT,
    defence INT,
    skill1 VARCHAR,
    skill2 VARCHAR,
    skill3 VARCHAR,
    skill4 VARCHAR
);

CREATE TABLE result (
    mymonsterName VARCHAR NOT NULL ,
    enemymonsterName VARCHAR NOT NULL ,
    matchresult VARCHAR NOT NULL
);

CREATE TABLE userInfo (
    id IDENTITY,
    userName VARCHAR NOT NULL
);

CREATE TABLE matchInfo (
    id IDENTITY,
    mymonsterid INT,
    mymonsterhp INT,
    skill VARCHAR,
    enemymonsterid INT,
    enemymonsterhp INT,
    damage INT
);

CREATE TABLE skill (
    skillName VARCHAR,
    damage INT
);
