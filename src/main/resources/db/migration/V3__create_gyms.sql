CREATE TABLE gyms (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    manager_id UUID,

    street VARCHAR(255) NOT NULL,
    number VARCHAR(25) NOT NULL,
    neighborhood VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_gyms_manager
                  FOREIGN KEY (manager_id)
                  REFERENCES users(id)
);

CREATE TABLE gym_members (
    gym_id UUID NOT NULL,
    member_id UUID NOT NULL,

    PRIMARY KEY (gym_id, member_id),

    CONSTRAINT fk_gym_members_gym
        FOREIGN KEY (gym_id)
            REFERENCES gyms(id) ON DELETE CASCADE,

    CONSTRAINT fk_gym_members_user
        FOREIGN KEY (member_id)
            REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE gym_classes (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gym_id UUID NOT NULL,
    instructor_id UUID NOT NULL,
    capacity INT NOT NULL,

    day_of_week VARCHAR(20) NOT NULL,
    room VARCHAR(100) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,

    CONSTRAINT fk_gym_class_gym
        FOREIGN KEY (gym_id)
            REFERENCES gyms(id) ON DELETE CASCADE,

    CONSTRAINT fk_gym_class_instructor
        FOREIGN KEY (instructor_id)
            REFERENCES users(id)
);

CREATE TABLE gym_class_students (
    gym_class_id UUID NOT NULL,
    student_id UUID NOT NULL,

    PRIMARY KEY (gym_class_id, student_id),

    CONSTRAINT fk_gym_class_students_class
        FOREIGN KEY (gym_class_id)
            REFERENCES gym_classes(id) ON DELETE CASCADE,

    CONSTRAINT fk_gym_class_students_student
        FOREIGN KEY (student_id)
            REFERENCES users(id) ON DELETE CASCADE
);