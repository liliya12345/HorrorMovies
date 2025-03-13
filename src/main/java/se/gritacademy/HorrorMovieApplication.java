package se.gritacademy;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import se.gritacademy.model.HorrorMovies;
import se.gritacademy.model.Reviews;
import se.gritacademy.repository.HorrorJDBC;
import se.gritacademy.repository.HorrorMoviesRepository;
import se.gritacademy.repository.ReviewRepository;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;


@SpringBootApplication

public class HorrorMovieApplication {

    private final DataSource dataSource;

    public HorrorMovieApplication(DataSource dataSource) {
        this.dataSource = dataSource;
        initializeDatabase();
    }
    public static void main(String[] args) {
        SpringApplication.run(HorrorMovieApplication.class, args);
    }

    private void initializeDatabase() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS horror_movies (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), release_year INT, director VARCHAR(255), poster VARCHAR(255))");
            stmt.execute("CREATE TABLE IF NOT EXISTS reviews (id INT AUTO_INCREMENT PRIMARY KEY, movie_id INT, review TEXT, FOREIGN KEY(movie_id) REFERENCES horror_movies(id))");
            stmt.execute("INSERT INTO horror_movies (title, release_year, director, poster) VALUES ('The Exorcist', 1973, 'William Friedkin', 'exorcist.jpg'), ('Halloween', 1978, 'John Carpenter', 'halloween.jpg'), ('A Nightmare on Elm Street', 1984, 'Wes Craven', 'elm_street.jpg'), ('The Shining', 1980, 'Stanley Kubrick', 'shining.jpg')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }








}