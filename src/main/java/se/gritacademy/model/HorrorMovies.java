package se.gritacademy.model;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "horror_movies")
public class HorrorMovies {

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public HorrorMovies(int id, String title, int release_year, String director, String poster, List<Reviews> reviews) {
        this.id = id;
        this.title = title;
        this.release_year = release_year;
        this.director = director;
        this.poster = poster;
        this.reviews = reviews;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int release_year;
    private String director;
    private String poster;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reviews> reviews = new ArrayList<>();





    public HorrorMovies() {

    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


}

