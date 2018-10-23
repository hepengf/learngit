# Host: 127.0.0.1  (Version: 5.6.16)
# Date: 2015-01-13 09:49:54
# Generator: MySQL-Front 5.3  (Build 4.13)

/*!40101 SET NAMES utf8 */;

#
# Source for table "s_resource"
#

CREATE TABLE `s_resource` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(200) DEFAULT NULL,
  `VALUE` varchar(500) DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCVAL` varchar(100) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `CREATEDATE` date DEFAULT NULL,
  `OPERATOR` varchar(25) DEFAULT NULL,
  `PID` int(11) DEFAULT NULL,
  `IMGURL` varchar(100) DEFAULT NULL,
  `ORDERNUM` int(11) DEFAULT NULL,
  `isSign` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

#
# Data for table "s_resource"
#

INSERT INTO `s_resource` VALUES (1,'SUBLEFT','/index.action','个人信息',NULL,0,NULL,NULL,21,'web/img/left_tree1_4.gif',1,NULL),(2,'LEFT','/web/certAppAction.action','证书申请提交',NULL,0,NULL,NULL,8,'web/img/left_tree1.gif',10,NULL),(3,'SUBLEFT','/web/findUnSignCertAction.action','待签发证书查询',NULL,0,NULL,NULL,19,'web/img/left_tree3_1.gif',3,NULL),(4,'SUBLEFT','/web/findSignCertAction.action','已签发证书查询',NULL,0,NULL,NULL,19,'web/img/left_tree3_2.gif',4,NULL),(5,'MENU','/web/signCertAction.action','签发证书',NULL,0,NULL,NULL,3,'',10,NULL),(6,'SUBLEFT','/web/disabledCertCheckAction.action','作废证书申请',NULL,0,NULL,NULL,20,'web/img/left_tree5_1.gif',1,NULL),(7,'MENU','/web/disabledCertAction.action','作废证书',NULL,0,NULL,NULL,6,'',10,NULL),(8,'SUBLEFT','/web/CertAppRequestAction.action','子证书申请',NULL,0,NULL,NULL,18,'web/img/left_tree1.gif',2,NULL),(9,'MENU','/web/fileDownloadAction.action','证书下载',NULL,0,NULL,NULL,3,'',10,NULL),(10,'SUBLEFT','/web/','证书吊销列表管理',NULL,0,NULL,NULL,20,'web/img/left_tree5_2.gif',2,NULL),(11,'SUBLEFT','/web/','密钥导入',NULL,0,NULL,NULL,23,'web/img/left_tree2_1.gif',7,NULL),(12,'SUBLEFT','/web/','根证书导入',NULL,0,NULL,NULL,17,'web/img/left_tree2_2.gif',3,NULL),(13,'LEFT','/web/','吊销列表管理',NULL,0,NULL,NULL,20,'web/img/left_tree5_3.gif',3,NULL),(14,'SUBLEFT','/web/redirectRootCertAppAction.action','根证书申请',NULL,0,NULL,NULL,17,'web/img/left_tree1.gif',1,NULL),(15,'MENU','/web/rootCertAppAction.action','根证书申请提交',NULL,0,NULL,NULL,14,'',10,NULL),(16,'MENU','/web/certAppDetailAction.action','证书申请明细查询',NULL,0,NULL,NULL,3,'',1,NULL),(17,'LEFT','/web/','根证书管理',NULL,0,NULL,NULL,-1,'web/img/left_tree2.gif',2,NULL),(18,'LEFT','/web/','子证书管理',NULL,0,NULL,NULL,-1,'web/img/left_tree3.gif',3,NULL),(19,'LEFT','/web/','证书签发',NULL,0,NULL,NULL,-1,'web/img/left_tree4.gif',4,NULL),(20,'LEFT','/web/','证书吊销管理',NULL,0,NULL,NULL,-1,'web/img/left_tree5.gif',5,NULL),(21,'LEFT','/web/','我的工作台',NULL,0,NULL,NULL,-1,'web/img/left_tree6.gif',1,NULL),(22,'SUBLEFT','/web/redirectRootCertCheckAction.action','根证书申请审核',NULL,0,NULL,NULL,17,'web/img/left_tree2_3.gif',2,NULL),(23,'LEFT','/web/','密钥管理',NULL,0,NULL,NULL,-1,'web/img/left_tree7.gif',6,NULL),(24,'SUBLEFT','/web/redirectCertCheckAction.action','子证书申请审核',NULL,0,NULL,NULL,18,'web/img/left_tree1_5.gif',3,NULL),(25,'MEBU','/web/exportCertAction.action','导出证书',NULL,0,NULL,NULL,4,'',10,NULL);

#
# Source for table "s_role"
#

CREATE TABLE `s_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) DEFAULT NULL,
  `DESCRIPTION` varchar(50) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `CREATEDATE` date DEFAULT NULL,
  `OPERATOR` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Data for table "s_role"
