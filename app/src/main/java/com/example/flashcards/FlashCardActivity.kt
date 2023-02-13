package com.example.flashcards

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcards.data.model.FlashCardsModel
import com.example.flashcards.databinding.ActivityFlashCardBinding
import com.google.android.material.snackbar.Snackbar

class FlashCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlashCardBinding
    private val flashCardsModel: FlashCardsModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card)
        binding = ActivityFlashCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(flashCardsModel.gameStarted){
            binding.generateButton.setText("Restart Game")
            binding.submitAnswerButton.setEnabled(true)
            binding.scoreText.setText("Score: " + flashCardsModel.score)
            disableButtons()
        }else{
            binding.submitAnswerButton.setEnabled(false)
        }

        binding.generateButton.setOnClickListener {
            binding.submitAnswerButton.setEnabled(true)
            Log.i("Some value", flashCardsModel.gameStarted.toString())
            if(!flashCardsModel.gameStarted){
                var id = binding.RGroup.checkedRadioButtonId
                when (id) {
                    binding.subButton.id -> {
                        startGame("subtract")
                    }
                    binding.addButton.id -> {
                        startGame("add")
                    }
                    -1 -> {
                        Snackbar.make(binding.root, "Please choose a type", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }else{
                flashCardsModel.stopGame()
                binding.firstNum.setText("")
                binding.secondNum.setText("")
                binding.opNum.setText("")
                this.recreate()
            }
        }

        binding.submitAnswerButton.setOnClickListener {
            if(binding.editTextNumber.text.isNotBlank() && isNumeric(binding.editTextNumber.text.toString())) {
                if (checkAnswer()) {
                    nextQuestion(true)
                } else {
                    nextQuestion(false)
                }
                binding.editTextNumber.setText("")
            }else{
                Snackbar.make(binding.root, "Please enter proper value", Snackbar.LENGTH_LONG).show()
            }
        }

            Log.i("Flash card model", binding.RGroup.checkedRadioButtonId.toString())

        }


    fun isNumeric(strNum: String?): Boolean {
        if (strNum == null) {
            return false
        }
        try {
            val d = strNum.toDouble()
        } catch (nfe: NumberFormatException) {
            return false
        }
        return true
    }

    fun startGame(operation: String){
        binding.submitAnswerButton.setEnabled(true)
        flashCardsModel.startGame()
        disableButtons()
        binding.scoreText.setText("Score: " + flashCardsModel.score)
        flashCardsModel.chooseOperation(operation)
        binding.generateButton.setText("Restart Game")
        var firstNumber = Math.floor((Math.random() * (99)) + 1).toInt()
        var secondNumber = Math.floor((Math.random() * (19)) + 1).toInt()
        flashCardsModel.setNumbers(firstNumber.toString(), secondNumber.toString())
        binding.firstNum.setText(flashCardsModel.firstNum)
        binding.secondNum.setText(flashCardsModel.secondNum)
        binding.opNum.setText(flashCardsModel.operationChosen)
    }

    fun disableButtons(){
        binding.addButton.setEnabled(false)
        binding.subButton.setEnabled(false)
    }

    fun nextQuestion(correct:Boolean){
        if(correct){
            flashCardsModel.updateScore()
        }
        flashCardsModel.nextQuestion()
        binding.scoreText.setText("Score: " + flashCardsModel.score)
        checkWin()
        var firstNumber = Math.floor(Math.random() * (100)).toInt()
        var secondNumber = Math.floor(Math.random() * (20)).toInt()
        flashCardsModel.setNumbers(firstNumber.toString(), secondNumber.toString())
        binding.firstNum.setText(flashCardsModel.firstNum)
        binding.secondNum.setText(flashCardsModel.secondNum)
        binding.opNum.setText(flashCardsModel.operationChosen)
    }

    fun checkWin(){
        if(flashCardsModel.counter==10){
            Snackbar.make(binding.root, "FINAL SCORE: "+flashCardsModel.score, Snackbar.LENGTH_INDEFINITE).show()
            binding.submitAnswerButton.setEnabled(false)
        }
    }

    fun checkAnswer(): Boolean{
        Log.i("Check Answer", "Checking answer here")
        flashCardsModel.appendQuestion(flashCardsModel.firstNum + flashCardsModel.operationChosen + flashCardsModel.secondNum, binding.editTextNumber.text.toString())
        if(flashCardsModel.operationChosen.equals("+")){
            val answer = Integer.parseInt(flashCardsModel.firstNum) + Integer.parseInt(flashCardsModel.secondNum)
            if(Integer.parseInt(binding.editTextNumber.text.toString()) == answer){
                Snackbar.make(binding.root, "Correct Answer", Snackbar.LENGTH_SHORT).show()
                Log.i("Check Answer", "Correct Answer")
                return true
            }else{
                Snackbar.make(binding.root, "Incorrect Answer", Snackbar.LENGTH_LONG).show()
                Log.i("Check Answer", "It seems wrong")
                return false
            }
        }else{
            val answer = Integer.parseInt(flashCardsModel.firstNum) - Integer.parseInt(flashCardsModel.secondNum)
            if(Integer.parseInt(binding.editTextNumber.text.toString()) == answer){
                Snackbar.make(binding.root, "Correct Answer", Snackbar.LENGTH_SHORT).show()
                return true
            }else{
                Snackbar.make(binding.root, "Incorrect Answer", Snackbar.LENGTH_LONG).show()
                return false
            }
        }
    }
}