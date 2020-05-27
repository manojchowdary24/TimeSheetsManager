INSERT INTO users ( user_name,first_name,last_name,email, password,permissions_set,active,change_password_required)
VALUES ( 'admin','admin','admin','admin', '$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu','ROLE_ADMIN',true,false);
INSERT INTO users ( user_name,first_name,last_name,email, password,permissions_set,active,change_password_required)
VALUES ('user', 'user','user','user','$2a$04$PCIX2hYrve38M7eOcqAbCO9UqjYg7gfFNpKsinAxh99nms9e.8HwK','ROLE_USER',true,false);