CREATE TABLE public.account
(
    id        SERIAL PRIMARY KEY,
    player_id INTEGER NOT NULL,
    amount    NUMERIC(4, 2)
);

CREATE INDEX account_player_id_idx ON public.account (player_id);