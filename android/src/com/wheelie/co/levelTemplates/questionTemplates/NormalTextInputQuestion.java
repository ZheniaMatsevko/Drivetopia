package com.wheelie.co.levelTemplates.questionTemplates;


public class NormalTextInputQuestion {
    private String question;
    private String answer;

    public NormalTextInputQuestion(String question, String answer){
        this.question=question;
        this.answer=answer;
    }
    public NormalTextInputQuestion(){
        question="Забезпечувати, що їх дії / бездіяльність не створять загрозу життю та здоров’ю громадян та інших водіїв";
        answer="Привіт";
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
