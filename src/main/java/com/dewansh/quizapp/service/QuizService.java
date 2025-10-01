package com.dewansh.quizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dewansh.quizapp.dao.QuestionDao;
import com.dewansh.quizapp.dao.QuizDao;
import com.dewansh.quizapp.model.Question;
import com.dewansh.quizapp.model.QuestionWrapper;
import com.dewansh.quizapp.model.Quiz;
import com.dewansh.quizapp.model.Response;

@Service
public class QuizService{

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category,  String title) {

        List<Question> questions = questionDao.findRandomQuestionByCategory(category);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<String>("Quiz created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer quizNumber) {
        Optional<Quiz> quiz = quizDao.findById(quizNumber);

        List<Question> questionFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();
        for(Question q : questionFromDB){
            QuestionWrapper qw = new QuestionWrapper(
                q.getId(),
                q.getQuestionTitle(),
                q.getOption1(),
                q.getOption2(),
                q.getOption3(),
                q.getOption4()
            );
            questionForUser.add(qw);
        }
        // questionFromDB.stream().map(q -> new QuestionWrapper(
        //     q.getId(),
        //     q.getQuestionTitle(),
        //     q.getOption1(),
        //     q.getOption2(),
        //     q.getOption3(),
        //     q.getOption4()
        // )).toList();

        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();

        int right = 0;
        for(Response response: responses){
            for(Question question: questions){
                if(question.getId() == response.getId() && question.getRightAnswer().equals(response.getResponse())){
                    right++;
                    break;
                }
            }
        }
        return new ResponseEntity<Integer>(right, HttpStatus.OK);

    }

    

}
