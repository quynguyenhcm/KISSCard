package net.qrolling.kisscard.dto;

import java.io.Serializable;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 18/05/18.
 */

public class KissCard implements Serializable {
    private int ID;
    private String term;
    private String definition;

    public KissCard() {
        ID = 0;
        term = "";
        definition = "";
    }

    public KissCard(String pQuestion, String pAnswer) {

        term = pQuestion;

        definition = pAnswer;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
