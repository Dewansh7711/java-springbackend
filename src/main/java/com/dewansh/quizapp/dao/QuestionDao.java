package com.dewansh.quizapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dewansh.quizapp.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    List<Question> findRandomQuestionByCategory(String category, int numQ);
}
