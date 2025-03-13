package se.gritacademy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long movie_id;

    private String review;

    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    @JsonBackReference //
    private HorrorMovies movie;

    public Reviews() {
    }


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public HorrorMovies getMovie() {
        return movie;
    }

    public void setMovie(HorrorMovies movie) {
        this.movie = movie;
    }

    public Long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }
}