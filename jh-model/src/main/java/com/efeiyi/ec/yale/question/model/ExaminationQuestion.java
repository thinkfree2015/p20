package com.efeiyi.ec.yale.question.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2015/12/22.
 */
@Entity
@Table(name = "yale_weixin_examination_question")
public class ExaminationQuestion {
    private String id;
    private Examination examination;
    private Question question;

    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yale_weixin_examination_id")
    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yale_weixin_question_id")
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
