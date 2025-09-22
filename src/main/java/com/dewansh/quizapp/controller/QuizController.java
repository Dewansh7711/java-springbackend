package com.dewansh.quizapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dewansh.quizapp.service.quizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired

    quizService QuizService;


    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String Category, @RequestParam int numQ, @RequestParam String Title){
        return QuizService.createQuiz(Category, numQ, Title);
    }

}
