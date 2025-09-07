CREATE TABLE IF NOT EXISTS healing_activities (
  activity_id INTEGER PRIMARY KEY AUTOINCREMENT,
  title TEXT NOT NULL,
  type TEXT NOT NULL,
  mood_tag TEXT NOT NULL,
  difficulty INTEGER DEFAULT 1,
  duration_sec INTEGER DEFAULT 60,
  content TEXT,
  active INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS quotes (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  mood_tag TEXT NOT NULL,
  text TEXT NOT NULL
);

-- optional table to track completed activities
CREATE TABLE IF NOT EXISTS activity_runs (
  run_id INTEGER PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER,
  activity_id INTEGER,
  started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  completed_at TIMESTAMP,
  FOREIGN  KEY(activity_id) REFERENCES healing_activities(activity_id)
);

CREATE TABLE IF NOT EXISTS journal (
    page INTEGER PRIMARY KEY,
    date TEXT,
    content TEXT
);
