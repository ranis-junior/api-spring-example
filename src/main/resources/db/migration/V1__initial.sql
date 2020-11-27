CREATE TABLE public.stores
(
    id serial NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL UNIQUE,
    telephone character varying(15) COLLATE pg_catalog."default",
    address text COLLATE pg_catalog."default",
    CONSTRAINT store_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.stores
    OWNER to postgres;

CREATE TABLE public.products
(
    id serial NOT NULL,
    name character varying(45) COLLATE pg_catalog."default" NOT NULL,
    bar_code character varying(43) COLLATE pg_catalog."default" NOT NULL,
    category character varying(75) COLLATE pg_catalog."default",
    price numeric(8,2) NOT NULL,
    id_store integer NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id),
    CONSTRAINT id_store_fk FOREIGN KEY (id_store)
        REFERENCES public.stores (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.products
    OWNER to postgres;