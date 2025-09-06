INSERT INTO healing_activities (title,type,mood_tag,difficulty,duration_sec,content) VALUES
('Box Breathing 4-4-4-4','BREATHING','STRESSED',1,60,'{"inhale":4,"hold":4,"exhale":4,"rest":4,"cycles":5}'),
('Gratitude Prompt','JOURNAL','SAD',1,180,'Write 3 small things that were good today.'),
('Lo-fi Focus Playlist','MUSIC','TIRED',2,600,'https://open.spotify.com/playlist/...'),
('Mini Body Scan','EXERCISE','ANXIOUS',1,120,'Focus from head to toe, relaxing each region.'),
('Celebrate Small Wins','JOURNAL','HAPPY',1,120,'Write one small success you had today and how it made you feel.'),
('2-Minute Calm','BREATHING','ANXIOUS',1,120,'{"inhale":5,"hold":2,"exhale":6,"rest":0,"cycles":8}');

INSERT INTO quotes (mood_tag,text) VALUES
('SAD','This too shall pass.'),
('STRESSED','You don’t have to do it all today.'),
('ANGRY','Take a breath, then choose your response.'),
('TIRED','Allow yourself to rest; rest fuels creativity.'),
('HAPPY','Savor this moment — you earned it.'),
('ANXIOUS','You can handle one thing at a time. Breathe.');
