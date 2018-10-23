CREATE TABLE `un_cert_app` (
  `id` int(11) NOT NULL auto_increment,
  `batch` varchar(255) default NULL,
  `c` varchar(255) default NULL,
  `cn` varchar(255) default NULL,
  `createdate` datetime default NULL,
  `email` varchar(255) default NULL,
  `isSign` int(11) default NULL,
  `l` varchar(255) default NULL,
  `net` varchar(255) default NULL,
  `o` varchar(255) default NULL,
  `ou` varchar(255) default NULL,
  `signOperator` varchar(255) default NULL,
  `signdate` datetime default NULL,
  `st` varchar(255) default NULL,
  `userid` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
);

insert into UN_CERT_APP (ID, C, OU, O, ST, L, CN, NET, EMAIL, CREATEDATE, ISSIGN, SIGNOPERATOR, SIGNDATE, USERID, BATCH)
values (1050, 'CN', 'CONGOAL', 'IT', 'GD', 'GZ', 'ZD', null, null, to_date('29-05-2012 14:40:54', 'dd-mm-yyyy hh24:mi:ss'), 1, null, to_date('29-05-2012 15:07:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', null);
commit;

