-- Sync artwork_views data into artworks table
-- This script runs automatically when the application starts (ddl-auto=update)
INSERT INTO artworks (artwork_id, title, artist, image_url, museum_source, year, description, created_at)
SELECT DISTINCT
    av.artwork_id,
    av.title,
    av.artist,
    av.image_url,
    av.museum_source,
    NULL as year,
    NULL as description,
    CURRENT_TIMESTAMP as created_at
FROM artwork_views av
WHERE NOT EXISTS (
    SELECT 1 FROM artworks a WHERE a.artwork_id = av.artwork_id
);
