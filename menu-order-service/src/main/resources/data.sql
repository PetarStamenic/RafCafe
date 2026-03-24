-- Sastojci
INSERT INTO ingredients (name, price, available) VALUES ('Mleko', 30.00, true);
INSERT INTO ingredients (name, price, available) VALUES ('Sojino mleko', 50.00, true);
INSERT INTO ingredients (name, price, available) VALUES ('Bademovo mleko', 60.00, true);
INSERT INTO ingredients (name, price, available) VALUES ('Slag', 40.00, true);
INSERT INTO ingredients (name, price, available) VALUES ('Vanila sirup', 35.00, true);
INSERT INTO ingredients (name, price, available) VALUES ('Karamel sirup', 35.00, true);
INSERT INTO ingredients (name, price, available) VALUES ('Cokolada', 45.00, true);
INSERT INTO ingredients (name, price, available) VALUES ('Cimet', 15.00, true);
INSERT INTO ingredients (name, price, available) VALUES ('Med', 40.00, true);
INSERT INTO ingredients (name, price, available) VALUES ('Limun', 20.00, true);
INSERT INTO ingredients (name, price, available) VALUES ('Nana', 25.00, true);
INSERT INTO ingredients (name, price, available) VALUES ('Djumbir', 30.00, true);

-- Stavke menija - Kafe
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Espreso', 'Klasican espreso od najkvalitetnijih zrna', 150.00, 'COFFEE', 'ALL', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Kapucino', 'Espreso sa penastim mlekom i kakaom', 250.00, 'COFFEE', 'ALL', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Latte Macchiato', 'Toplo mleko sa espreso shotom', 280.00, 'COFFEE', 'ALL', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Turska kafa', 'Tradicionalna domaca kafa na dzezvi', 130.00, 'COFFEE', 'ALL', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Ledena kafa', 'Hladna kafa sa ledom i mlekom', 290.00, 'COFFEE', 'SUMMER', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Mocaccino', 'Espreso sa cokoladom i mlekom', 300.00, 'COFFEE', 'WINTER', true);

-- Stavke menija - Cajevi
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Zeleni caj', 'Japanski sencha zeleni caj', 180.00, 'TEA', 'ALL', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Kamilica', 'Prirodna kamilica za opustanje', 160.00, 'TEA', 'ALL', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Planinski caj', 'Mesavina divljih srpskih trava', 200.00, 'TEA', 'WINTER', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Ledeni caj od breskve', 'Osvezavajuci ledeni caj', 220.00, 'TEA', 'SUMMER', true);

-- Stavke menija - Sokovi i Smoothie
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Sveže ceđena pomorandža', 'Prirodni sok od pomorandze', 280.00, 'JUICE', 'ALL', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Limunada', 'Domaca limunada sa nanom', 220.00, 'JUICE', 'SUMMER', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Jagoda smoothie', 'Jagoda, banana i jogurt', 350.00, 'SMOOTHIE', 'SPRING', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Mango smoothie', 'Mango, ananas i kokosovo mleko', 380.00, 'SMOOTHIE', 'SUMMER', true);

-- Stavke menija - Peciva i Sendvici
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Kroasan sa cokoladom', 'Sveze pecen kroasan punjen cokoladom', 220.00, 'PASTRY', 'ALL', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Burek sa sirom', 'Tradicionalni srpski burek', 250.00, 'PASTRY', 'ALL', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Club sendvic', 'Pilecje belo, slanina, salata, paradajz', 420.00, 'SANDWICH', 'ALL', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Caprese sendvic', 'Mocarela, paradajz, bosiljak, pesto', 380.00, 'SANDWICH', 'SPRING', true);

-- Stavke menija - Deserti
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Cheesecake', 'Njujorski cheesecake sa sumskim vocem', 350.00, 'DESSERT', 'ALL', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Tiramisu', 'Klasican italijanski tiramisu', 380.00, 'DESSERT', 'ALL', true);
INSERT INTO menu_items (name, description, price, type, season, available) VALUES
('Palacinka sa Nutellom', 'Domace palacinke sa Nutellom i bananom', 320.00, 'DESSERT', 'ALL', true);

-- Povezivanje stavki menija sa sastojcima
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (2, 1);
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (3, 1);
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (5, 1);
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (6, 7);
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (6, 1);
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (9, 9);
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (10, 10);
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (12, 11);
INSERT INTO menu_item_ingredients (menu_item_id, ingredient_id) VALUES (12, 10);

-- Narudzbine
INSERT INTO orders (user_email, status, total_price, created_at) VALUES
('ana.ivanovic@raf.rs', 'DELIVERED', 530.00, '2026-03-20 09:15:00');
INSERT INTO orders (user_email, status, total_price, created_at) VALUES
('marko.jovanovic@raf.rs', 'PREPARING', 680.00, '2026-03-23 10:30:00');
INSERT INTO orders (user_email, status, total_price, created_at) VALUES
('jelena.petrovic@raf.rs', 'PENDING', 900.00, '2026-03-23 11:00:00');
INSERT INTO orders (user_email, status, total_price, created_at) VALUES
('ana.ivanovic@raf.rs', 'CANCELLED', 250.00, '2026-03-22 14:20:00');
INSERT INTO orders (user_email, status, total_price, created_at) VALUES
('milica.nikolic@raf.rs', 'READY', 750.00, '2026-03-23 08:45:00');

-- Stavke narudzbina
INSERT INTO order_items (order_id, menu_item_id, quantity, customization_notes, item_price) VALUES
(1, 2, 1, 'Sa dodatnim mlekom', 250.00);
INSERT INTO order_items (order_id, menu_item_id, quantity, customization_notes, item_price) VALUES
(1, 15, 1, NULL, 220.00);
INSERT INTO order_items (order_id, menu_item_id, quantity, customization_notes, item_price) VALUES
(1, 1, 1, 'Dupli', 150.00);
INSERT INTO order_items (order_id, menu_item_id, quantity, customization_notes, item_price) VALUES
(2, 17, 1, NULL, 420.00);
INSERT INTO order_items (order_id, menu_item_id, quantity, customization_notes, item_price) VALUES
(2, 2, 1, 'Bez secera', 250.00);
INSERT INTO order_items (order_id, menu_item_id, quantity, customization_notes, item_price) VALUES
(3, 19, 2, NULL, 700.00);
INSERT INTO order_items (order_id, menu_item_id, quantity, customization_notes, item_price) VALUES
(3, 7, 1, NULL, 180.00);
INSERT INTO order_items (order_id, menu_item_id, quantity, customization_notes, item_price) VALUES
(4, 16, 1, NULL, 250.00);
INSERT INTO order_items (order_id, menu_item_id, quantity, customization_notes, item_price) VALUES
(5, 20, 1, NULL, 380.00);
INSERT INTO order_items (order_id, menu_item_id, quantity, customization_notes, item_price) VALUES
(5, 4, 2, 'Srednje pecena', 260.00);
