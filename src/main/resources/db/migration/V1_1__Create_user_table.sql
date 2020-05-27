DROP TABLE IF EXISTS users;

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
   reset_pw_token_exp_date timestamp with time zone ,
   permissions_set VARCHAR(40),
   reset_password_token VARCHAR(50),
   create_date timestamp with time zone default CURRENT_TIMESTAMP not null,
   created_by VARCHAR(50),
   update_date timestamp with time zone ,
   update_by VARCHAR(50),
   password VARCHAR(250) NOT NULL
);