-- 完整的大都会博物馆画廊坐标数据
-- 基于实际楼层平面图：Floor 1 (2680x1330), Floor 2 (2598x1350)
-- 坐标基于地图上画廊的实际位置

-- ========================================
-- FLOOR 2 (2598 x 1350)
-- ========================================

-- Modern and Contemporary Art (左上角区域) - 900系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('900', 'Modern and Contemporary Art', '2', 380, 350),
('903', 'Modern and Contemporary Art', '2', 380, 250),
('904', 'Modern and Contemporary Art', '2', 280, 180),
('905', 'Modern and Contemporary Art', '2', 360, 180),
('906', 'Modern and Contemporary Art', '2', 420, 180),
('907', 'Modern and Contemporary Art', '2', 480, 180),
('908', 'Modern and Contemporary Art', '2', 380, 400),
('909', 'Modern and Contemporary Art', '2', 420, 350),
('912', 'Modern and Contemporary Art', '2', 520, 250),
('913', 'Modern and Contemporary Art', '2', 460, 250),
('917', 'Modern and Contemporary Art', '2', 380, 450),
('918', 'Modern and Contemporary Art', '2', 380, 500),
('919', 'Modern and Contemporary Art', '2', 320, 330)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- 19th and Early 20th Century European Paintings (左侧中部) - 800系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('801', '19th-20th Century European Paintings', '2', 450, 680),
('802', '19th-20th Century European Paintings', '2', 420, 730),
('803', '19th-20th Century European Paintings', '2', 420, 780),
('804', '19th-20th Century European Paintings', '2', 360, 830),
('805', '19th-20th Century European Paintings', '2', 360, 900),
('813', '19th-20th Century European Paintings', '2', 420, 630),
('815', '19th-20th Century European Paintings', '2', 480, 630),
('817', '19th-20th Century European Paintings', '2', 520, 630),
('819', '19th-20th Century European Paintings', '2', 560, 530),
('820', '19th-20th Century European Paintings', '2', 560, 480),
('822', '19th-20th Century European Paintings', '2', 580, 530),
('823', '19th-20th Century European Paintings', '2', 540, 480),
('825', '19th-20th Century European Paintings', '2', 520, 530),
('826', '19th-20th Century European Paintings', '2', 480, 480),
('828', '19th-20th Century European Paintings', '2', 360, 480),
('830', '19th-20th Century European Paintings', '2', 420, 480)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- European Paintings 1250-1800 (中央区域) - 600系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('601', 'European Paintings, 1250-1800', '2', 1020, 530),
('602', 'European Paintings, 1250-1800', '2', 980, 530),
('603', 'European Paintings, 1250-1800', '2', 1000, 580),
('604', 'European Paintings, 1250-1800', '2', 960, 580),
('605', 'European Paintings, 1250-1800', '2', 920, 530),
('606', 'European Paintings, 1250-1800', '2', 880, 530),
('607', 'European Paintings, 1250-1800', '2', 880, 580),
('609', 'European Paintings, 1250-1800', '2', 840, 430),
('610', 'European Paintings, 1250-1800', '2', 840, 380),
('611', 'European Paintings, 1250-1800', '2', 880, 380),
('612', 'European Paintings, 1250-1800', '2', 880, 330),
('613', 'European Paintings, 1250-1800', '2', 840, 280),
('614', 'European Paintings, 1250-1800', '2', 900, 250),
('615', 'European Paintings, 1250-1800', '2', 940, 250),
('616', 'European Paintings, 1250-1800', '2', 980, 280),
('617', 'European Paintings, 1250-1800', '2', 1020, 250),
('618', 'European Paintings, 1250-1800', '2', 1020, 300),
('619', 'European Paintings, 1250-1800', '2', 1060, 280),
('620', 'European Paintings, 1250-1800', '2', 1060, 330),
('621', 'European Paintings, 1250-1800', '2', 1100, 300),
('622', 'European Paintings, 1250-1800', '2', 1120, 250),
('623', 'European Paintings, 1250-1800', '2', 1120, 330),
('624', 'European Paintings, 1250-1800', '2', 1160, 250),
('625', 'European Paintings, 1250-1800', '2', 1160, 330),
('626', 'European Paintings, 1250-1800', '2', 1200, 380),
('627', 'European Paintings, 1250-1800', '2', 1200, 430),
('628', 'European Paintings, 1250-1800', '2', 1200, 480),
('629', 'European Paintings, 1250-1800', '2', 1240, 530),
('630', 'European Paintings, 1250-1800', '2', 1200, 580),
('631', 'European Paintings, 1250-1800', '2', 1160, 530),
('634', 'European Paintings, 1250-1800', '2', 1120, 530),
('635', 'European Paintings, 1250-1800', '2', 1000, 430),
('637', 'European Paintings, 1250-1800', '2', 1000, 330),
('638', 'European Paintings, 1250-1800', '2', 1060, 430),
('640', 'European Paintings, 1250-1800', '2', 1060, 380),
('641', 'European Paintings, 1250-1800', '2', 1100, 430),
('644', 'European Paintings, 1250-1800', '2', 1160, 430),
('690', 'European Paintings, 1250-1800', '2', 1020, 630)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- The American Wing (右侧) - 700系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('704', 'The American Wing', '2', 1420, 500),
('706', 'The American Wing', '2', 1460, 250),
('717', 'The American Wing', '2', 1600, 380),
('719', 'The American Wing', '2', 1620, 330),
('720', 'The American Wing', '2', 1660, 430),
('722', 'The American Wing', '2', 1660, 480),
('747', 'The American Wing', '2', 1720, 480),
('748', 'The American Wing', '2', 1740, 480),
('750', 'The American Wing', '2', 1760, 480),
('751', 'The American Wing', '2', 1780, 430),
('753', 'The American Wing', '2', 1780, 380),
('755', 'The American Wing', '2', 1820, 330),
('757', 'The American Wing', '2', 1860, 330),
('758', 'The American Wing', '2', 1860, 280),
('759', 'The American Wing', '2', 1900, 280),
('760', 'The American Wing', '2', 1940, 230),
('761', 'The American Wing', '2', 1980, 230),
('762', 'The American Wing', '2', 2020, 200),
('763', 'The American Wing', '2', 2060, 180),
('764', 'The American Wing', '2', 2020, 150),
('765', 'The American Wing', '2', 1980, 150),
('766', 'The American Wing', '2', 1940, 120),
('767', 'The American Wing', '2', 1980, 200),
('768', 'The American Wing', '2', 1940, 200),
('769', 'The American Wing', '2', 1940, 250),
('770', 'The American Wing', '2', 1980, 250),
('771', 'The American Wing', '2', 1900, 280),
('772', 'The American Wing', '2', 1900, 250)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- Musical Instruments (中右区域) - 680系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('681', 'Musical Instruments', '2', 1420, 680),
('682', 'Musical Instruments', '2', 1580, 630),
('684', 'Musical Instruments', '2', 1460, 500)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- Asian Art (右下区域) - 200系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('205', 'Asian Art', '2', 1380, 850),
('206', 'Asian Art', '2', 1420, 850),
('207', 'Asian Art', '2', 1580, 800),
('209', 'Asian Art', '2', 1620, 850),
('213', 'Asian Art', '2', 1900, 900),
('214', 'Asian Art', '2', 1940, 900),
('215', 'Asian Art', '2', 1980, 900),
('216', 'Asian Art', '2', 2020, 900),
('217', 'Asian Art', '2', 2060, 800),
('218', 'Asian Art', '2', 2100, 850),
('223', 'Asian Art', '2', 1700, 750),
('224', 'Asian Art', '2', 1740, 700),
('227', 'Asian Art', '2', 1780, 500),
('228', 'Asian Art', '2', 1820, 530),
('229', 'Asian Art', '2', 1860, 580),
('230', 'Asian Art', '2', 1860, 630),
('231', 'Asian Art', '2', 1900, 700),
('232', 'Asian Art', '2', 1940, 750),
('233', 'Asian Art', '2', 1460, 850),
('234', 'Asian Art', '2', 1500, 900),
('235', 'Asian Art', '2', 1540, 900),
('236', 'Asian Art', '2', 1580, 900),
('237', 'Asian Art', '2', 1620, 900),
('239', 'Asian Art', '2', 1700, 900),
('240', 'Asian Art', '2', 1740, 900),
('243', 'Asian Art', '2', 1820, 900),
('245', 'Asian Art', '2', 1900, 950),
('248', 'Asian Art', '2', 2020, 950),
('250', 'Asian Art', '2', 2100, 950)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- Art of the Arab Lands and Ancient West Asia (左下) - 400系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('401', 'Art of Ancient West Asia', '2', 840, 850),
('402', 'Art of Ancient West Asia', '2', 780, 900),
('403', 'Art of Ancient Cyprus', '2', 720, 900),
('404', 'Art of Ancient Cyprus', '2', 680, 900),
('405', 'Art of Ancient Cyprus', '2', 640, 850),
('452', 'Art of the Arab Lands', '2', 560, 800),
('453', 'Art of the Arab Lands', '2', 520, 800),
('454', 'Art of the Arab Lands', '2', 480, 800),
('458', 'Art of the Arab Lands', '2', 400, 800),
('461', 'Art of the Arab Lands', '2', 360, 950),
('464', 'Art of the Arab Lands', '2', 480, 1000),
('475', 'Art of the Arab Lands', '2', 600, 800),
('176', 'Art of the Arab Lands', '2', 640, 800),
('177', 'Art of the Arab Lands', '2', 680, 800)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- Exhibition Galleries - Floor 2
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('851', 'Exhibition Galleries', '2', 700, 600),
('852', 'Exhibition Galleries', '2', 700, 700),
('853', 'Exhibition Galleries', '2', 720, 650),
('999', 'Exhibition Galleries', '2', 600, 350)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- ========================================
-- FLOOR 1 (2680 x 1330)
-- ========================================

