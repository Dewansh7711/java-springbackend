package com.dewansh.quizapp.model;

import jakarta.persistence.Column;
import lombok.Data;

// we are making this class so that we can remove the right asnwer 
//from when we send the data of the quiz when requestied

@Data
public class QuestionWrapper {

    private Integer id;

    @Column(name = "question_title") // match SQL column
    private String questionTitle;

    private String option1;
    private String option2;
    private String option3;
    private String option4;


    public QuestionWrapper(Integer id, String questionTitle, String option1, String option2, String option3,
            String option4) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }
}
