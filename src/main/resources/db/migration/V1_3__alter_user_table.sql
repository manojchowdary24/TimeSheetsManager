alter table users
    add column reset_password_token VARCHAR(50);

alter table users
    rename password_expiration_date to reset_pw_token_exp_date;