prompt PL/SQL Developer import file
prompt Created on 2012Äê6ÔÂ5ÈÕ by huang
set feedback off
set define off
prompt Creating S_USER...
create table S_USER
(
  ID         NUMBER not null,
  NAME       VARCHAR2(20),
  PASSWORD   VARCHAR2(20),
  DISABLED   CHAR(1),
  FULLNAME   VARCHAR2(50),
  CREATEDATE DATE,
  TELEPHONE  VARCHAR2(30),
  PHONE      VARCHAR2(15),
  ZIPCODE    VARCHAR2(10),
  ADDRESS    VARCHAR2(50),
  FAX        VARCHAR2(15),
  ENGNAME    VARCHAR2(20)
)
;
alter table S_USER
  add primary key (ID);

prompt Loading S_USER...
insert into S_USER (ID, NAME, PASSWORD, DISABLED, FULLNAME, CREATEDATE, TELEPHONE, PHONE, ZIPCODE, ADDRESS, FAX, ENGNAME)
values (1, 'admin', '123', '1', null, null, null, null, null, null, null, null);
insert into S_USER (ID, NAME, PASSWORD, DISABLED, FULLNAME, CREATEDATE, TELEPHONE, PHONE, ZIPCODE, ADDRESS, FAX, ENGNAME)
values (2, 'user', '123', '1', null, null, null, null, null, null, null, null);
insert into S_USER (ID, NAME, PASSWORD, DISABLED, FULLNAME, CREATEDATE, TELEPHONE, PHONE, ZIPCODE, ADDRESS, FAX, ENGNAME)
values (3, 'json', '123', '1', null, null, null, null, null, null, null, null);
commit;
prompt 3 records loaded
set feedback on
set define on
prompt Done.
