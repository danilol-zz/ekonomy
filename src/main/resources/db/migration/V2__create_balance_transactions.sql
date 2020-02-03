CREATE TABLE balance_transactions (
 id serial PRIMARY KEY,
 description varchar(100) not null,
 category_id  bigint NOT NULL REFERENCES categories,
 acount_id  bigint NOT NULL REFERENCES accounts,
 amount bigint not null,
 transaction_date date not null,
 created_at timestamp DEFAULT now() not null,
 updated_at timestamp DEFAULT now() not null
);
