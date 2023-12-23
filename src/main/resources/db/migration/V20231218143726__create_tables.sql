CREATE TABLE IF NOT EXISTS public.user_entity (
	id bigserial NOT NULL,
	login varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
	CONSTRAINT uk_1dnnlrof07tq8gn7s3om9bhao UNIQUE (login),
	CONSTRAINT user_entity_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.role_entity (
	id bigserial NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT role_entity_pkey PRIMARY KEY (id),
	CONSTRAINT uk_2uqxlfg1dlwv0mtewrokr23ou UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS public.user_role (
	user_id int8 NOT NULL,
	role_id int8 NOT NULL,
	CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id),
	CONSTRAINT fk66rggwh2ht8u9ig7hvam1jvai FOREIGN KEY (role_id) REFERENCES public.role_entity(id),
	CONSTRAINT fk79ltvrbu1ni2ad7w7i9vers1k FOREIGN KEY (user_id) REFERENCES public.user_entity(id)
);

CREATE TABLE IF NOT EXISTS public.post_entity (
	id bigserial NOT NULL,
	creation_date date NOT NULL,
	post_content varchar(255) NOT NULL,
	post_name varchar(255) NOT NULL,
	user_id int8 NULL,
	CONSTRAINT post_entity_pkey PRIMARY KEY (id),
	CONSTRAINT uk_ipl8xecqe548ewt8b3uee78br UNIQUE (post_name),
	CONSTRAINT fk2jmp42lmrw2f3ljd16f1re3c8 FOREIGN KEY (user_id) REFERENCES public.user_entity(id)
);

CREATE TABLE IF NOT EXISTS public.comment_entity (
	id bigserial NOT NULL,
	comment_date date NOT NULL,
	comment_text varchar(255) NOT NULL,
	count_likes int4 NOT NULL,
	post_id int8 NULL,
	user_id int8 NULL,
	CONSTRAINT comment_entity_pkey PRIMARY KEY (id),
	CONSTRAINT fk5q5av5arkm3of9b5n493p992p FOREIGN KEY (post_id) REFERENCES public.post_entity(id),
	CONSTRAINT fk7u6osru73338guaca8ukops8l FOREIGN KEY (user_id) REFERENCES public.user_entity(id)
);