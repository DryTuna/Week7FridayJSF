/*
drop database Week7;
create database Week7;
*/
drop table if exists Staff;
create table Staff (
	id char(9) not null,
    lastName varchar(15),
    mi char(1),
    firstName varchar(15),
    address varchar(20),
    city varchar(20),
    state char(2),
    telephone char(10),
    email varchar(40),
    primary key (id)
);

insert into Staff values (
'111', 'Doji', '', 'Shuten', '12345 Ayakashi Dr.', 'Tokyo', 'GG',
'987-654-0123', 'shutendoji@ayakashi.gg');
insert into Staff values (
'222', 'Doji', '', 'Giri', '23456 Ayakashi Dr.', 'Tokyo', 'GG',
'654-789-0123', 'dojigiri@ayakashi.gg');
insert into Staff values (
'333', 'Takeda', '', 'Shingen', '34567 Ayakashi Dr.', 'Tokyo', 'GG',
'123-654-0987', 'shingentakeda@ayakashi.gg');
insert into Staff values (
'444', 'Hime', '', 'Kushinada', '45678 Ayakashi Dr.', 'Tokyo', 'GG',
'321-456-8790', 'kushinadahime@ayakashi.gg');