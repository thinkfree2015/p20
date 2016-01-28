package com.efeiyi.ec.yale.question.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2015/12/22.
 */
@Entity
@Table(name = "yale_weixin_examination_question")
public class ExaminationQuestion {
    private String id;
    private Examination examination;
    private Question question;
    private int questionOrder;
    private String answer;
    private String answerStatus;// 1.正确 2.错误
    private Map<String, String> randomAnswerMap;

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

    @Column(name = "question_order")
    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }

    @Column(name = "answer_status")
    public String getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(String answerStatus) {
        this.answerStatus = answerStatus;
    }

    @Column(name = "answer")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    //答案随机排个序
    public Map<String, String> getRandomAnswerMap() {
        Map<Integer, String> storeAnswerMap = new HashMap<>();
        storeAnswerMap.put(0, question.getAnswerA());
        storeAnswerMap.put(1, question.getAnswerB());
        storeAnswerMap.put(2, question.getAnswerC());
        storeAnswerMap.put(3, question.getAnswerD());
        Random random = new Random();
        Map<String, String> randomAnswerMap = new HashMap<>();
        for (int x = 4; x > 0; x--) {
            randomAnswerMap.put(numberMap2Letter(4 - x), storeAnswerMap.remove(random.nextInt(x)));
        }
        this.randomAnswerMap = randomAnswerMap;
        return this.randomAnswerMap;
    }

    private String numberMap2Letter(int num) {
        String letter;
        switch (num) {
            case 0:
                letter = "A";
                break;
            case 1:
                letter = "B";
                break;
            case 2:
                letter = "C";
                break;
            case 3:
                letter = "D";
                break;
            default:
                letter = "D";
        }
        return letter;
    }
}
