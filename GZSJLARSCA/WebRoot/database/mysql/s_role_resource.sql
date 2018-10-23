CREATE TABLE `s_role_resource` (
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY  (`role_id`,`resource_id`),
  KEY `FKE4A2D6CBA5BD9180` (`role_id`),
  KEY `FKE4A2D6CBAA629780` (`resource_id`),
  CONSTRAINT `FKE4A2D6CBAA629780` FOREIGN KEY (`resource_id`) REFERENCES `s_resource` (`id`),
  CONSTRAINT `FKE4A2D6CBA5BD9180` FOREIGN KEY (`role_id`) REFERENCES `s_role` (`id`)
);

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