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
    monsterName VARCHAR NOT NULL PRIMARY KEY,
    matchresult VARCHAR NOT NULL
);
