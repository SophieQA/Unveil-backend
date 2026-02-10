-- V1__Create_Tour_Plan_Tables.sql
-- Migration Script for Tour Plan Feature - Phase 1: Database Schema

-- Create galleries table
CREATE TABLE IF NOT EXISTS galleries (
    id BIGSERIAL PRIMARY KEY,
    gallery_number VARCHAR(50) NOT NULL UNIQUE,
    gallery_name VARCHAR(255),
    floor VARCHAR(20) NOT NULL,
    x_coordinate DECIMAL(10, 6),
    y_coordinate DECIMAL(10, 6),
    polygon_data JSONB,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for galleries
CREATE INDEX IF NOT EXISTS idx_gallery_number ON galleries(gallery_number);
CREATE INDEX IF NOT EXISTS idx_gallery_floor ON galleries(floor);

-- Create tour_events table
CREATE TABLE IF NOT EXISTS tour_events (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    title VARCHAR(500) NOT NULL,
    description TEXT,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    location VARCHAR(255),
    source_url VARCHAR(1000),
    event_date DATE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for tour_events
CREATE INDEX IF NOT EXISTS idx_tour_event_type ON tour_events(type);
CREATE INDEX IF NOT EXISTS idx_tour_event_date ON tour_events(event_date);
CREATE INDEX IF NOT EXISTS idx_tour_event_start_time ON tour_events(start_time);

-- Create plan_items table
CREATE TABLE IF NOT EXISTS plan_items (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    tour_event_id BIGINT NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_plan_items_tour_event FOREIGN KEY (tour_event_id) REFERENCES tour_events(id) ON DELETE CASCADE
);

-- Create indexes for plan_items
CREATE INDEX IF NOT EXISTS idx_plan_user_id ON plan_items(user_id);
CREATE INDEX IF NOT EXISTS idx_plan_tour_event ON plan_items(tour_event_id);
CREATE UNIQUE INDEX IF NOT EXISTS idx_plan_user_tour ON plan_items(user_id, tour_event_id);

-- Extend artworks table with gallery information
ALTER TABLE artworks
ADD COLUMN IF NOT EXISTS gallery_number VARCHAR(50),
ADD COLUMN IF NOT EXISTS gallery_id BIGINT;

-- Create foreign key constraint if not exists
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'artworks' AND constraint_name = 'fk_artworks_gallery'
    ) THEN
        ALTER TABLE artworks ADD CONSTRAINT fk_artworks_gallery
        FOREIGN KEY (gallery_id) REFERENCES galleries(id);
    END IF;
END $$;

-- Create index for artwork_gallery
CREATE INDEX IF NOT EXISTS idx_artwork_gallery ON artworks(gallery_number);
