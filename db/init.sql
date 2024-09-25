CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS bets;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS events_bets;

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    balance INT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE events (
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    event_date timestamp with time zone NOT NULL,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    odd_out double precision NOT NULL,
    odd_in double precision NOT NULL,
    odd_draw double precision NOT NULL,
    is_active boolean NOT NULL DEFAULT true,
    created_at timestamp with time zone DEFAULT now(),
    updated_at timestamp with time zone DEFAULT now(),
    deleted_at timestamp with time zone,
    home_team character varying(50) COLLATE pg_catalog."default" NOT NULL,
    away_team character varying(50) COLLATE pg_catalog."default" NOT NULL,
    result character varying COLLATE pg_catalog."default",
    CONSTRAINT events_pkey PRIMARY KEY (id)
);

CREATE TABLE bets
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    user_id uuid NOT NULL,
    bet_date timestamp with time zone NOT NULL,
    result boolean,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp with time zone DEFAULT now(),
    updated_at timestamp with time zone DEFAULT now(),
    deleted_at timestamp with time zone,
    stake double precision NOT NULL,
    stake_return double precision NOT NULL,
    CONSTRAINT bets_pkey PRIMARY KEY (id),
    CONSTRAINT bets_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

CREATE TABLE events_bets
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    events_id uuid NOT NULL,
    bets_id uuid NOT NULL,
    option character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT events_bets_pkey PRIMARY KEY (id),
    CONSTRAINT events_bets_bets_id_fkey FOREIGN KEY (bets_id)
        REFERENCES public.bets (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT events_bets_events_id_fkey FOREIGN KEY (events_id)
        REFERENCES public.events (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)