#

INSERT INTO `s_role` VALUES (1,'ROLE_ADMIN','ROLE_ADMIN',0,NULL,NULL),(2,'ROLE_USER','ROLE_USER',0,NULL,NULL);

#
# Source for table "s_role_resource"
#

CREATE TABLE `s_role_resource` (
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`),
  KEY `FKE4A2D6CBA5BD9180` (`role_id`),
  KEY `FKE4A2D6CBAA629780` (`resource_id`),
  CONSTRAINT `FKE4A2D6CBA5BD9180` FOREIGN KEY (`role_id`) REFERENCES `s_role` (`ID`),
  CONSTRAINT `FKE4A2D6CBAA629780` FOREIGN KEY (`resource_id`) REFERENCES `s_resource` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "s_role_resource"
#

INSERT INTO `s_role_resource` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25);

#
# Source for table "s_user"
#

CREATE TABLE `s_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) DEFAULT NULL,
  `PASSWORD` varchar(20) DEFAULT NULL,
  `DISABLED` varchar(1) DEFAULT NULL,
  `FULLNAME` varchar(50) DEFAULT NULL,
  `CREATEDATE` date DEFAULT NULL,
  `TELEPHONE` varchar(30) DEFAULT NULL,
  `PHONE` varchar(15) DEFAULT NULL,
  `ZIPCODE` varchar(10) DEFAULT NULL,
  `ADDRESS` varchar(50) DEFAULT NULL,
  `FAX` varchar(15) DEFAULT NULL,
  `ENGNAME` varchar(20) DEFAULT NULL,
  `orgid` varchar(20) DEFAULT NULL,
  `lastdisableddate` date DEFAULT NULL,
  `disabledcout` int(11) DEFAULT NULL,
  `lastdisabledoperator` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "s_user"
#

