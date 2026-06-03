DELETE FROM gym_class_students;
DELETE FROM gym_classes;
DELETE FROM gym_members;
DELETE FROM gyms;
DELETE FROM users;

INSERT INTO users (
    id, user_type, name, email, login, password, phone,
    created_at, updated_at,
    street, number, neighborhood, zip_code, city, state,
    cref, specialty,
    birthday, status
) VALUES
      (
          '11111111-1111-1111-1111-111111111111',
          'MANAGER',
          'Pedro Silva',
          'pedro.manager@gym.com',
          'pedro.manager',
          'hashed_password',
          '81999990001',
          NOW(),
          NULL,
          'Rua A', '100', 'Espinheiro', '51000-000', 'Recife', 'PE',
          NULL, NULL,
          NULL, NULL
      ),
      (
          '22222222-2222-2222-2222-222222222222',
          'INSTRUCTOR',
          'Ana Souza',
          'ana.instructor@gym.com',
          'ana.instructor',
          'hashed_password',
          '81999990002',
          NOW(),
          NULL,
          'Rua B', '200', 'Gracas', '51000-001', 'Recife', 'PE',
          'CREF12345',
          'Funcional',
          NULL, NULL
      ),
      (
          '99999999-9999-9999-9999-999999999999',
          'MANAGER',
          'Maria Santos',
          'maria.manager@gym.com',
          'maria.manager',
          'hashed_password',
          '81999990099',
          NOW(),
          NULL,
          'Rua Z', '999', 'Boa Vista', '50000-000', 'Recife', 'PE',
          NULL, NULL,
          NULL, NULL
      );

INSERT INTO gyms (
    id, name, phone, manager_id,
    street, number, neighborhood, city, state,
    created_at, updated_at
) VALUES
      (
          '11111111-1111-1111-1111-111111111111',
          'First-gym-name',
          'first-gym-phone-num',
          '11111111-1111-1111-1111-111111111111',
          'First-gym-street-name',
          'any-number',
          'any-neighborhood',
          'any-city',
          'any-state',
          NOW(),
          NULL
      ),
      (
          '22222222-2222-2222-2222-222222222222',
          'Second-gym-name',
          'second-gym-phone-num',
          '99999999-9999-9999-9999-999999999999',
          'Second-gym-street-name',
          'another-number',
          'another-neighborhood',
          'another-city',
          'another-state',
          NOW(),
          NULL
      );