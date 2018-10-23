prompt PL/SQL Developer import file
prompt Created on 2012Äê6ÔÂ5ÈÕ by huang
set feedback off
set define off
prompt Creating S_ROLE_RESOURCE...
create table S_ROLE_RESOURCE
(
  ROLE_ID     NUMBER,
  RESOURCE_ID NUMBER
)
;
alter table S_ROLE_RESOURCE
  add constraint FK_RES_ID foreign key (RESOURCE_ID)
  references S_RESOURCE (ID);
alter table S_ROLE_RESOURCE
  add constraint FK_RO_ID foreign key (ROLE_ID)
  references S_ROLE (ID);

prompt Loading S_ROLE_RESOURCE...
insert into S_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values (1, 2);
insert into S_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values (1, 3);
insert into S_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values (1, 4);
insert into S_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values (1, 5);
insert into S_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values (1, 1);
commit;
prompt 5 records loaded
set feedback on
set define on
prompt Done.
