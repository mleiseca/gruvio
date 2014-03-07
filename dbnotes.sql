create database gruvio;
create user 'gruvio'@'localhost' identified by 'gruvio';
grant all on gruvio.* to 'gruvio'@'localhost';
flush privileges;


 createdb gruvio