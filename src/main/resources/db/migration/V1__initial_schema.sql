CREATE TABLE users (
    id UUID PRIMARY KEY,
    user_type VARCHAR(25) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
--     Address
    street VARCHAR(255) NOT NULL,
    number VARCHAR(25) NOT NULL,
    neighborhood VARCHAR(50) NOT NULL,
    zip_code VARCHAR (15) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
--     manager
    gym_name VARCHAR(255),
--     instructor
    cref VARCHAR(15),
    specialty VARCHAR(255),
--     student
    birthday DATE,
    status BOOLEAN,

    CONSTRAINT check_user_type
    CHECK ( user_type IN ('MANAGER', 'INSTRUCTOR', 'STUDENT') )
);
