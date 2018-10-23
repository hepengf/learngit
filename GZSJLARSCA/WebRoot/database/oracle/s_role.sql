prompt PL/SQL Developer import file
prompt Created on 2012年6月5日 by huang
set feedback off
set define off
prompt Creating S_ROLE...
create table S_ROLE
(
  ID          NUMBER not null,
  NAME        VARCHAR2(20),
  DESCRIPTION VARCHAR2(50),
  STATUS      NUMBER,
  CREATEDATE  DATE,
  OPERATOR    VARCHAR2(25)
)
;
comment on column S_ROLE.ID
  is '自动增量';
comment on column S_ROLE.NAME
  is '角色名称';
comment on column S_ROLE.DESCRIPTION
  is '描述';
comment on column S_ROLE.STATUS
  is '0-启用 1-停用';
comment on column S_ROLE.CREATEDATE
  is '创建日期';
comment on column S_ROLE.OPERATOR
  is '创建人';
alter table S_ROLE
  add primary key (ID);

prompt Loading S_ROLE...
insert into S_ROLE (ID, NAME, DESCRIPTION, STATUS, CREATEDATE, OPERATOR)
values (1, 'ROLE_ADMIN', 'ROLE_ADMIN', 0, null, null);
insert into S_ROLE (ID, NAME, DESCRIPTION, STATUS, CREATEDATE, OPERATOR)
values (2, 'ROLE_USER', 'ROLE_USER', 0, null, null);
commit;
prompt 2 records loaded
set feedback on
set define on
prompt Done.
