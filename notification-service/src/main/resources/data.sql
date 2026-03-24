-- Notifikacije
INSERT INTO notifications (user_email, type, subject, message, sent_at, status) VALUES
('ana.ivanovic@raf.rs', 'REGISTRATION', 'Dobrodošli u RafCafe!', 'Poštovana Ana, dobrodošli u RafCafe! Vaš nalog je uspešno kreiran. Uživajte u našoj ponudi!', '2026-03-15 10:00:00', 'SENT');

INSERT INTO notifications (user_email, type, subject, message, sent_at, status) VALUES
('ana.ivanovic@raf.rs', 'ORDER_CONFIRMATION', 'Potvrda narudžbine #1', 'Vaša narudžbina #1 je primljena i biće pripremljena u najkraćem roku. Hvala na poverenju!', '2026-03-20 09:15:00', 'SENT');

INSERT INTO notifications (user_email, type, subject, message, sent_at, status) VALUES
('ana.ivanovic@raf.rs', 'ORDER_STATUS_CHANGE', 'Narudžbina #1 isporučena', 'Vaša narudžbina #1 je uspešno isporučena. Prijatno! Ocenite nas na Google Maps.', '2026-03-20 09:45:00', 'SENT');

INSERT INTO notifications (user_email, type, subject, message, sent_at, status) VALUES
('marko.jovanovic@raf.rs', 'REGISTRATION', 'Dobrodošli u RafCafe!', 'Poštovani Marko, dobrodošli u RafCafe! Vaš nalog je uspešno kreiran.', '2026-03-16 14:30:00', 'SENT');

INSERT INTO notifications (user_email, type, subject, message, sent_at, status) VALUES
('marko.jovanovic@raf.rs', 'ORDER_CONFIRMATION', 'Potvrda narudžbine #2', 'Vaša narudžbina #2 je primljena. Club sendvič i kapucino stižu uskoro!', '2026-03-23 10:30:00', 'SENT');

INSERT INTO notifications (user_email, type, subject, message, sent_at, status) VALUES
('jelena.petrovic@raf.rs', 'REGISTRATION', 'Dobrodošli u RafCafe!', 'Poštovana Jelena, dobrodošli u RafCafe! Kao poklon dobijate 50 loyalty poena!', '2026-03-17 11:00:00', 'SENT');

INSERT INTO notifications (user_email, type, subject, message, sent_at, status) VALUES
('jelena.petrovic@raf.rs', 'PROMOTIONAL', 'Vikend akcija - 20% popusta!', 'Ovog vikenda svi deserti su na 20% popusta. Probajte naš cheesecake ili tiramisu!', '2026-03-21 08:00:00', 'SENT');

INSERT INTO notifications (user_email, type, subject, message, sent_at, status) VALUES
('milica.nikolic@raf.rs', 'ORDER_STATUS_CHANGE', 'Narudžbina #5 spremna!', 'Vaša narudžbina #5 je spremna za preuzimanje. Prijatno!', '2026-03-23 09:00:00', 'SENT');

INSERT INTO notifications (user_email, type, subject, message, sent_at, status) VALUES
('ana.ivanovic@raf.rs', 'ORDER_CANCELLED', 'Narudžbina #4 otkazana', 'Vaša narudžbina #4 je uspešno otkazana. Iznos će biti vraćen na vaš račun.', '2026-03-22 14:25:00', 'SENT');

INSERT INTO notifications (user_email, type, subject, message, sent_at, status) VALUES
('milica.nikolic@raf.rs', 'PROMOTIONAL', 'Novi letnji meni!', 'Pogledajte naše nove letnje napitke: Ledena kafa, Ledeni čaj od breskve i Mango smoothie!', '2026-03-22 10:00:00', 'SENT');
