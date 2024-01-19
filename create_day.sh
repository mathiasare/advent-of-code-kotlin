#!/bin/bash

# Check if exactly two parameters are provided
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <year> <day>"
    exit 1
fi

year=$1
day=$2

# Create the destination directory if it doesn't exist
destination="./src/main/kotlin/y${year}/day${day}"
mkdir -p "${destination}"

# Copy the contents of the 'day' folder from ./templates/ to the destination
cp -r "./src/main/kotlin/template/day/." "${destination}"

# Rename Day.kt to Day<day number>.kt
mv "${destination}/Day.kt" "${destination}/Day${day}.kt"

# Rename package
sed -i "s/package template.day/package y${year}.day${day}/" "${destination}/Day${day}.kt"

# Rename day function
sed -i "s/day(/day${day}(/g" "${destination}/Day${day}.kt"

echo "Folder and file copied and renamed successfully to ${destination}"