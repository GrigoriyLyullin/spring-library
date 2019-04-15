-- drop all tables to start from the scratch each time
DROP TABLE IF EXISTS AUTHOR;
DROP TABLE IF EXISTS GENRE;
DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS REVIEW;
DROP TABLE IF EXISTS MAP_BOOK_AUTHOR;
DROP TABLE IF EXISTS MAP_BOOK_GENRE;

DROP SEQUENCE IF EXISTS GENRE_SEQUENCE;
DROP SEQUENCE IF EXISTS AUTHOR_SEQUENCE;
DROP SEQUENCE IF EXISTS BOOK_SEQUENCE;
DROP SEQUENCE IF EXISTS REVIEW_SEQUENCE;

-- create required tables
CREATE TABLE AUTHOR
(
  ID         BIGINT,
  FIRST_NAME VARCHAR(255) NOT NULL,
  LAST_NAME  VARCHAR(255) NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE GENRE
(
  ID    BIGINT,
  GENRE VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (ID)
);

CREATE TABLE BOOK
(
  ID    BIGINT PRIMARY KEY,
  TITLE VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (ID)
);

CREATE TABLE REVIEW
(
  ID          BIGINT,
  REVIEW_TEXT VARCHAR(255) NOT NULL,
  BOOK_ID     BIGINT,
  PRIMARY KEY (ID),
  FOREIGN KEY (BOOK_ID) REFERENCES BOOK (ID)
);

CREATE TABLE MAP_BOOK_AUTHOR
(
  BOOK_ID   BIGINT,
  AUTHOR_ID BIGINT,
  FOREIGN KEY (BOOK_ID) REFERENCES BOOK (ID),
  FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHOR (ID)
);

CREATE TABLE MAP_BOOK_GENRE
(
  BOOK_ID  BIGINT,
  GENRE_ID BIGINT,
  FOREIGN KEY (BOOK_ID) REFERENCES BOOK (ID),
  FOREIGN KEY (GENRE_ID) REFERENCES GENRE (ID)
);

CREATE UNIQUE INDEX UNIQUE_AUTHOR ON AUTHOR (FIRST_NAME, LAST_NAME);

CREATE SEQUENCE GENRE_SEQUENCE MINVALUE 1 INCREMENT BY 1 CACHE 10;
CREATE SEQUENCE AUTHOR_SEQUENCE MINVALUE 1 INCREMENT BY 1 CACHE 10;
CREATE SEQUENCE BOOK_SEQUENCE MINVALUE 1 INCREMENT BY 1 CACHE 10;
CREATE SEQUENCE REVIEW_SEQUENCE MINVALUE 1 INCREMENT BY 1 CACHE 10;