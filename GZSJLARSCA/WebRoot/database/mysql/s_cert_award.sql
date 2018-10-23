CREATE TABLE `un_cert_award` (
  `id` int(11) NOT NULL auto_increment,
  `awardDate` datetime default NULL,
  `batch` varchar(255) default NULL,
  `ctype` int(11) default NULL,
  `disableddate` datetime default NULL,
  `disabledoperator` varchar(255) default NULL,
  `fname` varchar(255) default NULL,
  `path` varchar(255) default NULL,
  `signOperator` varchar(255) default NULL,
  `status` int(11) default NULL,
  `userid` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
)



