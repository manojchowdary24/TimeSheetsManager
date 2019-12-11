create table users
(
   id serial not null
       constraint users_pkey
       PRIMARY KEY,
   user_name VARCHAR (50) NOT NULL,
   email VARCHAR (355) ,
   first_name VARCHAR(250),
   last_name VARCHAR(250),
   active BOOLEAN,
   change_password_required BOOLEAN,
   password_expiration_date timestamp ,
   permissions_set VARCHAR(40),
   create_date timestamp with time zone default CURRENT_TIMESTAMP not null,
   created_by VARCHAR(50),
   update_date timestamp with time zone ,
   update_by VARCHAR(50),
   password VARCHAR(250) NOT NULL
);

INSERT INTO users ( id,user_name,first_name,last_name,email, password) VALUES ( 1,'Alex123','Alex123','Alex123','Alex123', '$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu');
INSERT INTO users ( id,user_name,first_name,last_name,email, password) VALUES (2,'Tom234', 'Alex123','Alex123','Alex123','$2a$04$PCIX2hYrve38M7eOcqAbCO9UqjYg7gfFNpKsinAxh99nms9e.8HwK');
INSERT INTO users ( id,user_name,first_name,last_name,email, password) VALUES (3,'Adam','Alex123','Alex123','Alex123', '$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu');