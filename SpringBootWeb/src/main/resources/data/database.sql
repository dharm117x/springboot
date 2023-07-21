create table if NOT EXISTS language (
id integer auto_increment,
locale varchar2(5),
key varchar2(250),
content varchar2(250),
primary key(id)
);