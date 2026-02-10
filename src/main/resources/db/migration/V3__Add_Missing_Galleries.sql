-- Add missing galleries that exist in artworks but not in galleries table
-- These galleries are needed for the favorites feature to display markers on the map

-- Gallery 525 - Medieval Art (Floor 1)
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate)
VALUES ('525', 'Medieval Art', '1', 1350.0, 750.0)
ON CONFLICT (gallery_number, floor) DO UPDATE 
SET gallery_name = EXCLUDED.gallery_name,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- Gallery 101 - Egyptian Art (Floor 2)
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate)
VALUES ('101', 'Egyptian Art', '2', 1900.0, 500.0)
ON CONFLICT (gallery_number, floor) DO UPDATE 
SET gallery_name = EXCLUDED.gallery_name,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- Gallery 102 - Egyptian Art (Floor 1)
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate)
VALUES ('102', 'Egyptian Art', '1', 1900.0, 550.0)
ON CONFLICT (gallery_number, floor) DO UPDATE 
SET gallery_name = EXCLUDED.gallery_name,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- Gallery 231 - Ancient Near Eastern Art (Floor 1)
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate)
VALUES ('231', 'Ancient Near Eastern Art', '1', 1550.0, 650.0)
ON CONFLICT (gallery_number, floor) DO UPDATE 
SET gallery_name = EXCLUDED.gallery_name,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- Gallery 104 - Egyptian Art (Floor 1)
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate)
VALUES ('104', 'Egyptian Art', '1', 1850.0, 600.0)
ON CONFLICT (gallery_number, floor) DO UPDATE 
SET gallery_name = EXCLUDED.gallery_name,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;
