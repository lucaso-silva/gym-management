CREATE TABLE gyms (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,

    street VARCHAR(255) NOT NULL,
    number VARCHAR(25) NOT NULL,
    neighborhood VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE gym_members (
    gym_id UUID NOT NULL,
    member_id UUID NOT NULL,

    PRIMARY KEY (gym_id, member_id),

    CONSTRAINT fk_gym_members_gym
    FOREIGN KEY (gym_id) REFERENCES gyms(id) ON DELETE CASCADE,
    CONSTRAINT fk_gym_members_user
    FOREIGN KEY (member_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE gym_classes (
    gym_id UUID NOT NULL,
    class_id UUID NOT NULL,

    PRIMARY KEY (gym_id, class_id),

    CONSTRAINT fk_gym_classes_gym
    FOREIGN KEY (gym_id) REFERENCES gyms(id) ON DELETE CASCADE
);