INSERT INTO `s_user` VALUES (1,'admin','123','1','系统管理员',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'user','123','1','系统用户',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'json','123','1','测试用户',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

#
# Source for table "s_user_role"
#

CREATE TABLE `s_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKA97DE37EA5BD9180` (`role_id`),
  KEY `FKA97DE37E4AE85560` (`user_id`),
  CONSTRAINT `FKA97DE37E4AE85560` FOREIGN KEY (`user_id`) REFERENCES `s_user` (`ID`),
  CONSTRAINT `FKA97DE37EA5BD9180` FOREIGN KEY (`role_id`) REFERENCES `s_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "s_user_role"
#

INSERT INTO `s_user_role` VALUES (1,1),(1,2),(2,2);

#
# Source for table "un_cert"
#

CREATE TABLE `un_cert` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batch` varchar(32) NOT NULL DEFAULT '' COMMENT '批次号',
  `begindate` datetime DEFAULT NULL COMMENT '启用日期',
  `ctype` varchar(255) NOT NULL DEFAULT '' COMMENT '证书类型',
  `enddate` datetime DEFAULT NULL COMMENT '有限期',
  `fname` varchar(255) DEFAULT NULL COMMENT '文件名',
  `issuredn` varchar(255) NOT NULL DEFAULT '' COMMENT '颁发者',
  `privatekey` varchar(8000) DEFAULT NULL COMMENT '私钥密文16进制字符串',
  `publickey` varchar(4000) DEFAULT '' COMMENT '公钥密文16进制字符串',
  `serialnumber` varchar(32) NOT NULL DEFAULT '' COMMENT '序列号',
  `sigalgname` varchar(255) DEFAULT NULL COMMENT '签名算法',
  `subjectdn` varchar(255) NOT NULL DEFAULT '' COMMENT '使用者',
  `userid` varchar(255) DEFAULT NULL COMMENT '创建人',
  `version` int(11) DEFAULT NULL COMMENT '版本',
  `alias` varchar(50) DEFAULT NULL COMMENT '条目别名',
  `certType` tinyint(3) DEFAULT NULL COMMENT '证书类型 1-根证书 2-子证书',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态',
  `secret` varchar(32) DEFAULT NULL COMMENT '密钥密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

#
# Data for table "un_cert"
#

INSERT INTO `un_cert` VALUES (8,'1416844461776466','2015-01-13 09:31:06','X.509','2025-01-13 09:31:06','ROOTCA20150113093106.keystore','C=CN,CN=127.0.0.1,L=广州,ST=GD,OU=广州华工信息软件有限公司,O=研发中心','308204bf020100300d06092a864886f70d0101010500048204a9308204a50201000282010100aaf719b44104228c3078513dc9c0af99237a0ec551c95d0e50a745ba05c1dcaa14d8d415f7b250a208be36c75a321fe5d13659e1f65a1bf09bdb101edc05f65f98fc07a1621b8983ee41d39e82c549b566eb357e156e9d6f80575284d12b9a3fef0383eebf98f92d3dedb28f867f5382c663866ad0579f8e2b9741dd335d49c6cdf97af91e5209269be70b5912352e42c6fe238ac2db28146c930d59bff6e7b34dcf2c314258110988065f6c153f7919e712ea173a7c34645d7bc913b84b61d1bcdb72e0db0b05fd4d4b0d7a9cbd59d8c33e0605ea4192f96441ebf86e923391083b3506fb393fe0bd5afdaa031b8cbe7ec0e4be7f612aed0ef0d1784d5b1cb9020301000102820101009913d6f8069ee53010d62a27e0d59e64c7d72a1293407109b83d8db91a1e92add72cadf95d6753bb4188cf13803711487d22e527e5a4990fbefd4e59d337a379c54924abe36434a4e4581c5af94c1ccb47f3a259da66c8a06591ca5911d0b0b43e162fb33dea827177d9b4dfbdd593b8ac59c0a93312c1d2524584bbc299eccb92f7aa799f440d06082628846ea5d0aa04854a8cc96511261130eec8c21a19350a47fa43fa3ae2328862cdf8224a28fc19a0257a4a1d6895ff006dcadfc95690be6fafa90f2a6f69016378d68821aecc6e8773cae96bd2bdadb3a15e23caeb73e02c113dffc9257529091a7a47cf101d41d2cb15b74773433829e5f7d5c103e102818100ef0d629cb94e22beb20d29218ba19bdfbc19f794421620a9cdda2aaf8ce2a5bcefc2296dde7bfd7e17e9be4492fa526c570dadeb588939513a781f25579cf94104a07e5f2dc661c88fb1526be0bdcf643f074a7a848cb4792219a34d6e393b7257e259fad145ef51b0ae394938032e856c3f7c4febcc4c927f35258923392d4302818100b715fcda6a42b411c13e26db2e4d36ce95d5f8944f0ff35e9a92c600eccf85d9507cbe9582a5c03644911a12f371cf6ec395f95c2716f3bb0c4cef709abd0a0b3c5d3c811f9fbd5ad07666a866627d83d987ba61e976c306f9979674769689c485fa3292133bc811579490b5a9b94c31f1a9cd54373ed37ce3a5e6c52099d05302818100c5b22526859d57e0ab31751d97f36c0c68ec74b2b8c0c1fc8c106c12c889bc157796f45a379005011dd7a211e6177cc8ba69c6b6867eddd70e6abf1f4bc8b6de0d6c89763f4dfc1a38b3a5fd2f5cea41f65a1a3f1e959abf3ef342f94b39abaf117d517abb4d81f0e613ffae4cd35ccadccdd809e4710db66ddb4f128dfdc02f0281803a049dbc19304a1fd63cfbd33938fec5387635efc99e2461365a8ca47e66c4e3b8bdcf2751df74a9bdd0bd9283d2ed090cb1d9f379efac0cfa38e727a7e17fed4f93bc38aeeccff4975aeded2f84c681a1cd8122c5f8fc949943904a17ca5c9e0a56deefab68a65dd8831bd8759062b4f0fffe3151d076f47caf7611c1e2fc4102818100d042eaa35dde05a7046d27dd629e70d6b36f0f5080d8a834fb4c1532d4e2283329421c972249975d853cb4e6d97ad5908d4a875554c72d707f3143a8dc96f4aeb699f78103b647916eddccdb1b1341949d7914defb90bccbb48bd1674d5c1dd50291a475dd0f99305bb9a05e145dadc87d6deae2cf5fb553bd0bc645cc34d281','30820122300d06092a864886f70d01010105000382010f003082010a0282010100aaf719b44104228c3078513dc9c0af99237a0ec551c95d0e50a745ba05c1dcaa14d8d415f7b250a208be36c75a321fe5d13659e1f65a1bf09bdb101edc05f65f98fc07a1621b8983ee41d39e82c549b566eb357e156e9d6f80575284d12b9a3fef0383eebf98f92d3dedb28f867f5382c663866ad0579f8e2b9741dd335d49c6cdf97af91e5209269be70b5912352e42c6fe238ac2db28146c930d59bff6e7b34dcf2c314258110988065f6c153f7919e712ea173a7c34645d7bc913b84b61d1bcdb72e0db0b05fd4d4b0d7a9cbd59d8c33e0605ea4192f96441ebf86e923391083b3506fb393fe0bd5afdaa031b8cbe7ec0e4be7f612aed0ef0d1784d5b1cb90203010001','1b f6 c2 1c 1d da 83 55 ','SHA1WithRSAEncryption','C=CN,CN=127.0.0.1,L=广州,ST=GD,OU=广州华工信息软件有限公司,O=研发中心','admin',1,'1',1,1,'123456'),(9,'1417326143583723','2015-01-13 09:37:30','X.509','2016-01-13 09:37:30','CN20150113093730.pfx','C=CN,CN=127.0.0.1,L=广州,ST=GD,OU=广州华工信息软件有限公司,O=研发中心','30820276020100300d06092a864886f70d0101010500048202603082025c02010002818100d1f38cf25eeb8c1d52bbe68fea2e7b4540523b7cbaa07c12ceda1bb47847cd59e55f5074eb41b18517fa4a9d7f81a0ba69acc4ba85c0ac22c33b11b968bc729a9967c250eb085b9f4aefb580766fdb93a22b33d0e1ee9f596303681cb8db081d9ece08232bc31b7e29c884925020a298edfac636bbfe84841cde185e69f3366d02030100010281803be9b2991fb93c1cca1550f054b5bafbc22535509cb95b6eca94b73f42ebca9012c2a6651715822126fc87fa75fe79ed786693255f31e26d71251423c2ddb41e2195807eae82dcd02b47922ca230543306a0e308c06abf2ef9f9c1db69de97cb13f23e61375690b8ea9e8bb1d7983d6adb10e702e7b8044f0e133d9605aae241024100f43e46783021ba535d8c6345185f33abd68e0df289018d729b83ad541ef42c53dd61e3b5af037a6fa1cc9dd47148f254c4867a75ec8e01abdd3d02d951c5d735024100dc0eb54ed1d24c631d37fdc59672afcc7d4a487a4af884d5370593ac6f2aa5e346357e6c2652b93cce9190d40028aeb7d97675264230b20e10e4a08c941971590240312fb16556dc13f7e1e8f9bcda71a1a843670c2c01a74c62fe2a8067666c69d8e1c66ae315c1af9077970b59d30e8baec27d0a80610920110a56d5120ac3bad9024100b11a0541120cb5fd6e9e895663e6992572103e946d91e515d3da6126fdb0cd214477f8ef0148c25bf5c3a1537d6d4070264bf7ac7ac8c6041c046451bf3f5ac102401fd4176a5499ca6d221e485b7ffd6026cb249939d7a6d89a7d8ac07d536dec394f8ba3a7779353ffc2cbaf6c4a1f298dc2811b338c6fd83fa93b48591acd2830','30819f300d06092a864886f70d010101050003818d0030818902818100d1f38cf25eeb8c1d52bbe68fea2e7b4540523b7cbaa07c12ceda1bb47847cd59e55f5074eb41b18517fa4a9d7f81a0ba69acc4ba85c0ac22c33b11b968bc729a9967c250eb085b9f4aefb580766fdb93a22b33d0e1ee9f596303681cb8db081d9ece08232bc31b7e29c884925020a298edfac636bbfe84841cde185e69f3366d0203010001','1b f6 c2 1c 21 93 66 4d ','SHA1WithRSAEncryption','C=CN,CN=10.173.235.180,L=深圳,ST=GD,OU=上海琮谷信息科技有限公司,O=互联网金融','admin',3,'CN20150113093730',2,1,'654321');

#
# Source for table "un_cert_app"
#

CREATE TABLE `un_cert_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c` varchar(50) NOT NULL,
  `ou` varchar(50) DEFAULT NULL,
  `o` varchar(50) DEFAULT NULL,
  `st` varchar(50) DEFAULT NULL,
  `l` varchar(50) DEFAULT NULL,
  `cn` varchar(50) DEFAULT NULL,
  `net` varchar(20) DEFAULT NULL COMMENT '网站',
  `email` varchar(20) DEFAULT NULL COMMENT '邮件',
  `batch` varchar(32) NOT NULL DEFAULT '' COMMENT '批处理号',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态 0-未审核 1-审核通过 2-审核失败',
  `checkOperator` varchar(30) DEFAULT NULL COMMENT '审核人',
  `checkdate` datetime DEFAULT NULL COMMENT '审核时间',
  `checkResult` varchar(255) DEFAULT NULL COMMENT '审核结果描述',
  `certType` tinyint(3) DEFAULT NULL COMMENT '证书类型',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `isSign` int(11) DEFAULT NULL COMMENT '是否已签发',
  `signOperator` varchar(30) DEFAULT NULL COMMENT '签发经办人',
  `signdate` datetime DEFAULT NULL COMMENT '签发时间',
  `userid` varchar(30) DEFAULT NULL COMMENT '申请人名称',
  `dueTime` tinyint(3) DEFAULT NULL COMMENT '证书有效期',
  `effect` varchar(30) DEFAULT NULL COMMENT '用途',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `newKeyPair` tinyint(3) DEFAULT NULL COMMENT '是否生成新密钥对',
  `privatekeyHex` varchar(2000) DEFAULT NULL COMMENT '私钥',
  `importName` varchar(255) DEFAULT NULL COMMENT '导入文件名',
  `secret` varchar(32) DEFAULT NULL COMMENT '密钥密码',
  `signAlgorithm` varchar(20) DEFAULT NULL COMMENT '签名算法',
  `keySize` int(11) DEFAULT NULL COMMENT '密钥长度',
  `publickeyHex` varchar(2000) DEFAULT NULL COMMENT '公钥16进制字符串',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

#
# Data for table "un_cert_app"
#

INSERT INTO `un_cert_app` VALUES (14,'CN','广州华工信息软件有限公司','研发中心','GD','广州','127.0.0.1','www.hgsoft.com','','1416844461776466',1,'1','2015-01-13 09:30:46','审核通过',1,'2015-01-13 09:28:33',1,'admin','2015-01-13 09:31:06','admin',10,'测试根证书','',1,NULL,NULL,'123456','SHA1WithRSA',2048,NULL),(15,'CN','上海琮谷信息科技有限公司','互联网金融','GD','深圳','10.173.235.180','www.congoal.com','','1417326143583723',1,'1','2015-01-13 09:36:46','审核通过',2,'2015-01-13 09:36:35',1,'admin','2015-01-13 09:37:30','admin',1,'服务器证书','',1,NULL,NULL,'654321','SHA1WithRSA',1024,NULL);

#
# Source for table "un_cert_award"
#

CREATE TABLE `un_cert_award` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `awardDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '签发日期',
  `batch` varchar(32) NOT NULL DEFAULT '' COMMENT '批次号',
  `ctype` int(11) DEFAULT NULL,
  `disableddate` datetime DEFAULT NULL COMMENT '吊销时间',
  `disabledoperator` varchar(30) DEFAULT NULL COMMENT '吊销经办人',
  `fname` varchar(100) DEFAULT NULL COMMENT '文件名',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '文件完整路径',
  `signOperator` varchar(255) DEFAULT NULL COMMENT '签发经办人',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `userid` varchar(255) DEFAULT NULL,
  `serialnumber` varchar(32) DEFAULT NULL COMMENT '序列号',
  `effect` varchar(30) DEFAULT NULL COMMENT '用途',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `alias` varchar(50) DEFAULT NULL COMMENT '条目别名',
  `certType` tinyint(3) DEFAULT NULL COMMENT '证书类型',
  `isExportCert` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否已导出证书(针对密钥库有效)',
  `fileType` varchar(10) DEFAULT NULL COMMENT '文件类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

#
# Data for table "un_cert_award"
#

INSERT INTO `un_cert_award` VALUES (8,'2015-01-13 09:31:06','1416844461776466',2,NULL,NULL,'ROOTCA20150113093106.keystore','F:/tempWorkSpaces/CCA/WebRoot/certs/new/root/ROOTCA20150113093106.keystore','admin',1,'admin','1b f6 c2 1c 1d da 83 55 ','测试根证书',NULL,'1',1,1,'JKS'),(9,'2015-01-13 09:37:30','1417326143583723',2,NULL,NULL,'CN20150113093730.pfx','F:/tempWorkSpaces/CCA/WebRoot/certs/new/sub/CN20150113093730.pfx','admin',1,'admin','1b f6 c2 1c 21 93 66 4d ','服务器证书',NULL,'CN20150113093730',2,1,'PFX');

#
# Source for table "un_cert_export"
#

CREATE TABLE `un_cert_export` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exportFlag` tinyint(3) NOT NULL DEFAULT '0' COMMENT '导出文件标识 1-公钥 2-私钥',
  `exportDate` datetime DEFAULT NULL COMMENT '导出时间',
  `batch` varchar(32) NOT NULL DEFAULT '' COMMENT '批次号',
  `fileType` varchar(10) DEFAULT NULL COMMENT '文件类型',
  `fname` varchar(100) DEFAULT '文件名',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '文件路径',
  `downLoadCount` int(11) DEFAULT NULL COMMENT '下载次数',
  `lastDownLoadTime` datetime DEFAULT NULL COMMENT '最近一次下载时间',
  `lastDownLoadOperator` int(11) DEFAULT NULL COMMENT '最近下载人',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `createOperator` int(11) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

#
# Data for table "un_cert_export"
#

INSERT INTO `un_cert_export` VALUES (9,1,NULL,'1416844461776466','cer','ROOTCA20150113093106.cer','F:/tempWorkSpaces/CCA/WebRoot/certs/new/root/ROOTCA20150113093106.cer',0,NULL,NULL,0,1,'2015-01-13 09:34:18'),(10,1,NULL,'1417326143583723','cer','CN20150113093730.cer','F:/tempWorkSpaces/CCA/WebRoot/certs/new/sub/CN20150113093730.cer',0,NULL,NULL,0,1,'2015-01-13 09:40:13');

#
# Source for table "un_config"
#

CREATE TABLE `un_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) DEFAULT NULL,
  `class` varchar(5) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `value` varchar(1) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdate` date DEFAULT NULL,
  `operator` varchar(20) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "un_config"
#

