#!/bin/bash

# Unveil Backend - API Test Script

BASE_URL="http://localhost:8080/api"

echo "🎨 Testing Unveil Backend API"
echo "=============================="
echo ""

# Test 1: Health Check
echo "1️⃣  Testing health endpoint..."
response=$(curl -s -w "\n%{http_code}" "${BASE_URL}/artworks/health")
http_code=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)

if [ "$http_code" = "200" ]; then
    echo "✅ Health check passed: $body"
else
    echo "❌ Health check failed (HTTP $http_code)"
fi
echo ""

# Test 2: Get Random Artwork
echo "2️⃣  Testing random artwork endpoint..."
response=$(curl -s -w "\n%{http_code}" "${BASE_URL}/artworks/random")
http_code=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)

if [ "$http_code" = "200" ]; then
    echo "✅ Random artwork retrieved successfully!"
    echo "$body" | python3 -m json.tool 2>/dev/null || echo "$body"
else
    echo "❌ Failed to get random artwork (HTTP $http_code)"
    echo "$body"
fi
echo ""

# Test 3: Record View
echo "3️⃣  Testing record view endpoint..."
artwork_data='{
  "artworkId": "test-123",
  "title": "Test Artwork",
  "artist": "Test Artist",
  "imageUrl": "https://example.com/test.jpg",
  "museumSource": "museum1",
  "objectDate": "2024",
  "medium": "Digital",
  "dimensions": "100x100",
  "creditLine": "Test Museum"
}'

response=$(curl -s -w "\n%{http_code}" -X POST "${BASE_URL}/artworks/view" \
  -H "Content-Type: application/json" \
  -H "X-User-Id: test-user" \
  -d "$artwork_data")
http_code=$(echo "$response" | tail -n1)

if [ "$http_code" = "202" ]; then
    echo "✅ View recorded successfully (HTTP 202 Accepted)"
else
    echo "❌ Failed to record view (HTTP $http_code)"
fi
echo ""

# Test 4: Get History
echo "4️⃣  Testing history endpoint..."
sleep 1  # Wait a moment for async view recording

response=$(curl -s -w "\n%{http_code}" "${BASE_URL}/artworks/history?limit=5" \
  -H "X-User-Id: test-user")
http_code=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)

if [ "$http_code" = "200" ]; then
    echo "✅ History retrieved successfully!"
    echo "$body" | python3 -m json.tool 2>/dev/null || echo "$body"
else
    echo "❌ Failed to get history (HTTP $http_code)"
    echo "$body"
fi
echo ""

echo "=============================="
echo "🎉 API tests completed!"
