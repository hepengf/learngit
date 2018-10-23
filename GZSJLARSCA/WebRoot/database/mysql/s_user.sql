create table S_USER
(
  ID         INT PRIMARY KEY not null AUTO_INCREMENT,
  NAME       VARCHAR(20),
  PASSWORD   VARCHAR(20),
  DISABLED   VARCHAR(1),
  FULLNAME   VARCHAR(50),
  CREATEDATE DATE,
  TELEPHONE  VARCHAR(30),
  PHONE      VARCHAR(15),
  ZIPCODE    VARCHAR(10),
  ADDRESS    VARCHAR(50),
  FAX        VARCHAR(15),
  ENGNAME    VARCHAR(20),
  orgid 	VARCHAR(20),
  lastdisableddate  DATE,
  disabledcout INT,
  lastdisabledoperator VARCHAR(20)
)
;

insert into S_USER (ID, NAME, PASSWORD, DISABLED, FULLNAME, CREATEDATE, TELEPHONE, PHONE, ZIPCODE, ADDRESS, FAX, ENGNAME)
values (1, 'admin', '123', '1', null, null, null, null, null, null, null, null);
insert into S_USER (ID, NAME, PASSWORD, DISABLED, FULLNAME, CREATEDATE, TELEPHONE, PHONE, ZIPCODE, ADDRESS, FAX, ENGNAME)
values (2, 'user', '123', '1', null, null, null, null, null, null, null, null);
insert into S_USER (ID, NAME, PASSWORD, DISABLED, FULLNAME, CREATEDATE, TELEPHONE, PHONE, ZIPCODE, ADDRESS, FAX, ENGNAME)
values (3, 'json', '123', '1', null, null, null, null, null, null, null, null);
commit;

