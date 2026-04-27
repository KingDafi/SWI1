INSERT INTO app_user (user_id, username, password, role)
VALUES (gen_random_uuid(), 'student@osu.cz', 'heslo', 'MEMBER');

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price)
VALUES (gen_random_uuid(), 'English Vocabulary in Use: Pre-intermediate', 'Stuart Redman', 'Učebnice', 5, 5, 550, 650);

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price)
VALUES (gen_random_uuid(), 'Duna', 'Frank Herbert', 'Sci-Fi', 2, 2, 350, null);

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price)
VALUES (gen_random_uuid(), 'Clean Code: A Handbook of Agile Software Craftsmanship', 'Robert C. Martin', 'Programování', 4, 4, 890, 1100);

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price)
VALUES (gen_random_uuid(), '1984', 'George Orwell', 'Klasická beletrie', 3, 3, 250, 320);

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price)
VALUES (gen_random_uuid(), 'Sapiens: Úchvatný příběh lidstva', 'Yuval Noah Harari', 'Historie / Populárně naučná', 4, 4, 450, 550);

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price)
VALUES (gen_random_uuid(), 'Pán Prstenů: Společenstvo Prstenu', 'J.R.R. Tolkien', 'Fantasy', 3, 3, 390, null);

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price)
VALUES (gen_random_uuid(), 'Atomic Habits', 'James Clear', 'Productivity', 5, 5, 430, 550);