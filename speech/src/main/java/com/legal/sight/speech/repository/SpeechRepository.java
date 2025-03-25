package com.legal.sight.speech.repository;

import com.legal.sight.speech.model.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SpeechRepository extends JpaRepository<Speech, Long> {

    @Query("SELECT s FROM Speech s WHERE LOWER(s.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<Speech> findByAuthorContaining(@Param("author") String author);

    List<Speech> findByKeywordsContaining(String keywords);

    List<Speech> findByTextContaining(String text);

    List<Speech> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
