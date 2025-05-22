CREATE DATABASE IF NOT EXISTS Sonar

CREATE TABLE IF NOT EXISTS Events (
ID INT NOT NULL PRIMARY KEY,
Address VARCHAR(50),
Category VARCHAR(50),
StartDate DATE,
EndDate DATE,
Owner VARCHAR(50),
Administrator VARCHAR(50),
Status VARCHAR(50)
);

INSERT INTO Events (ID, Address, Category, StartDate, EndDate, Owner, Administrator, Status) VALUES
(1, 'Kinderhaus Herne, Bahnhofstraße 10', 'Bastelworkshop', '2025-06-01', '2025-06-01', 'Stadt Herne', 'Lea Schumann', 'Geplant'),
(2, 'Familienzentrum Herne, Schulstraße 3', 'Kinderkonzert', '2025-06-10', '2025-06-10', 'Musikschule Herne', 'Tobias Richter', 'Geplant'),
(3, 'Jugendtreff Herne, Lindenweg 5', 'Spielenachmittag', '2025-06-15', '2025-06-15', 'Stadt Herne', 'Miriam Hoffmann', 'Offen'),
(4, 'Zoo Herne, Tierparkstraße 1', 'Tierführung für Kinder', '2025-07-01', '2025-07-01', 'Zoo Herne', 'Daniel König', 'Ausgebucht'),
(5, 'Kita Regenbogen, Blütenweg 4', 'Märchenstunde', '2025-07-05', '2025-07-05', 'Bücherei Herne', 'Sandra Weber', 'Geplant'),
(6, 'Sporthalle Herne-Mitte, Feldstraße 20', 'Kinderturnen', '2025-07-10', '2025-07-10', 'Turnverein Herne', 'Jonas Klein', 'Offen'),
(7, 'Spielplatz Südstraße, Herne', 'Kinderolympiade', '2025-07-15', '2025-07-15', 'Jugendamt Herne', 'Nina Berger', 'Geplant'),
(8, 'Museum Herne, Kulturstraße 8', 'Kinderführung im Museum', '2025-08-01', '2025-08-01', 'Museum Herne', 'Katrin Vogel', 'Geplant'),
(9, 'Parkanlage Gysenberg, Herne', 'Sommerfest für Kinder', '2025-08-10', '2025-08-10', 'Stadt Herne', 'Thomas Lange', 'Verschoben'),
(10, 'Stadtbücherei Herne, Hauptstraße 6', 'Lesewettbewerb für Kinder', '2025-08-15', '2025-08-15', 'Bücherei Herne', 'Julia Sommer', 'Geplant');
