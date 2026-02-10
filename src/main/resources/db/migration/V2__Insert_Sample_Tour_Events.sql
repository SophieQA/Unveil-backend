-- V2__Insert_Sample_Tour_Events.sql
-- Sample Tour Events Data for MVP Phase

-- Insert Sample Tours
INSERT INTO tour_events (type, title, description, start_time, end_time, location, event_date, is_active)
VALUES
('TOUR', 'Highlights of The Met', 'Introduction to the greatest works of art in the Met collection', '2026-02-10 10:30:00', '2026-02-10 12:00:00', 'Great Hall', '2026-02-10', true),
('TOUR', 'Egyptian Art Tour', 'Explore ancient Egyptian civilization and pharaonic artifacts', '2026-02-10 14:00:00', '2026-02-10 15:00:00', 'Gallery 100', '2026-02-10', true),
('TOUR', 'European Paintings Gallery Walk', 'A guided tour through the finest European paintings from the Renaissance to the Impressionist era', '2026-02-11 11:00:00', '2026-02-11 12:30:00', 'Gallery 800', '2026-02-11', true),
('TOUR', 'Asian Art Highlights', 'Discover the beauty of Chinese, Japanese, and Indian art', '2026-02-11 14:30:00', '2026-02-11 15:30:00', 'Gallery 200', '2026-02-11', true),
('TOUR', 'Medieval Art and Architecture', 'Step back in time with medieval treasures and architectural marvels', '2026-02-12 10:00:00', '2026-02-12 11:30:00', 'Gallery 300', '2026-02-12', true),
('TOUR', 'Greek and Roman Antiquities', 'Explore the legacy of ancient Greece and Rome through sculpture and pottery', '2026-02-12 13:00:00', '2026-02-12 14:30:00', 'Gallery 199', '2026-02-12', true),
('TOUR', 'American Art from Colonial to Modern', 'A comprehensive overview of American artistic traditions', '2026-02-13 15:00:00', '2026-02-13 16:30:00', 'Gallery 750', '2026-02-13', true),
('TOUR', 'Armor and Weapons Through the Ages', 'Fascinating collection of historical military equipment and ornate armor', '2026-02-13 11:00:00', '2026-02-13 12:00:00', 'Gallery 370', '2026-02-13', true);

-- Insert Sample Events
INSERT INTO tour_events (type, title, description, start_time, end_time, location, event_date, is_active)
VALUES
('EVENT', 'MetFridays: Music and Art', 'Live music, gallery talks, and cocktails in the Great Hall', '2026-02-14 17:00:00', '2026-02-14 21:00:00', 'Great Hall', '2026-02-14', true),
('EVENT', 'Member Lecture Series: Van Gogh''s Letters', 'Curator-led discussion on the correspondence and artistic journey of Vincent van Gogh', '2026-02-15 14:00:00', '2026-02-15 15:30:00', 'Auditorium', '2026-02-15', true),
('EVENT', 'Photography Workshop: Capturing Museum Moments', 'Learn professional photography techniques in the galleries', '2026-02-16 10:00:00', '2026-02-16 12:00:00', 'Photography Studio', '2026-02-16', true),
('EVENT', 'Family Art Day: Create Your Masterpiece', 'Interactive art activities for children and families', '2026-02-17 11:00:00', '2026-02-17 16:00:00', 'Education Wing', '2026-02-17', true),
('EVENT', 'Behind the Scenes: Conservation Lab Tour', 'See how the Met preserves and restores artworks', '2026-02-17 14:00:00', '2026-02-17 15:00:00', 'Conservation Lab', '2026-02-17', true),
('EVENT', 'Evening Exhibition Preview: Contemporary Masters', 'Preview of the latest contemporary art exhibition with wine and cheese', '2026-02-18 17:30:00', '2026-02-18 19:30:00', 'Modern Wing', '2026-02-18', true),
('EVENT', 'Artist Talk: Stories Behind the Sculptures', 'Renowned sculptor discusses her creative process and inspirations', '2026-02-19 13:00:00', '2026-02-19 14:30:00', 'Auditorium', '2026-02-19', true);

-- Insert Sample Galleries
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate, description)
VALUES
('199', 'Greek and Roman Art', '1', 780.2, 540.3, 'Classical antiquities including sculptures, pottery, and artifacts'),
('201', 'Ancient Near Eastern Art', '1', 650.5, 480.2, 'Art from ancient Mesopotamia, Persia, and the Levant'),
('300', 'Medieval Art', '1', 450.8, 620.5, 'European medieval art spanning from the Early Medieval to Gothic periods'),
('370', 'Armor and Arms', '1', 320.2, 700.1, 'Historic military equipment and ornate armor from around the world'),
('100', 'Egyptian Art', '1', 850.3, 350.8, 'One of the world''s finest collections of Egyptian antiquities'),
('800', 'European Paintings', '2', 450.5, 320.8, 'Masterpieces from European painters of the Renaissance through Modern eras'),
('822', 'Impressionist Paintings', '2', 380.9, 280.4, 'Works by Monet, Renoir, Cézanne and other Impressionist masters'),
('750', 'American Wing', '1', 200.5, 450.3, 'American art from colonial times to the 20th century'),
('200', 'Asian Art', '2', 600.2, 400.7, 'Comprehensive collection of Chinese, Japanese, Indian and Southeast Asian art'),
('505', 'Islamic Art', '1', 720.1, 580.9, 'Decorative arts and manuscripts from Islamic civilizations');
