package se.gritacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.gritacademy.model.HorrorMovies;

import java.util.Collection;
import java.util.List;

public interface HorrorMoviesRepository extends JpaRepository<HorrorMovies, Integer> {
    public List<HorrorMovies> findHorrorMoviesByTitle(String title);

    public List<HorrorMovies> findHorrorMoviesByTitleContaining(String title);

    public List<HorrorMovies> findAllById(int movieId);
    @Query(value="SELECT * FROM horror_movies", nativeQuery=true)
    public List<HorrorMovies>findAll();

    @Query(value="SELECT * FROM horror_movies WHERE title LIKE :title",nativeQuery = true)
    List<HorrorMovies> findHorrorMoviesById(@Param("title") String title);
}
