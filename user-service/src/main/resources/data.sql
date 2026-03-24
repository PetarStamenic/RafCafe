-- Seed korisnici za RafCafe
-- Lozinka za sve: password123 (BCrypt encoded)

INSERT INTO users (email, password, first_name, last_name, role, loyalty_points, active) VALUES
('ana.ivanovic@raf.rs', '$2b$10$dkVvhUQjNwiOjADOfdy//ePcrSnamon8Mb4cJlYamPLwJOzfQoo0K', 'Ana', 'Ivanović', 'CUSTOMER', 120, true);

INSERT INTO users (email, password, first_name, last_name, role, loyalty_points, active) VALUES
('marko.jovanovic@raf.rs', '$2b$10$dkVvhUQjNwiOjADOfdy//ePcrSnamon8Mb4cJlYamPLwJOzfQoo0K', 'Marko', 'Jovanović', 'CUSTOMER', 85, true);

INSERT INTO users (email, password, first_name, last_name, role, loyalty_points, active) VALUES
('jelena.petrovic@raf.rs', '$2b$10$dkVvhUQjNwiOjADOfdy//ePcrSnamon8Mb4cJlYamPLwJOzfQoo0K', 'Jelena', 'Petrović', 'CUSTOMER', 200, true);

INSERT INTO users (email, password, first_name, last_name, role, loyalty_points, active) VALUES
('nikola.djordjevic@raf.rs', '$2b$10$dkVvhUQjNwiOjADOfdy//ePcrSnamon8Mb4cJlYamPLwJOzfQoo0K', 'Nikola', 'Đorđević', 'CUSTOMER', 50, true);

INSERT INTO users (email, password, first_name, last_name, role, loyalty_points, active) VALUES
('milica.nikolic@raf.rs', '$2b$10$dkVvhUQjNwiOjADOfdy//ePcrSnamon8Mb4cJlYamPLwJOzfQoo0K', 'Milica', 'Nikolić', 'CUSTOMER', 310, true);

INSERT INTO users (email, password, first_name, last_name, role, loyalty_points, active) VALUES
('stefan.milosevic@raf.rs', '$2b$10$dkVvhUQjNwiOjADOfdy//ePcrSnamon8Mb4cJlYamPLwJOzfQoo0K', 'Stefan', 'Milošević', 'MANAGER', 0, true);

INSERT INTO users (email, password, first_name, last_name, role, loyalty_points, active) VALUES
('admin@raf.rs', '$2b$10$dkVvhUQjNwiOjADOfdy//ePcrSnamon8Mb4cJlYamPLwJOzfQoo0K', 'Admin', 'RafCafe', 'ADMIN', 0, true);

INSERT INTO users (email, password, first_name, last_name, role, loyalty_points, active) VALUES
('maja.stojanovic@raf.rs', '$2b$10$dkVvhUQjNwiOjADOfdy//ePcrSnamon8Mb4cJlYamPLwJOzfQoo0K', 'Maja', 'Stojanović', 'CUSTOMER', 45, true);

INSERT INTO users (email, password, first_name, last_name, role, loyalty_points, active) VALUES
('lazar.popovic@raf.rs', '$2b$10$dkVvhUQjNwiOjADOfdy//ePcrSnamon8Mb4cJlYamPLwJOzfQoo0K', 'Lazar', 'Popović', 'CUSTOMER', 175, true);

INSERT INTO users (email, password, first_name, last_name, role, loyalty_points, active) VALUES
('teodora.markovic@raf.rs', '$2b$10$dkVvhUQjNwiOjADOfdy//ePcrSnamon8Mb4cJlYamPLwJOzfQoo0K', 'Teodora', 'Marković', 'CUSTOMER', 90, true);
