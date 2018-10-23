prompt PL/SQL Developer import file
prompt Created on 2012Äê6ÔÂ5ÈÕ by huang
set feedback off
set define off
prompt Creating UN_CERT_APP...
create table UN_CERT_APP
(
  ID           NUMBER not null,
  C            VARCHAR2(50) not null,
  OU           VARCHAR2(50),
  O            VARCHAR2(50),
  ST           VARCHAR2(50),
  L            VARCHAR2(50),
  CN           VARCHAR2(50),
  NET          VARCHAR2(20),
  EMAIL        VARCHAR2(20),
  CREATEDATE   DATE,
  ISSIGN       NUMBER,
  SIGNOPERATOR VARCHAR2(20),
  SIGNDATE     DATE,
  USERID       VARCHAR2(20),
  BATCH        VARCHAR2(10)
)
;
alter table UN_CERT_APP
  add primary key (ID);

prompt Loading UN_CERT_APP...
insert into UN_CERT_APP (ID, C, OU, O, ST, L, CN, NET, EMAIL, CREATEDATE, ISSIGN, SIGNOPERATOR, SIGNDATE, USERID, BATCH)
values (1050, 'CN', 'CONGOAL', 'IT', 'GD', 'GZ', 'ZD', null, null, to_date('29-05-2012 14:40:54', 'dd-mm-yyyy hh24:mi:ss'), 1, null, to_date('29-05-2012 15:07:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', null);
commit;
prompt 1 records loaded
set feedback on
set define on
prompt Done.
