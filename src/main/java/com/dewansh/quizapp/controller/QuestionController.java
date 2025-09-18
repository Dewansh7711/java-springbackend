package com.dewansh.quizapp.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dewansh.quizapp.Question;
import com.dewansh.quizapp.service.QuestionService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("question")
public class QuestionController { 

    @Autowired
    QuestionService questionService;

   @GetMapping("/")
    public String home() {
        return "Welcome to Quiz App";
    }
    @GetMapping("allQuestions")
    public List<Question> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public String addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);  
    }

    @DeleteMapping("delete/{delete}")
    public String deleteQuestion(@PathVariable("delete") Integer deleteId){
        return questionService.deleteQuestion(deleteId);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateFullQuestion(@PathVariable Integer id, @RequestBody Question updatedQuestion){
        boolean updated = questionService.updateFullQuestion(id, updatedQuestion);
        if (updated){
            return ResponseEntity.ok("Question updated successfully!");
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("question now found");
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updatePartialQuestion(
            @PathVariable Integer id,
            @RequestBody Question updatedFields) {

        boolean updated = questionService.updatePartialQuestion(id, updatedFields);

        if (updated) {
            return ResponseEntity.ok("Question updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Question not found");
        }
    }
}