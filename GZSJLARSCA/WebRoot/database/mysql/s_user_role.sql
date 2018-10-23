CREATE TABLE `s_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY  (`user_id`,`role_id`),
  KEY `FKA97DE37EA5BD9180` (`role_id`),
  KEY `FKA97DE37E4AE85560` (`user_id`),
  CONSTRAINT `FKA97DE37E4AE85560` FOREIGN KEY (`user_id`) REFERENCES `s_user` (`id`),
  CONSTRAINT `FKA97DE37EA5BD9180` FOREIGN KEY (`role_id`) REFERENCES `s_role` (`id`)
);

insert into S_USER_ROLE (ROLE_ID, USER_ID)
values (1, 1);
insert into S_USER_ROLE (ROLE_ID, USER_ID)
values (2, 1);
insert into S_USER_ROLE (ROLE_ID, USER_ID)
values (2, 2);
commit;

