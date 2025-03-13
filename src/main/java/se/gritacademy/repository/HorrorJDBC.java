package se.gritacademy.repository;

import org.springframework.stereotype.Repository;
import se.gritacademy.model.HorrorMovies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class HorrorJDBC {
    private static final String JDBC_URL = "jdbc:h2:mem:testdb";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";


    public List<HorrorMovies> findHorrorMoviesByTitle(String title) {
        List<HorrorMovies> movies = new ArrayList<>();

        String sql = "SELECT * FROM horror_movies WHERE title LIKE '%" + title + "%'";
//        String sql = "SELECT * FROM horror_movies WHERE title = '" + title + "'";
        System.out.println(sql);
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Обработка результата запроса
            while (rs.next()) {
                HorrorMovies movie = new HorrorMovies();
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setRelease_year(rs.getInt("release_year"));
                movie.setDirector(rs.getString("director"));
                movie.setPoster(rs.getString("poster"));

                // Добавляем объект в список
                movies.add(movie);
            }
        } catch (SQLException e) {
            // Обработка ошибок
            e.printStackTrace();
        }

        return movies;
    }
}
