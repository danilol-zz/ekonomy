CREATE TABLE categories (
 id serial PRIMARY KEY,
 name VARCHAR(20) UNIQUE NOT NULL,

 created_at timestamp not null,
 updated_at timestamp not null
);

CREATE TABLE accounts(
 id serial PRIMARY KEY,
 name VARCHAR(20) UNIQUE NOT NULL,

 created_at timestamp not null,
 updated_at timestamp not null
);

