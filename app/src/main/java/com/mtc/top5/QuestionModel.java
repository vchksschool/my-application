package com.mtc.top5;

public class QuestionModel {

    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;
    private int status;
    private int userAnswerPosition;
    private  int selectedAns;


    public int getOptionforq() {
        return optionforq;
    }

    public void setOptionforq(int optionforq) {
        this.optionforq = optionforq;
    }

    private int optionforq;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private int time;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionE() {
        return optionE;
    }

    public void setOptionE(String optionE) {
        this.optionE = optionE;
    }

    public int getUserAnswerPosition() {
        return userAnswerPosition;
    }

    public void setUserAnswerPosition(int userAnswerPosition) {
        this.userAnswerPosition = userAnswerPosition;
    }


    public QuestionModel(String question, String optionA, String optionB, String optionC, String optionD, String optionE, int userAnswerPosition, int time, int selectedAns, int status, int optionforq) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.optionE = optionE;
        this.userAnswerPosition = userAnswerPosition;
        this.time = time;
        this.selectedAns = selectedAns;
        this.status = status;
        this.optionforq = optionforq;

    }

    public int getSelectedAns() {
        return selectedAns;
    }

    public void setSelectedAns(int selectedAns) {
        System.out.println("Selected answer just changed to" + String.valueOf(selectedAns));

        this.selectedAns = selectedAns;
    }
}
