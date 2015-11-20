CREATE TABLESPACE tbs_perm
  DATAFILE 'devbiblio_perm.dat'
    SIZE 20M
  ONLINE;

CREATE TEMPORARY TABLESPACE tbs_temp
TEMPFILE 'devbiblio_temp.dbf'
SIZE 5M
AUTOEXTEND ON;

CREATE USER devbiblio
IDENTIFIED BY devbiblio
DEFAULT TABLESPACE tbs_perm
TEMPORARY TABLESPACE tbs_temp
QUOTA 20M on tbs_perm;

GRANT create session TO devbiblio;
GRANT create table TO devbiblio;
GRANT create view TO devbiblio;
GRANT create any trigger TO devbiblio;
GRANT create any procedure TO devbiblio;
GRANT create sequence TO devbiblio;
GRANT create synonym TO devbiblio;