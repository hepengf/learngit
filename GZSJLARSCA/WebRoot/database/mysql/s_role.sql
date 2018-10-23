create table S_ROLE
(
  ID          INT PRIMARY KEY not null AUTO_INCREMENT,
  NAME        VARCHAR(20),
  DESCRIPTION VARCHAR(50),
  STATUS      INT,
  CREATEDATE  DATE,
  OPERATOR    VARCHAR(25)
);


insert into S_ROLE (ID, NAME, DESCRIPTION, STATUS, CREATEDATE, OPERATOR)
values (1, 'ROLE_ADMIN', 'ROLE_ADMIN', 0, null, null);
insert into S_ROLE (ID, NAME, DESCRIPTION, STATUS, CREATEDATE, OPERATOR)
values (2, 'ROLE_USER', 'ROLE_USER', 0, null, null);
commit;
