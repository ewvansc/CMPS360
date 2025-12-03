

const express = require("express");
const fs = require("fs");
const path = require("path");

const app = express();
const PORT = 3000;


function readMoviesFile() {
  const filePath = path.join(__dirname, "movies.json");
  const fileContents = fs.readFileSync(filePath, "utf8");
  return JSON.parse(fileContents);
}


app.get("/movies", (req, res) => {
  try {
    const movies = readMoviesFile();
    res.json(movies);
  } catch (err) {
    console.error("Error reading movies.json:", err);
    res.status(500).json({ error: "Could not read movies file" });
  }
});

app.get("/movies/average-rating", (req, res) => {
  try {
    const movies = readMoviesFile();
    if (movies.length === 0) {
      return res.json({ averageRating: null, count: 0 });
    }

    const total = movies.reduce((sum, m) => sum + m.rating, 0);
    const avg = total / movies.length;

    res.json({
      count: movies.length,
      averageRating: avg,
    });
  } catch (err) {
    console.error("Error calculating average rating:", err);
    res.status(500).json({ error: "Could not calculate average rating" });
  }
});


app.listen(PORT, () => {
  console.log(`Web service running at http://localhost:${PORT}`);
  console.log("Try visiting: http://localhost:3000/movies");
  console.log("Or:          http://localhost:3000/movies/average-rating");
});
