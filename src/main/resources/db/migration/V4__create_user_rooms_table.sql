CREATE TABLE IF NOT EXISTS room_users (
  id        SERIAL  NOT NULL,
  user_id   INT     NOT NULL,
  room_id   INT     NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);
-- user_idとroom_idは外部キー
-- ON DELETE CASCADEは、親であるroom_usersが消えたら同時に消える