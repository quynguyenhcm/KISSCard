package com.it617.myquizapplication;

/**
 * Created by student on 3/03/2018.
 */

public class Question {
    private int ID;
    private String QUESTION;
    private String OPTA;
    private String OPTB;
    private String OPTC;
    private String OPTD;
    private String ANSWER;
    public Question()
    {
        ID=0;
        QUESTION="";
        OPTA="";
        OPTB="";
        OPTC="";
        OPTD="";
        ANSWER="";
    }
    public Question(String pQuestion, String oPTA, String oPTB, String oPTC, String oPTD,
                    String pAnswer) {

        QUESTION = pQuestion;
        OPTA = oPTA;
        OPTB = oPTB;
        OPTC = oPTC;
        OPTD = oPTD;
        ANSWER = pAnswer;
    }
    public int getID()
    {
        return ID;
    }
    public String getQUESTION() {
        return QUESTION;
    }
    public String getQuestion() {
        return QUESTION;
    }
    public String getOPTA() {
        return OPTA;
    }
    public String getOPTB() {
        return OPTB;
    }
    public String getOPTC() {
        return OPTC;
    }
    public String getOPTD() {
        return OPTD;
    }
    public String getANSWER() {
        return ANSWER;
    }
    public String getAnswer() {
        return ANSWER;
    }
    public void setID(int id)
    {
        ID=id;
    }
    public void setId(int id)
    {
        ID=id;
    }
    public void setQUESTION(String pQuestion) {
        QUESTION = pQuestion;
    }
    public void setQuestion(String pQuestion) {
        QUESTION = pQuestion;
    }
    public void setOPTA(String oPTA) {
        OPTA = oPTA;
    }
    public void setOPTB(String oPTB) {
        OPTB = oPTB;
    }
    public void setOPTC(String oPTC) {
        OPTC = oPTC;
    }
    public void setOPTD(String oPTD) {
        OPTD = oPTD;
    }
    public void setANSWER(String pAnswer) {
        ANSWER = pAnswer;
    }
    public void setAnswer(String pAnswer) {
        ANSWER = pAnswer;
    }

}
