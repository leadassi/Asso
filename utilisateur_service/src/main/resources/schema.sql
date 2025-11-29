CREATE TABLE utilisateurs(
    id int primary key,
    nom VARCHAR(100) not null,
    prenom varchar(100) not null,
    email varchar(100) not null,
    mot_passe varchar(100) not null,
    numerotelephone int not null,
    pays varchar(50) not null,
    ville varchar(50) not null,
    quartier varchar(50) not null,

);