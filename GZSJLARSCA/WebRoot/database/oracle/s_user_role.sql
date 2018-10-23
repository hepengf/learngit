prompt PL/SQL Developer import file
prompt Created on 2012Äê6ÔÂ5ÈÕ by huang
set feedback off
set define off
prompt Creating S_USER_ROLE...
create table S_USER_ROLE
(
  ROLE_ID NUMBER,
  USER_ID NUMBER
)
;
alter table S_USER_ROLE
  add constraint FK_R_ID foreign key (ROLE_ID)
  references S_ROLE (ID);
alter table S_USER_ROLE
  add constraint FK_U_ID foreign key (USER_ID)
  references S_USER (ID);

prompt Loading S_USER_ROLE...
insert into S_USER_ROLE (ROLE_ID, USER_ID)
values (1, 1);
insert into S_USER_ROLE (ROLE_ID, USER_ID)
values (2, 1);
insert into S_USER_ROLE (ROLE_ID, USER_ID)
values (2, 2);
commit;
prompt 3 records loaded
set feedback on
set define on
prompt Done.
