package se.gritacademy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.gritacademy.model.HorrorMovies;
import se.gritacademy.model.Reviews;
import se.gritacademy.repository.HorrorJDBC;
import se.gritacademy.repository.HorrorMoviesRepository;
import se.gritacademy.repository.ReviewRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/movies")
public class HorrorMovieController {
    @Autowired
   private HorrorJDBC horrorJDBC;
    @Autowired
    private HorrorMoviesRepository horrorMoviesRepository;
    @Autowired
    private ReviewRepository reviewRepository;


    @GetMapping("/search2")

    public List<HorrorMovies> findByTitle(@RequestParam String title) {
//        String maliciousInput = "'; DROP TABLE reviews; --";
        return horrorJDBC.findHorrorMoviesByTitle(title);
    }
    @GetMapping
    public List<Map<String, Object>> getMovies(@RequestParam(value = "search", required = false) String search) {

        List<Map<String, Object>> movies = new ArrayList<>();
        System.out.println(search);
        if (search != null && !search.isEmpty()) {
//            List<HorrorMovies> byTitle = horrorMoviesRepository.findHorrorMoviesByTitleContaining(search);
            List<HorrorMovies> byTitle = horrorJDBC.findHorrorMoviesByTitle(search);
            if (byTitle != null && !byTitle.isEmpty()) {
                for (HorrorMovies movie : byTitle) {
                    Map<String, Object> movieMap = new HashMap<>();
                    movieMap.put("id", movie.getId());
                    movieMap.put("title", movie.getTitle());
                    movieMap.put("release_year", movie.getRelease_year());
                    movieMap.put("director", movie.getDirector());
                    movieMap.put("poster", movie.getPoster());
                    movies.add(movieMap);
                }
            }
        } else {
            // Fetch all movies if no search parameter is provided
            List<HorrorMovies> allMovies = horrorMoviesRepository.findAll();
            for (HorrorMovies movie : allMovies) {
                Map<String, Object> movieMap = new HashMap<>();
                movieMap.put("id", movie.getId());
                movieMap.put("title", movie.getTitle());
                movieMap.put("release_year", movie.getRelease_year());
                movieMap.put("director", movie.getDirector());
                movieMap.put("poster", movie.getPoster());
                movies.add(movieMap);
            }
        }

        return movies;
    }

    @PostMapping("/review")
    public String addReview(@RequestParam int movieId, @RequestParam String review) {
        System.out.println(review);
        Reviews feedback  = new Reviews();
        Optional<HorrorMovies> first = horrorMoviesRepository.findAllById(movieId).stream().findFirst();
        if(first.isPresent()) {
            feedback.setMovie_id((long) movieId);
            feedback.setReview(review);
            reviewRepository.save(feedback);
        }

        return "Review was successfully added!"+movieId;
    }

    @GetMapping("/reviews")
    public List<Reviews> getReviews(@RequestParam int movieId) {
        List<Reviews> reviewsList = reviewRepository.findReviewsByMovie_id((long) movieId);
        return reviewsList;


    }
    @GetMapping("/find_poster/{movieId}")
    public String getPoster(@PathVariable int movieId) {
        HorrorMovies movie = horrorMoviesRepository.findById(movieId).orElse(null);
        String poster = movie.getPoster();
        return poster;

    }

    @GetMapping("/poster")
    public ResponseEntity<byte[]> getMoviePoster(@RequestParam String filename) {
        // Construct the file path
        Path path = Paths.get("posters/" + filename);

        // Check if the file exists
        if (!Files.exists(path)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            // Read the file into a byte array
            byte[] image = Files.readAllBytes(path);

            // Determine the content type dynamically

            // Return the image with the appropriate content type
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                    .body(image);
        } catch (IOException e) {
            // Handle IO errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }


}
