CREATE TABLE public.transaction
(
    id         INTEGER PRIMARY KEY,
    account_id INTEGER     NOT NULL REFERENCES public.account (id),
    type       VARCHAR(50) NOT NULL,
    amount     NUMERIC(4, 2)
);