package com.efeiyi.ec.yale.question.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
@Entity
@Table(name = "yale_weixin_examination")
public class Examination {
    private String id;
    private String serial;
    private String name;
    private String url;//试卷链接
    private Integer relayLimit;//
    private Date expireDate;
    private String status;// 0假删  1正常
    private Date createDatetime;
    private List<ExaminationQuestion> examinationQuestionList;

    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "serial")
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "examination_url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "relay_limit")
    public Integer getRelayLimit() {
        return relayLimit;
    }

    public void setRelayLimit(Integer relayLimit) {
        this.relayLimit = relayLimit;
    }

    @Column(name = "expire_date")
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "create_datetime")
    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "examination")
    public List<ExaminationQuestion> getExaminationQuestionList() {
        return examinationQuestionList;
    }

    public void setExaminationQuestionList(List<ExaminationQuestion> examinationQuestionList) {
        this.examinationQuestionList = examinationQuestionList;
    }
}
