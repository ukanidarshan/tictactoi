package com.example.tictactoe

import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.tictactoe.databinding.ActivityAiGamePlayBinding
import com.google.firebase.database.FirebaseDatabase
import kotlin.system.exitProcess

var playerTurn = true

class AiGamePlay : AppCompatActivity() {
    lateinit var binding: ActivityAiGamePlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_ai_game_play)

        binding.button10.setOnClickListener {
            reset()

        }
    }

    var player1Count = 0
    var player2Count = 0
    fun clickfun(view: View) {
        if (playerTurn) {
            val but = view as Button
            var cellID = 0
            //Toast.makeText(this,but.id.toString() , Toast.LENGTH_SHORT).show();
            when (but.id) {
                R.id.button -> cellID = 1
                R.id.button2 -> cellID = 2
                R.id.button3 -> cellID = 3
                R.id.button4 -> cellID = 4
                R.id.button5 -> cellID = 5
                R.id.button6 -> cellID = 6
                R.id.button7 -> cellID = 7
                R.id.button8 -> cellID = 8
                R.id.button9 -> cellID = 9

            }
            playerTurn = false;
            Handler().postDelayed(Runnable { playerTurn = true }, 600)
            playnow(but, cellID)

        }
    }

    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1
    fun playnow(buttonSelected: Button, currCell: Int) {
        if (activeUser == 1) {
            buttonSelected.text = "X"
            buttonSelected.setTextColor(Color.parseColor("#EC0C0C"))
            buttonSelected.setBackgroundColor(Color.parseColor("#A7C5EB"))
            player1.add(currCell)
            emptyCells.add(currCell)
            //Handler().postDelayed(Runnable { audio.pause() } , 500)
            buttonSelected.isEnabled = false
            val checkWinner = checkwinner()
            if (checkWinner == 1) {
                Handler().postDelayed(Runnable { reset() }, 2000)
            } else if (singleUser) {
                Handler().postDelayed(Runnable { robot() }, 500)
                //Toast.makeText(this , "Calling Robot" , Toast.LENGTH_SHORT).show()
            } else
                activeUser = 2

        } else {
            buttonSelected.text = "O"
            buttonSelected.setTextColor(Color.parseColor("#D22BB804"))
            buttonSelected.setBackgroundColor(Color.parseColor("#F5DF99"))
            //Handler().postDelayed(Runnable { audio.pause() } , 500)
            activeUser = 1
            player2.add(currCell)
            emptyCells.add(currCell)

            buttonSelected.isEnabled = false
            val checkWinner = checkwinner()
            if (checkWinner == 1)
                Handler().postDelayed(Runnable { reset() }, 4000)
        }

    }

    fun checkwinner(): Int {
        if ((player1.contains(1) && player1.contains(2) && player1.contains(3)) || (player1.contains(
                1
            ) && player1.contains(4) && player1.contains(7)) ||
            (player1.contains(3) && player1.contains(6) && player1.contains(9)) || (player1.contains(
                7
            ) && player1.contains(8) && player1.contains(9)) ||
            (player1.contains(4) && player1.contains(5) && player1.contains(6)) || (player1.contains(
                1
            ) && player1.contains(5) && player1.contains(9)) ||
            player1.contains(3) && player1.contains(5) && player1.contains(7) || (player1.contains(2) && player1.contains(
                5
            ) && player1.contains(8))
        ) {
            player1Count += 1
            buttonDisable()
            disableReset()
            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_layout)

            val body = dialog.findViewById(R.id.settitle) as TextView
            body.text = "You won!"
            val dialogMessage = dialog.findViewById(R.id.dialogMessage) as TextView
            dialogMessage.text =
                "Congratulations! Victory is yours. Well played!" + "\n\n" + "Do you want to play again"

            val yesBtn = dialog.findViewById(R.id.exitButton) as Button
            yesBtn.setOnClickListener {
                Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show()
                FirebaseDatabase.getInstance().reference.child(code)
                    .child("isExit").push().setValue("true")
                exitProcess(1)
            }

            val noBtn = dialog.findViewById(R.id.playAgainButton) as Button
            noBtn.setOnClickListener {
                dialog.dismiss()
                reset()
            }
            Handler().postDelayed(Runnable { dialog.show() }, 1)
            return 1


        } else if ((player2.contains(1) && player2.contains(2) && player2.contains(3)) || (player2.contains(
                1
            ) && player2.contains(4) && player2.contains(7)) ||
            (player2.contains(3) && player2.contains(6) && player2.contains(9)) || (player2.contains(
                7
            ) && player2.contains(8) && player2.contains(9)) ||
            (player2.contains(4) && player2.contains(5) && player2.contains(6)) || (player2.contains(
                1
            ) && player2.contains(5) && player2.contains(9)) ||
            player2.contains(3) && player2.contains(5) && player2.contains(7) || (player2.contains(2) && player2.contains(
                5
            ) && player2.contains(8))
        ) {
            player2Count += 1
            buttonDisable()
            disableReset()
            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_layout)

            val body = dialog.findViewById(R.id.settitle) as TextView
            body.text = "You lose!"
            val dialogMessage = dialog.findViewById(R.id.dialogMessage) as TextView
            dialogMessage.text =
                "better luck for next time" + "\n\n" + "Do you want to play again"

            val yesBtn = dialog.findViewById(R.id.exitButton) as Button
            yesBtn.setOnClickListener {
                Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show()
                FirebaseDatabase.getInstance().reference.child(code)
                    .child("isExit").push().setValue("true")
                exitProcess(1)
            }

            val noBtn = dialog.findViewById(R.id.playAgainButton) as Button
            noBtn.setOnClickListener {
                dialog.dismiss()
                reset()
            }
            Handler().postDelayed(Runnable { dialog.show() }, 1)
            return 1
        } else if (emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) && emptyCells.contains(
                4
            ) && emptyCells.contains(5) && emptyCells.contains(6) && emptyCells.contains(7) &&
            emptyCells.contains(8) && emptyCells.contains(9)
        ) {

            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_layout)

            val body = dialog.findViewById(R.id.settitle) as TextView
            body.text = "Stalemate: It's a Tie!"
            val dialogMessage = dialog.findViewById(R.id.dialogMessage) as TextView
            dialogMessage.text =
                "Try again for a decisive victory" + "\n\n" + "Do you want to play again?"

            val yesBtn = dialog.findViewById(R.id.exitButton) as Button
            yesBtn.setOnClickListener {
                Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show()
                FirebaseDatabase.getInstance().reference.child(code)
                    .child("isExit").push().setValue("true")
                exitProcess(1)
            }

            val noBtn = dialog.findViewById(R.id.playAgainButton) as Button
            noBtn.setOnClickListener {
                dialog.dismiss()
                reset()
            }
            dialog.show()
            return 1

        }
        return 0
    }

    fun reset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1;
        for (i in 1..9) {
            var buttonselected: Button?
            buttonselected = when (i) {
                1 -> binding.button
                2 -> binding.button2
                3 -> binding.button3
                4 -> binding.button4
                5 -> binding.button5
                6 -> binding.button6
                7 -> binding.button7
                8 -> binding.button8
                9 -> binding.button9
                else -> {
                    binding.button
                }
            }
            buttonselected.isEnabled = true
            buttonselected.setBackgroundColor(Color.parseColor("#F1F6F9"))
            buttonselected.text = ""
            binding.textView.text = "Player1 : $player1Count"
            binding.textView2.text = "Player2 : $player2Count"
        }
    }

    fun robot() {
        val rnd = (1..9).random()
        if (emptyCells.contains(rnd))
            robot()
        else {
            val buttonselected: Button?
            buttonselected = when (rnd) {
                1 -> binding.button
                2 -> binding.button2
                3 -> binding.button3
                4 -> binding.button4
                5 -> binding.button5
                6 -> binding.button6
                7 -> binding.button7
                8 -> binding.button8
                9 -> binding.button9
                else -> {
                    binding.button
                }
            }
            emptyCells.add(rnd);
            buttonselected.text = "O"
            buttonselected.setTextColor(Color.parseColor("#D22BB804"))
            buttonselected.setBackgroundColor(Color.parseColor("#F5DF99"))
            player2.add(rnd)
            buttonselected.isEnabled = false
            var checkWinner = checkwinner()
            if (checkWinner == 1)
                Handler().postDelayed(Runnable { reset() }, 2000)

        }
    }

    fun buttonDisable() {
        for (i in 1..9) {
            val buttonSelected = when (i) {
                1 -> binding.button
                2 -> binding.button2
                3 -> binding.button3
                4 -> binding.button4
                5 -> binding.button5
                6 -> binding.button6
                7 -> binding.button7
                8 -> binding.button8
                9 -> binding.button9
                else -> {
                    binding.button
                }

            }
            if (buttonSelected.isEnabled == true)
                buttonSelected.isEnabled = false
        }
    }

    fun disableReset() {
        binding.button10.isEnabled = false
        Handler().postDelayed(Runnable { binding.button10.isEnabled = true }, 2200)
    }
}