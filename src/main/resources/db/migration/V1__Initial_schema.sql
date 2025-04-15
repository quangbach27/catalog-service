CREATE TABLE book
(
    id                 UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    isbn               varchar(255) UNIQUE NOT NULL,
    author             varchar(255)        NOT NULL,
    title              varchar(255)        NOT NULL,
    price              float8              NOT NULL,
    created_date       timestamp           NOT NULL,
    last_modified_date timestamp           NOT NULL,
    version            integer             not null
);

CREATE UNIQUE INDEX idx_book_isbn ON book (isbn);