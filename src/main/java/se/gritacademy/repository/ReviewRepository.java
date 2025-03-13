package se.gritacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.gritacademy.model.Reviews;

import java.util.List;

public interface ReviewRepository  extends JpaRepository<Reviews, Integer> {
public List<Reviews> findReviewsByMovie_id(Long movieId);

}
