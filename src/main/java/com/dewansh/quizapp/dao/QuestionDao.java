package com.dewansh.quizapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dewansh.quizapp.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);


   
    @Query(value = "SELECT * FROM questions WHERE category = :category ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Question> findRandomQuestionByCategory(@Param("category") String category);
}