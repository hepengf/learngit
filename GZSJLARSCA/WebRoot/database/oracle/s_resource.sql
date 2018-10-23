prompt PL/SQL Developer import file
prompt Created on 2012��6��5�� by huang
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
  is '�Զ�����';
comment on column S_RESOURCE.TYPE
  is '����';
comment on column S_RESOURCE.VALUE
  is '��Դֵ';
comment on column S_RESOURCE.NAME
  is '��Դ����';
comment on column S_RESOURCE.DESCRIBE
  is '����';
comment on column S_RESOURCE.STATUS
  is '0-���� 1-ͣ��';
comment on column S_RESOURCE.CREATEDATE
  is '��������';
comment on column S_RESOURCE.OPERATOR
  is '������';
comment on column S_RESOURCE.PID
  is '�ϼ���Դid';
alter table S_RESOURCE
  add primary key (ID);

prompt Loading S_RESOURCE...
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (1, 'URL', '/index.action', '��ҳ', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (2, 'URL', '/web/certAppAction.action', '֤�������ύ', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (3, 'URL', '/web/findUnSignCertAction.action', '��ǩ��֤���ѯ', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (4, 'URL', '/web/findSignCertAction.action', '��ǩ��֤���ѯ', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (5, 'URL', '/web/signCertAction.action', 'ǩ��֤��', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (7, 'URL', '/web/disabledCertAction.action', '����֤��', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (6, 'URL', '/web/disabledCertCheckAction.action', '������֤���ѯ', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (8, 'URL', '/web/CertAppRequestAction.action', '֤������', null, 0, null, null, null);
insert into S_RESOURCE (ID, TYPE, VALUE, NAME, DESCRIBE, STATUS, CREATEDATE, OPERATOR, PID)
values (9, 'URL', '/web/certDownloadAction.action', '֤������', null, 0, null, null, null);
commit;
prompt 9 records loaded
set feedback on
set define on
prompt Done.
