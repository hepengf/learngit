prompt PL/SQL Developer import file
prompt Created on 2012年6月5日 by huang
set feedback off
set define off
prompt Creating S_RESOURCE...
create table S_RESOURCE
(
  ID         NUMBER not null,
  TYPE       VARCHAR2(200),
  VALUE      VARCHAR2(500),
  NAME       VARCHAR2(50),
  DESCRIBE   VARCHAR2(100),
  STATUS     NUMBER,
  CREATEDATE DATE,
  OPERATOR   VARCHAR2(25),
  PID        NUMBER
)
;
comment on column S_RESOURCE.ID
  is '自动增量';
comment on column S_RESOURCE.TYPE
  is '类型';
comment on column S_RESOURCE.VALUE
  is '资源值';
comment on column S_RESOURCE.NAME
  is '资源名称';
comment on column S_RESOURCE.DESCRIBE
  is '描述';
comment on column S_RESOURCE.STATUS
  is '0-启用 1-停用';
comment on column S_RESOURCE.CREATEDATE
  is '创建日期';
comment on column S_RESOURCE.OPERATOR
  is '创建人';
comment on column S_RESOURCE.PID
  is '上级资源id';
alter table S_RESOURCE
  add primary key (ID);

prompt Loading S_RESOURCE...
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (1, 'URL', '/index.action', '主页', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (2, 'URL', '/web/certAppAction.action', '证书申请提交', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (3, 'URL', '/web/findUnSignCertAction.action', '待签发证书查询', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (4, 'URL', '/web/findSignCertAction.action', '已签发证书查询', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (5, 'URL', '/web/signCertAction.action', '签发证书', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (7, 'URL', '/web/disabledCertAction.action', '作废证书', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (6, 'URL', '/web/disabledCertCheckAction.action', '待作废证书查询', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (8, 'URL', '/web/CertAppRequestAction.action', '证书申请', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (9, 'URL', '/web/certDownloadAction.action', '证书下载', null, 0, null, null, null);
commit;
prompt 9 records loaded
set feedback on
set define on
prompt Done.
