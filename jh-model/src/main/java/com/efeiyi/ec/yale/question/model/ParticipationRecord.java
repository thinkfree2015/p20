package com.efeiyi.ec.yale.question.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
@Entity
@Table(name = "yale_weixin_participation_record")
public class ParticipationRecord {
    private String id;
    private ParticipationRecord creationRecord;
    private String unionid;
    private Date createDatetime;
    private String recordType;//记录类型：1.发起答题 2.协助答题
    private String answer;//1.正确 2.错误
    private List<ParticipationRecord> participationRecordList;
    private Examination examination;

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
    @JoinColumn(name = "yale_weixin_participation_record_id")
    public ParticipationRecord getCreationRecord() {
        return creationRecord;
    }

    public void setCreationRecord(ParticipationRecord creationRecord) {
        this.creationRecord = creationRecord;
    }

    @Column(name = "unionid")
    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Column(name = "create_datetime")
    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Column(name = "record_type")
    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "creationRecord")
    public List<ParticipationRecord> getParticipationRecordList() {
        return participationRecordList;
    }

    public void setParticipationRecordList(List<ParticipationRecord> participationRecordList) {
        this.participationRecordList = participationRecordList;
    }

    @Column(name = "answer")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yale_weixin_examination_id")
    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }
}
