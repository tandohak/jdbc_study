show databases;

create table student(
	id char(7),
	name varchar(10),
	dept varchar(20),
	primary key(id)
);

desc student;

insert into student values('1091011', '김철수', '컴퓨터시스템');
insert into student values('0792012','최고봉','멀티미디어');
insert into student values('0494013','이기자','컴퓨터 공학');

select * from student;

update student set dept = "컴퓨터공학" where name = "최고봉";

delete from student where id = "0792012";

insert into student values('1234567','배수지','성악과');
delete from student where id ="1234567";