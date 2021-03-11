insert into topic(id,name,description) values(1, 'econ', 'The economy');
insert into topic(id,name,description) values(2, 'pol', 'Politics');

insert into bookmark(id,topic_id, url, description) values(1, 1, 'http://huff.puff.com', 'The economy is booming!');
insert into bookmark(id,topic_id, url, description) values(2, 2, 'http://huff.puff.com', 'Breaking: A politician told a lie!');
insert into bookmark(id,topic_id, url, description) values(3, 1, 'http://huff.puff.com', 'Some other country''s economy is not booming so much.');
insert into bookmark(id,topic_id, url, description) values(4, 2, 'http://huff.puff.com', 'Breaking: Another politician told the truth!');
