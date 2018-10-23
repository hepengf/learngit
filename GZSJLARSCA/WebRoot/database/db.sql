--myql
create database certs;

use certs;

--证书申请
create table un_cert_app(
	id int not null primary key  auto_increment,
	c varchar(50) not null,
	ou varchar(50),
	o varchar(50),
	st varchar(50),
	l varchar(50),
	cn varchar(50),
	net varchar(20),
	email varchar(20) 
)


--oracle
--序列
--证书申请表
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
  USERID       VARCHAR2(20)
)


--证书颁发表
create table UN_CERT_AWARD
(
  ID               NUMBER not null,
  USERID           VARCHAR2(20),
  CTYPE            NUMBER,
  FNAME            VARCHAR2(50),
  PATH             VARCHAR2(100),
  STATUS           NUMBER,
  AWARDDATE        DATE,
  SIGNOPERATOR     VARCHAR2(20),
  BATCH            VARCHAR2(10),
  DISABLEDOPERATOR VARCHAR2(20),
  DISABLEDDATE     DATE
)
tablespace VAEZ
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column UN_CERT_AWARD.USERID
  is '用户id';
comment on column UN_CERT_AWARD.CTYPE
  is '文件类型';
comment on column UN_CERT_AWARD.FNAME
  is '文件名';
comment on column UN_CERT_AWARD.PATH
  is '文件路径';
comment on column UN_CERT_AWARD.STATUS
  is '状态 1-正常 2-作废';
comment on column UN_CERT_AWARD.AWARDDATE
  is '签发日期';
comment on column UN_CERT_AWARD.SIGNOPERATOR
  is '签发人';
comment on column UN_CERT_AWARD.BATCH
  is '批次号';
comment on column UN_CERT_AWARD.DISABLEDOPERATOR
  is '作废操作人';
comment on column UN_CERT_AWARD.DISABLEDDATE
  is '作废日期';

--证书实体
create table UN_CERT
(
  ID           NUMBER not null,
  SUBJECTDN    VARCHAR2(100),
  ISSUREDN     VARCHAR2(100),--签发人
  SERIALNUMBER VARCHAR2(50),--序列号
  BEGINDATE    DATE,--起始日期
  ENDDATE      DATE,--截止日期
  PUBLICKEY    VARCHAR2(2000), --公钥
  PRIVATEKEY   VARCHAR2(2000),--私钥
  SIGALGNAME   VARCHAR2(50),--签名算法
  CTYPE        VARCHAR2(20),--类型
  VERSION      NUMBER,--证书版本
  USERID       VARCHAR2(20),--用户
  FNAME		   VARCHAR2(50）,--文件名
  BATCH        VARCHAR2(10)--批次号
)

create sequence SEQ_UNCERTAPP
minvalue 1
maxvalue 9999999999999999999
start with 1
increment by 1
cache 20;

create sequence SEQ_UNCERTAWARD
minvalue 1
maxvalue 9999999999999999999
start with 1
increment by 1
cache 20;

create sequence SEQ_UNCERT
minvalue 1
maxvalue 9999999999999999999
start with 1
increment by 1
cache 20;

--用户 角色  权限
select *
  from (select su.name     as username,
               su.password as pass,
               su.disabled as disabled,
               x.user_id as userid,
               x.role_id as roleid,
               x.name     as rolename
          from (select sur.user_id, sur.role_id, sr.name
                  from s_user_role sur
                  left join s_role sr on sur.role_id = sr.id) x
          left join s_user su on su.id = x.user_id) y
  left join s_role_resource srr on y.roleid = srr.role_id left join s_resource sre on sre.id = srr.resource_id 
  
--基础参数配置 
create table un_config(
       id int primary key not null auto_increment,
       class varchar(5),
       name varchar(20),
       value varchar(1),
       status int,
       createdate date,
       operator varchar(20),
       description varchar(50)
)
  