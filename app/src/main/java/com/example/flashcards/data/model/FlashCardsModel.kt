package com.example.flashcards.data.model

import androidx.lifecycle.ViewModel
import com.example.flashcards.R

class FlashCardsModel : ViewModel() {
    var currentIndex = 0

    var score = 0

    var gameStarted = false

    var operationChosen = ""

    var firstNum = ""

    var secondNum = ""

    var questions = mutableListOf<Question>()

    var counter = 0

    fun nextQuestion(){
        counter+=1
    }

    fun updateScore(){
        score +=1;
    }

    fun startGame() {
        gameStarted = true;
    }

    fun stopGame(){
        currentIndex = 0;
        score = 0;
        gameStarted = false;
        questions.clear()
        counter = 0;
    }

    fun chooseOperation(operation:String){
        if(operation.equals("add")){
            operationChosen = "+";
        }else{
            operationChosen = "-";
        }
    }

    fun setNumbers(firstNum: String, secondNum: String){
        this.firstNum = firstNum
        this.secondNum = secondNum
    }

    fun appendQuestion(question:String, answerGiven:String){
        var q = Question(question, answerGiven)
        questions.add(q)
    }
}