create table un_config(
       id int primary key not null auto_increment,
       type varchar(20),
       class varchar(5),
       name varchar(20),
       value varchar(1),
       status int,
       createdate date,
       operator varchar(20),
       description varchar(50)
)
  