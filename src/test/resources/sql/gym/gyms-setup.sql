DELETE from gym_members;
DELETE from gym_classes;
DELETE FROM gyms;

INSERT INTO gyms (id, name, phone, street, number, neighborhood, city, state, created_at, updated_at
) VALUES (
             '11111111-1111-1111-1111-111111111111',
          'First-gym-name',
          'first-gym-phone-num',
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
          'Second-gym-street-name',
          'another-number',
          'another-neighborhood',
          'another-city',
          'another-state',
          NOW(),
          NULL
      );