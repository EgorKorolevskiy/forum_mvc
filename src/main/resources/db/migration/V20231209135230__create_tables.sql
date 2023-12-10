CREATE TABLE IF NOT EXISTS public.user_entity (
	id bigserial NOT NULL,
	login varchar(255) NULL,
	"password" varchar(255) NULL,
	CONSTRAINT user_entity_pkey PRIMARY KEY (id)
);

insert into user_entity (login, password) values ('user1', '123');