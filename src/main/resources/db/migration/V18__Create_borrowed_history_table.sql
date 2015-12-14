CREATE TABLE BORROWED_HISTORY (
  HISTORY_ID NUMBER(5) PRIMARY KEY,
  BOOK_ID NUMBER(5) REFERENCES BOOK(BOOK_ID),
  MEMBER_ID VARCHAR2(36) REFERENCES LIBRARY_MEMBER(U_ID),
  START_DATE DATE NOT NULL,
  END_DATE DATE
);

CREATE SEQUENCE BORROWED_HISTORY_SEQUENCE
START WITH     1
INCREMENT BY   1
NOCACHE
NOCYCLE;


