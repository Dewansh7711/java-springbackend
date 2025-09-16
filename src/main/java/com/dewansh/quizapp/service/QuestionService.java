package com.dewansh.quizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dewansh.quizapp.Question;
import com.dewansh.quizapp.dao.QuestionDao;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);

    }

    public String addQuestion(Question question) {
        questionDao.save(question);
        return "Success";

    }

    public String deleteQuestion(Integer deleteId) {
        questionDao.deleteById(deleteId);
        return"delete";
    }

    public boolean updateFullQuestion(Integer id, Question updatedQuestion) {
        return questionDao.findById(id).map(existing -> {
        existing.setCategory(updatedQuestion.getCategory());
        existing.setDifficultylevel(updatedQuestion.getDifficultylevel());
        existing.setOption1(updatedQuestion.getOption1());
        existing.setOption2(updatedQuestion.getOption2());
        existing.setOption3(updatedQuestion.getOption3());
        existing.setOption4(updatedQuestion.getOption4());
        existing.setQuestionTitle(updatedQuestion.getQuestionTitle());
        existing.setRightAnswer(updatedQuestion.getRightAnswer());
        questionDao.save(existing);
        return true;
        }).orElse(false);
        
    }

    public boolean updatePartialQuestion(Integer id, Question updatedFields) {
        return questionDao.findById(id).map(existing -> {

            if (updatedFields.getCategory() != null) {
                existing.setCategory(updatedFields.getCategory());
            }
            if (updatedFields.getDifficultylevel() != null) {
                existing.setDifficultylevel(updatedFields.getDifficultylevel());
            }
            if (updatedFields.getOption1() != null) {
                existing.setOption1(updatedFields.getOption1());
            }
            if (updatedFields.getOption2() != null) {
                existing.setOption2(updatedFields.getOption2());
            }
            if (updatedFields.getOption3() != null) {
                existing.setOption3(updatedFields.getOption3());
            }
            if (updatedFields.getOption4() != null) {
                existing.setOption4(updatedFields.getOption4());
            }
            if (updatedFields.getQuestionTitle() != null) {
                existing.setQuestionTitle(updatedFields.getQuestionTitle());
            }
            if (updatedFields.getRightAnswer() != null) {
                existing.setRightAnswer(updatedFields.getRightAnswer());
            }

            questionDao.save(existing);
            return true;

        }).orElse(false);
    }
}
