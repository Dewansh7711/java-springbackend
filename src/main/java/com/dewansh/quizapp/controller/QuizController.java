package com.dewansh.quizapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dewansh.quizapp.model.QuestionWrapper;
import com.dewansh.quizapp.model.Response;
import com.dewansh.quizapp.service.QuizService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired

    QuizService QuizService;


    @PostMapping("/create")
    // here @RequestParam is used to get the parameters from the URL and to get from body use @RequestBody
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam String Title){
        return QuizService.createQuiz(category, Title);
    }

    @GetMapping("/get/{quizNumber}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable Integer quizNumber){
        return QuizService.getQuiz(quizNumber);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return QuizService.calculateResult(id, responses);
    }

}