-- Modern and Contemporary Art (左上) - 900系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('900', 'Modern and Contemporary Art', '1', 400, 380),
('903', 'Modern and Contemporary Art', '1', 400, 280),
('904', 'Modern and Contemporary Art', '1', 300, 200),
('905', 'Modern and Contemporary Art', '1', 380, 200),
('906', 'Modern and Contemporary Art', '1', 440, 200),
('907', 'Modern and Contemporary Art', '1', 500, 200),
('908', 'Modern and Contemporary Art', '1', 400, 430),
('909', 'Modern and Contemporary Art', '1', 440, 380),
('912', 'Modern and Contemporary Art', '1', 540, 280),
('913', 'Modern and Contemporary Art', '1', 480, 280)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- European Sculpture and Decorative Arts (中央) - 500系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('500', 'European Sculpture and Decorative Arts', '1', 940, 600),
('503', 'European Sculpture and Decorative Arts', '1', 1120, 580),
('504', 'European Sculpture and Decorative Arts', '1', 1180, 600),
('508', 'European Sculpture and Decorative Arts', '1', 1120, 500),
('509', 'European Sculpture and Decorative Arts', '1', 1180, 450),
('510', 'European Sculpture and Decorative Arts', '1', 1180, 380),
('512', 'European Sculpture and Decorative Arts', '1', 1240, 380),
('513', 'European Sculpture and Decorative Arts', '1', 1280, 280),
('515', 'European Sculpture and Decorative Arts', '1', 1340, 280),
('516', 'European Sculpture and Decorative Arts', '1', 1340, 380),
('520', 'European Sculpture and Decorative Arts', '1', 1180, 330),
('521', 'European Sculpture and Decorative Arts', '1', 1240, 280),
('522', 'European Sculpture and Decorative Arts', '1', 1180, 330),
('524', 'European Sculpture and Decorative Arts', '1', 1120, 330),
('525', 'Medieval Art', '1', 1080, 330),
('526', 'Medieval Art', '1', 1040, 280),
('527', 'Medieval Art', '1', 1080, 280),
('528', 'Medieval Art', '1', 1080, 380),
('536', 'European Sculpture and Decorative Arts', '1', 1000, 650),
('537', 'European Sculpture and Decorative Arts', '1', 1040, 680),
('538', 'European Sculpture and Decorative Arts', '1', 1040, 650),
('539', 'European Sculpture and Decorative Arts', '1', 1000, 550),
('542', 'Medieval Art', '1', 1180, 550),
('545', 'Medieval Art', '1', 1120, 380),
('549', 'Medieval Art', '1', 1120, 330),
('550', 'European Sculpture and Decorative Arts', '1', 1000, 500),
('552', 'European Sculpture and Decorative Arts', '1', 1120, 430),
('554', 'European Sculpture and Decorative Arts', '1', 1000, 330),
('555', 'European Sculpture and Decorative Arts', '1', 1040, 430),
('556', 'European Sculpture and Decorative Arts', '1', 1000, 550)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- Medieval Art (中央) - 300系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('300', 'Medieval Art', '1', 940, 700),
('303', 'Medieval Art', '1', 980, 680),
('304', 'Medieval Art', '1', 980, 650)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- Arms and Armor (右中) - 370系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('373', 'Arms and Armor', '1', 1340, 550),
('374', 'Arms and Armor', '1', 1400, 550),
('375', 'Arms and Armor', '1', 1460, 550),
('376', 'Arms and Armor', '1', 1520, 550),
('377', 'Arms and Armor', '1', 1340, 630),
('378', 'Arms and Armor', '1', 1400, 630),
('379', 'Arms and Armor', '1', 1460, 630)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- The American Wing (右上) - 700系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('700', 'The American Wing', '1', 1540, 380),
('723', 'The American Wing', '1', 1620, 430),
('725', 'The American Wing', '1', 1700, 330),
('728', 'The American Wing', '1', 1780, 430),
('730', 'The American Wing', '1', 1700, 430),
('731', 'The American Wing', '1', 1740, 430),
('732', 'The American Wing', '1', 1780, 480),
('735', 'The American Wing', '1', 1900, 330),
('738', 'The American Wing', '1', 2020, 200),
('740', 'The American Wing', '1', 2020, 150),
('741', 'The American Wing', '1', 2060, 150),
('742', 'The American Wing', '1', 2020, 200),
('743', 'The American Wing', '1', 2020, 150),
('744', 'The American Wing', '1', 1940, 330),
('745', 'The American Wing', '1', 1900, 280),
('746 South', 'The American Wing', '1', 1940, 280)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- Greek and Roman Art (左下) - 150-170系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('151', 'Greek and Roman Art', '1', 820, 850),
('152', 'Greek and Roman Art', '1', 780, 900),
('153', 'Greek and Roman Art', '1', 740, 950),
('154', 'Greek and Roman Art', '1', 700, 900),
('155', 'Greek and Roman Art', '1', 740, 850),
('156', 'Greek and Roman Art', '1', 700, 900),
('157', 'Greek and Roman Art', '1', 780, 850),
('158', 'Greek and Roman Art', '1', 660, 900),
('159', 'Greek and Roman Art', '1', 740, 850),
('162', 'Greek and Roman Art', '1', 540, 900),
('163', 'Greek and Roman Art', '1', 620, 1000),
('164', 'Greek and Roman Art', '1', 580, 1000),
('165', 'Greek and Roman Art', '1', 540, 1000),
('166', 'Greek and Roman Art', '1', 500, 1000),
('167', 'Greek and Roman Art', '1', 460, 1000),
('168', 'Greek and Roman Art', '1', 420, 1000),
('169', 'Greek and Roman Art', '1', 380, 950)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- Egyptian Art (右下) - 100系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('100', 'Egyptian Art', '1', 1420, 900),
('101', 'Egyptian Art', '1', 1380, 900),
('103', 'Egyptian Art', '1', 1500, 900),
('104', 'Egyptian Art', '1', 1540, 900),
('105', 'Egyptian Art', '1', 1580, 1000),
('106', 'Egyptian Art', '1', 1540, 1000),
('107', 'Egyptian Art', '1', 1620, 900),
('108', 'Egyptian Art', '1', 1660, 900),
('110', 'Egyptian Art', '1', 1700, 1000),
('111', 'Egyptian Art', '1', 1740, 1000),
('112', 'Egyptian Art', '1', 1700, 900),
('114', 'Egyptian Art', '1', 1860, 1000),
('115', 'Egyptian Art', '1', 1900, 1000),
('116', 'Egyptian Art', '1', 1940, 1000),
('118', 'Egyptian Art', '1', 2020, 1000),
('119', 'Egyptian Art', '1', 2060, 950),
('121', 'Egyptian Art', '1', 2060, 900),
('127', 'Egyptian Art', '1', 2100, 850)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- The Michael C. Rockefeller Wing (左中下) - 300-360系列
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('344', 'The Michael C. Rockefeller Wing', '1', 540, 800),
('345', 'The Michael C. Rockefeller Wing', '1', 540, 750),
('354', 'The Michael C. Rockefeller Wing', '1', 480, 700),
('360', 'The Michael C. Rockefeller Wing', '1', 480, 600),
('362', 'The Michael C. Rockefeller Wing', '1', 520, 550),
('363', 'The Michael C. Rockefeller Wing', '1', 560, 550)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- Exhibition Gallery 199 (中央下方)
INSERT INTO galleries (gallery_number, gallery_name, floor, x_coordinate, y_coordinate) VALUES 
('199', 'Exhibition Gallery', '1', 900, 700)
ON CONFLICT (gallery_number) DO UPDATE SET
    gallery_name = EXCLUDED.gallery_name,
    floor = EXCLUDED.floor,
    x_coordinate = EXCLUDED.x_coordinate,
    y_coordinate = EXCLUDED.y_coordinate;

-- 验证结果
SELECT 
    floor,
    COUNT(*) as total_galleries,
    MIN(gallery_number) as min_gallery,
    MAX(gallery_number) as max_gallery
FROM galleries
GROUP BY floor
ORDER BY floor DESC;

SELECT '所有画廊已更新完成！' as status;
