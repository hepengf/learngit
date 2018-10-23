create table S_RESOURCE
(
  ID         INT PRIMARY KEY not null AUTO_INCREMENT,
  TYPE       VARCHAR(200),
  VALUE      VARCHAR(500),
  NAME       VARCHAR(50),
  DESCVAL   VARCHAR(100),
  STATUS     INT,
  CREATEDATE DATE,
  OPERATOR   VARCHAR(25),
  PID        INT,
  IMGURL  VARCHAR(100),
  ORDERNUM INT
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
comment on column S_RESOURCE.DESCVAL
  is '描述';
comment on column S_RESOURCE.STATUS
  is '0-启用 1-停用';
comment on column S_RESOURCE.CREATEDATE
  is '创建日期';
comment on column S_RESOURCE.OPERATOR
  is '创建人';
comment on column S_RESOURCE.PID
  is '上级资源id';
  
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCVAL, STATUS, CREATEDATE, OPERATOR, PID)
values (1, 'URL', '/index.action', '主页', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCVAL, STATUS, CREATEDATE, OPERATOR, PID)
values (2, 'URL', '/web/certAppAction.action', '证书申请提交', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCVAL, STATUS, CREATEDATE, OPERATOR, PID)
values (3, 'URL', '/web/findUnSignCertAction.action', '待签发证书查询', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCVAL, STATUS, CREATEDATE, OPERATOR, PID)
values (4, 'URL', '/web/findSignCertAction.action', '已签发证书查询', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCVAL, STATUS, CREATEDATE, OPERATOR, PID)
values (5, 'URL', '/web/signCertAction.action', '签发证书', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCVAL, STATUS, CREATEDATE, OPERATOR, PID)
values (7, 'URL', '/web/disabledCertAction.action', '作废证书', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCVAL, STATUS, CREATEDATE, OPERATOR, PID)
values (6, 'URL', '/web/disabledCertCheckAction.action', '待作废证书查询', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCVAL, STATUS, CREATEDATE, OPERATOR, PID)
values (8, 'URL', '/web/CertAppRequestAction.action', '证书申请', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCVAL, STATUS, CREATEDATE, OPERATOR, PID)
values (9, 'URL', '/web/certDownloadAction.action', '证书下载', null, 0, null, null, null);
commit;