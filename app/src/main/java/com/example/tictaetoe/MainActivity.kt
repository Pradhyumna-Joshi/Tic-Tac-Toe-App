package com.example.tictaetoe

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private var buttons = arrayOf<ArrayList<Button>>(arrayListOf(), arrayListOf(), arrayListOf())
    private var player1Turn: Boolean = true
    private var roundCount = 0

    private var player1Points = 0
    private var player2Points = 0

    private lateinit var textViewPlayer1: TextView
    private lateinit var textViewPlayer2: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark)
        }
        else{
            setTheme(R.style.Theme_Light)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




//
        textViewPlayer1 = findViewById(R.id.textView_player1)
        textViewPlayer2 = findViewById(R.id.textView_player2)



        for (i in 0 until 3) {
            for (j in 0 until 3) {

                val btnId = "button_$i$j"
                val resId: Int = resources.getIdentifier(btnId, "id", packageName)

                buttons[i].add(findViewById(resId))
                buttons[i][j].setOnClickListener{ v->
                    onClickListener(v)


                }
            }
            }
            val buttonReset = findViewById<Button>(R.id.button_reset)
            buttonReset.setOnClickListener {
                resetGame()
            }



            findViewById<Switch>(R.id.theme).setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }


        }

    private fun onClickListener(view: View) {

        if((view as Button).text.toString() != ""){
            return
        }
        if(player1Turn){
            view.text="X"
        }else{
            view.text="O"
        }

        roundCount++

        if(checkForWin()){
            if(player1Turn){
                player1Wins()
            }
            else{
                player2Wins()
            }
        }
        else if(roundCount==9){
            draw()
        }
        else{
            player1Turn=!player1Turn
        }
    }


    private fun resetGame() {
            player1Points = 0
            player2Points = 0
            updatePointsText()
            resetBoard()
        }


        private fun player1Wins() {
            player1Points++
            Toast.makeText(this, "Player1 Wins!!!", Toast.LENGTH_SHORT).show()
            updatePointsText()
            resetBoard()
        }


        private fun player2Wins() {
            player2Points++
            Toast.makeText(this, "Player2 Wins!!!", Toast.LENGTH_SHORT).show()
            updatePointsText()
            resetBoard()
        }

        @SuppressLint("SetTextI18n")
        private fun updatePointsText() {
            textViewPlayer1.text = "Player 1 : $player1Points"
            textViewPlayer2.text = "Player 2 : $player2Points"
        }

        private fun draw() {

            Toast.makeText(this, "Draw!!!", Toast.LENGTH_SHORT).show()
            resetBoard()
        }


        private fun resetBoard() {

            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    buttons[i][j].text = ""
                }
            }

            roundCount = 0
            player1Turn = true
        }


        private fun checkForWin(): Boolean {

            val string = Array(3) { Array(3) { "" } }

            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    string[i][j] = buttons[i][j].text.toString()
                }
            }

            for (i in 0 until 3) {
                if (string[i][0] == string[i][1] && string[i][1] == string[i][2] && string[i][0] != "") {
                    return true
                }
            }

            for (i in 0 until 3) {
                if (string[0][i] == string[1][i] && string[1][i] == string[2][i] && string[0][i] != "") {
                    return true
                }
            }

            if (string[0][0] == string[1][1] && string[0][0] == string[2][2] && string[0][0] != "") {
                return true
            }

            if (string[0][2] == string[1][1] && string[0][2] == string[2][0] && string[0][2] != "") {
                return true
            }

            return false
        }

        override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
            super.onSaveInstanceState(outState, outPersistentState)

            outState.putInt("roundCount", roundCount)
            outState.putInt("player1Points", player1Points)
            outState.putInt("player2Points", player2Points)
            outState.putBoolean("player1Turn", player1Turn)

        }

        override fun onRestoreInstanceState(savedInstanceState: Bundle) {
            super.onRestoreInstanceState(savedInstanceState)

            roundCount = savedInstanceState.getInt("roundCount")
            player1Points = savedInstanceState.getInt("player1Points")
            player2Points = savedInstanceState.getInt("player2Points")
            player1Turn = savedInstanceState.getBoolean("player1Turn")
        }
    }
