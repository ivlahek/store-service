create TABLE public.product (
    id bigint NOT NULL,
    name character varying(255) not NULL unique,
    description character varying(255) not NULL,
    price numeric(17, 2),
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL
);

alter table product add CONSTRAINT product_pkey PRIMARY KEY(id);

create TABLE public.p_order (
    id bigint NOT NULL,
    email character varying(255) not null,
    total_price numeric(17, 2),
    date_created timestamp NOT NULL
);

alter table p_order add CONSTRAINT order_pkey PRIMARY KEY(id);


create table public.order_product (
    id bigint  NOT NULL,
    product_id bigint not null,
    product_price numeric(17,2),
    order_id bigint not null,
    quantity int not null
);

alter table order_product add CONSTRAINT order_product_pkey PRIMARY KEY(id);
alter table order_product add constraint fk_placed_order_order_id foreign key (order_id) references p_order(id);
alter table order_product add constraint fk_order_product_id foreign key (product_id) references product(id);

create sequence public.product_sequence
    start with 1
    increment by 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create sequence public.order_sequence
    start with 1
    increment by 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create sequence public.order_product_sequence
    start with 1
    increment by 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;