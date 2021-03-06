CREATE TABLE CATEGORY (
  CATEGORY_ID NUMBER(5) PRIMARY KEY,
  TYPE VARCHAR2(255)
);

CREATE SEQUENCE CATEGORY_SEQUENCE
START WITH     1
INCREMENT BY   1
NOCACHE
NOCYCLE;

CREATE TABLE BOOK_CATEGORY (
  BOOK_ID NUMBER(5) REFERENCES BOOK(BOOK_ID) ON DELETE CASCADE,
  CATEGORY_ID NUMBER(5) REFERENCES CATEGORY(CATEGORY_ID) ON DELETE CASCADE,
  CONSTRAINT PK_BOOK_CATEGORY PRIMARY KEY (BOOK_ID, CATEGORY_ID)
);

