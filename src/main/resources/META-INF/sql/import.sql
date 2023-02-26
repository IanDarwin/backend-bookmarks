drop sequence hibernate_sequence if exists;
create sequence hibernate_sequence start with 10 increment by 1;

insert into topic(id,description) values('econ', 'The economy');
insert into topic(id,description) values('pol', 'Politics');

insert into bookmark(id, topic_id, url, text) values(1, 'econ', 'http://huff.puff.com', 'The economy is booming!');
insert into bookmark(id, topic_id, url, text) values(2, 'pol', 'http://huff.puff.com', 'Boring: A politician told a lie!');
insert into bookmark(id, topic_id, url, text) values(3, 'econ', 'http://huff.puff.com', 'Some other country''s economy is not booming so much.');
insert into bookmark(id, topic_id, url, text) values(4, 'pol', 'http://huff.puff.com', 'Breaking: Another politician told the truth!');
