#!/bin/bash

# Unveil Backend - Quick Start Script

echo "🎨 Starting Unveil Backend..."
echo ""

# Check if PostgreSQL is running
echo "🔍 Checking PostgreSQL connection..."
if ! psql -U unveil -d unveil_dev -c "SELECT 1" > /dev/null 2>&1; then
    echo "⚠️  Cannot connect to PostgreSQL database 'unveil_dev'"
    echo ""
    echo "Please create the database first:"
    echo "  createdb unveil_dev"
    echo "  OR"
    echo "  psql -U postgres"
    echo "  CREATE DATABASE unveil_dev;"
    echo "  CREATE USER unveil WITH PASSWORD 'unveil_dev';"
    echo "  GRANT ALL PRIVILEGES ON DATABASE unveil_dev TO unveil;"
    echo ""
    read -p "Do you want to continue anyway? (y/n) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
else
    echo "✅ PostgreSQL connection successful"
    echo ""
fi

# Build and run
echo "🔨 Building application..."
./mvnw clean package -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Build successful!"
    echo ""
    echo "🚀 Starting application..."
    echo "📡 Server will be available at: http://localhost:8080/api"
    echo ""
    echo "Press Ctrl+C to stop the server"
    echo ""

    ./mvnw spring-boot:run
else
    echo "❌ Build failed. Please check the errors above."
    exit 1
fi
