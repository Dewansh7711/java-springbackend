package com.dewansh.quizapp;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
// import jakarta.persistence.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
// import jakarta.persistence.*;

@Data
@Entity
@Table(name = "questions") // match your SQL table name
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ matches MySQL AUTO_INCREMENT
    private Integer id;

    @Column(name = "question_title") // match SQL column
    private String questionTitle;

    private String option1;
    private String option2;
    private String option3;
    private String option4;

    @Column(name = "right_answer") // match SQL column
    private String rightAnswer;

    private String difficultylevel;
    private String category; // you had this in SQL but not in entity
}
