CREATE TABLESPACE tbs_perm
  DATAFILE 'biblio_perm.dat'
    SIZE 20M
  ONLINE;

CREATE TEMPORARY TABLESPACE tbs_temp
TEMPFILE 'biblio_temp.dbf'
SIZE 5M
AUTOEXTEND ON;

CREATE USER biblio
IDENTIFIED BY biblio
DEFAULT TABLESPACE tbs_perm
TEMPORARY TABLESPACE tbs_temp
QUOTA 20M on tbs_perm;

GRANT create session TO biblio;
GRANT create table TO biblio;
GRANT create view TO biblio;
GRANT create any trigger TO biblio;
GRANT create any procedure TO biblio;
GRANT create sequence TO biblio;
GRANT create synonym TO biblio;