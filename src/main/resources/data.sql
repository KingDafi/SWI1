INSERT INTO app_user (user_id, name, email, password, role)
VALUES (gen_random_uuid(), 'Student', 'student@osu.cz', 'heslo', 'MEMBER');

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price, cover_url)
VALUES (gen_random_uuid(), 'English Vocabulary in Use: Pre-intermediate', 'Stuart Redman', 'Učebnice', 5, 5, 550, 650, 'http://localhost:8080/covers/vocabulary.jpg');

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price, cover_url)
VALUES (gen_random_uuid(), 'Duna', 'Frank Herbert', 'Sci-Fi', 2, 2, 350, null, 'http://localhost:8080/covers/duna.jpg');

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price, cover_url)
VALUES (gen_random_uuid(), 'Clean Code: A Handbook of Agile Software Craftsmanship', 'Robert C. Martin', 'Programování', 4, 4, 890, 1100, 'http://localhost:8080/covers/cleancode.jpg');

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price, cover_url)
VALUES (gen_random_uuid(), '1984', 'George Orwell', 'Klasická beletrie', 3, 3, 250, 320, 'http://localhost:8080/covers/1984.jpg');

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price, cover_url)
VALUES (gen_random_uuid(), 'Sapiens: Úchvatný příběh lidstva', 'Yuval Noah Harari', 'Historie / Populárně naučná', 4, 4, 450, 550, 'http://localhost:8080/covers/sapiens.jpg');

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price, cover_url)
VALUES (gen_random_uuid(), 'Pán Prstenů: Společenstvo Prstenu', 'J.R.R. Tolkien', 'Fantasy', 3, 3, 390, null, 'http://localhost:8080/covers/lotr.jpg');

INSERT INTO book (book_id, title, author, category, quantity, available_quantity, price, original_price, cover_url)
VALUES (gen_random_uuid(), 'Atomové návyky', 'James Clear', 'Produktivita', 5, 5, 430, 550, 'http://localhost:8080/covers/atomic.jpg');