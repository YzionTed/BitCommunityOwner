package com.bit.fuxingwuye.bean;

/**
 * Created by Dell on 2017/8/19.
 * Created time:2017/8/19 15:29
 */

public class EvaluationBean {

    private String evaluationNo;
    private String repairNo;
    private String grade;
    private String content;
    private String anonymous;   //是否匿名（1： 否； 2： 是）
    private String userId;

    public EvaluationBean() {
    }

    public String getEvaluationNo() {
        return evaluationNo;
    }

    public void setEvaluationNo(String evaluationNo) {
        this.evaluationNo = evaluationNo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRepairNo() {
        return repairNo;
    }

    public void setRepairNo(String repairNo) {
        this.repairNo = repairNo;
    }
}
