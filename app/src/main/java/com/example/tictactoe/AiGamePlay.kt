package com.example.tictactoe

import android.app.Dialog
import android.graphics.Color
import android.hardware.camera2.params.BlackLevelPattern
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
    var line = 0
    var player1Count = 0
    var player2Count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_ai_game_play)

        if (!singleUser) {
            binding.turn.visibility = View.VISIBLE
            binding.turn.text = "Turn : ${intent.getStringExtra("player1")}"
            binding.textView.text = "${intent.getStringExtra("player1")} : $player1Count"
            binding.textView2.text = "${intent.getStringExtra("player2")} : $player2Count"
        } else {
            binding.turn.visibility = View.GONE
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.button10.setOnClickListener {
            reset()

        }
    }


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
            binding.turn.text = "Turn : ${intent.getStringExtra("player2")}"
            player1.add(currCell)
            emptyCells.add(currCell)
            //Handler().postDelayed(Runnable { audio.pause() } , 500)
            buttonSelected.isEnabled = false
            val checkWinner = checkwinner()
            if (checkWinner == 1) {
                Handler().postDelayed(Runnable { }, 2000)
            } else if (singleUser) {
                Handler().postDelayed(Runnable { robot() }, 500)
                //Toast.makeText(this , "Calling Robot" , Toast.LENGTH_SHORT).show()
            } else
                activeUser = 2

        } else {
            buttonSelected.text = "O"
            buttonSelected.setTextColor(Color.parseColor("#D22BB804"))
            buttonSelected.setBackgroundColor(Color.parseColor("#F5DF99"))
            binding.turn.text = "Turn : ${intent.getStringExtra("player1")}"
            //Handler().postDelayed(Runnable { audio.pause() } , 500)
            activeUser = 1
            player2.add(currCell)
            emptyCells.add(currCell)

            buttonSelected.isEnabled = false
            val checkWinner = checkwinner()
        }

    }

    fun checkwinner(): Int {
        if ((player1.contains(1) && player1.contains(2) && player1.contains(3))
            || (player2.contains(1) && player2.contains(2) && player2.contains(3))
        )
            line = 1
        else if ((player1.contains(4) && player1.contains(5) && player1.contains(6))
            || (player2.contains(4) && player2.contains(5) && player2.contains(6)))
            line = 2
        else if ((player1.contains(7) && player1.contains(8) && player1.contains(9))
            || (player2.contains(7) && player2.contains(8) && player2.contains(9)))
            line = 3
        else if ((player1.contains(1) && player1.contains(4) && player1.contains(7))
            || (player2.contains(1) && player2.contains(4) && player2.contains(7)))
            line = 4
        else if ((player1.contains(2) && player1.contains(5) && player1.contains(8))
            || (player2.contains(2) && player2.contains(5) && player2.contains(8))
        )
            line = 5
        else if ((player1.contains(3) && player1.contains(6) && player1.contains(9))
            || (player2.contains(3) && player2.contains(6) && player2.contains(9)))
            line = 6
        else if ((player1.contains(1) && player1.contains(5) && player1.contains(9))
            || (player2.contains(1) && player2.contains(5) && player2.contains(9)))
            line = 7
        else if (player1.contains(3) && player1.contains(5) && player1.contains(7)
            || player2.contains(3) && player2.contains(5) && player2.contains(7))
            line = 8


        if ((player1.contains(1) && player1.contains(2) && player1.contains(3))
            || (player1.contains(1) && player1.contains(4) && player1.contains(7))
            || (player1.contains(3) && player1.contains(6) && player1.contains(9))
            || (player1.contains(7) && player1.contains(8) && player1.contains(9))
            || (player1.contains(4) && player1.contains(5) && player1.contains(6))
            || (player1.contains(1) && player1.contains(5) && player1.contains(9))
            || player1.contains(3) && player1.contains(5) && player1.contains(7)
            || (player1.contains(2) && player1.contains(5) && player1.contains(8)))
        {
            drawWinningLine(line)
            player1Count += 1
            buttonDisable()
            disableReset()
            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_layout)

            val body = dialog.findViewById(R.id.settitle) as TextView
            if (singleUser) {
                body.text = "You won!"
            } else {
                body.text = intent.getStringExtra("player1") + " Won!"
            }
            val dialogMessage = dialog.findViewById(R.id.dialogMessage) as TextView
            dialogMessage.text =
                "Congratulations! Victory is yours. Well played!" + "\n\n" + "Do you want to play again"

            val yesBtn = dialog.findViewById(R.id.exitButton) as Button
            yesBtn.setOnClickListener {
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


        } else if ((player2.contains(1) && player2.contains(2) && player2.contains(3))
            || (player2.contains(1) && player2.contains(4) && player2.contains(7))
            || (player2.contains(3) && player2.contains(6) && player2.contains(9))
            || (player2.contains(7) && player2.contains(8) && player2.contains(9))
            || (player2.contains(4) && player2.contains(5) && player2.contains(6))
            || (player2.contains(1) && player2.contains(5) && player2.contains(9))
            || player2.contains(3) && player2.contains(5) && player2.contains(7)
            || (player2.contains(2) && player2.contains(5) && player2.contains(8)))
        {
            drawWinningLine(line)
            player2Count += 1
            buttonDisable()
            disableReset()
            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_layout)

            val body = dialog.findViewById(R.id.settitle) as TextView
            if (singleUser) {
                body.text = "You lose!"
            } else {
                body.text = intent.getStringExtra("player2") + " Won!"
            }
            val dialogMessage = dialog.findViewById(R.id.dialogMessage) as TextView
            dialogMessage.text =
                "better luck for next time" + "\n\n" + "Do you want to play again"

            val yesBtn = dialog.findViewById(R.id.exitButton) as Button
            yesBtn.setOnClickListener {
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
        } else if (emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3)
            && emptyCells.contains(4) && emptyCells.contains(5) && emptyCells.contains(6)
            && emptyCells.contains(7) && emptyCells.contains(8) && emptyCells.contains(9))
        {

            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_layout)

            val body = dialog.findViewById(R.id.settitle) as TextView
            body.text = "It's a Tie!"
            val dialogMessage = dialog.findViewById(R.id.dialogMessage) as TextView
            dialogMessage.text =
                "Try again for a decisive victory" + "\n\n" + "Do you want to play again?"

            val yesBtn = dialog.findViewById(R.id.exitButton) as Button
            yesBtn.setOnClickListener {
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
            if (!singleUser) {
                binding.textView.text = "${intent.getStringExtra("player1")} : $player1Count"
                binding.textView2.text = "${intent.getStringExtra("player2")} : $player2Count"
            }
            else{
                binding.textView.text = "player1 : $player1Count"
                binding.textView2.text = "player2 : $player2Count"
            }
            binding.turn.text = intent.getStringExtra("player1")


        }
        when (line) {
            1 -> binding.line1.visibility = View.GONE
            2 -> binding.line2.visibility = View.GONE
            3 -> binding.line3.visibility = View.GONE
            4 ->  binding.line4.visibility = View.GONE
            5 -> binding.line5.visibility = View.GONE
            6 -> binding.line6.visibility = View.GONE
            7 -> binding.line7.visibility = View.GONE
            8 -> binding.line8.visibility = View.GONE
        }
    }

    fun shouldMakeWinningMove(playerMoves: List<Int>, winningCombination: List<Int>, suggest: Int): Boolean {
        return playerMoves.containsAll(winningCombination) && !emptyCells.contains(suggest)
    }

    fun shouldBlockOpponent(opponentMoves: List<Int>, blockingCombination: List<Int>, suggest: Int): Boolean {
        return opponentMoves.containsAll(blockingCombination) && !emptyCells.contains(suggest)
    }

    fun robot() {
        var rnd = (1..9).random()
        if (emptyCells.contains(rnd))
            robot()
        else {

            if (shouldMakeWinningMove(player2, listOf(2, 3), 1)
                || shouldMakeWinningMove(player2, listOf(4, 5), 1)
                || shouldMakeWinningMove(player2, listOf(5, 9), 1))
                rnd = 1
            else if (shouldMakeWinningMove(player2, listOf(1, 3), 2)
                || shouldMakeWinningMove(player2, listOf(5, 8), 2))
                rnd = 2
            else if (shouldMakeWinningMove(player2, listOf(1, 2), 3)
                || shouldMakeWinningMove(player2, listOf(6, 9), 3)
                || shouldMakeWinningMove(player2, listOf(5, 7), 3))
                rnd = 3
            else if (shouldMakeWinningMove(player2, listOf(5, 6), 4)
                || shouldMakeWinningMove(player2, listOf(1, 7), 4))
                rnd = 4
            else if (shouldMakeWinningMove(player2, listOf(4, 6), 5)
                || shouldMakeWinningMove(player2, listOf(2, 8), 5)
                || shouldMakeWinningMove(player2, listOf(1, 9), 5)
                || shouldMakeWinningMove(player2, listOf(3, 7), 5))
                rnd = 5
            else if (shouldMakeWinningMove(player2, listOf(4, 5), 6)
                || shouldMakeWinningMove(player2, listOf(3, 9), 6))
                rnd = 6
            else if (shouldMakeWinningMove(player2, listOf(8, 9), 7)
                || shouldMakeWinningMove(player2, listOf(1, 4), 7)
                || shouldMakeWinningMove(player2, listOf(5, 3), 7))
                rnd = 7
            else if (shouldMakeWinningMove(player2, listOf(7, 9), 8)
                || shouldMakeWinningMove(player2, listOf(2, 5), 8))
                rnd = 8
            else if (shouldMakeWinningMove(player2, listOf(7, 8), 9)
                || shouldMakeWinningMove(player2, listOf(3, 6), 9)
                || shouldMakeWinningMove(player2, listOf(1, 5), 9))
                rnd = 9
            else if (shouldBlockOpponent(player1, listOf(2, 3), 1)
                || shouldBlockOpponent(player1, listOf(4, 7), 1)
                || shouldBlockOpponent(player1, listOf(5, 9), 1))
                rnd = 1
            else if (shouldBlockOpponent(player1, listOf(1, 3), 2)
                || shouldBlockOpponent(player1, listOf(5, 8), 2))
                rnd = 2
            else if (shouldBlockOpponent(player1, listOf(1, 2), 3)
                || shouldBlockOpponent(player1, listOf(6, 9), 3)
                || shouldBlockOpponent(player1, listOf(5, 7), 3))
                rnd = 3
            else if (shouldBlockOpponent(player1, listOf(5, 6), 4)
                || shouldBlockOpponent(player1, listOf(1, 7), 4))
                rnd = 4
            else if (shouldBlockOpponent(player1, listOf(4, 6), 5)
                || shouldBlockOpponent(player1, listOf(2, 8), 5)
                || shouldBlockOpponent(player1, listOf(1, 9), 5)
                || shouldBlockOpponent(player1, listOf(3, 7), 5))
                rnd = 5
            else if (shouldBlockOpponent(player1, listOf(4, 5), 6)
                || shouldBlockOpponent(player1, listOf(3, 9), 6))
                rnd = 6
            else if (shouldBlockOpponent(player1, listOf(8, 9), 7)
                || shouldBlockOpponent(player1, listOf(1, 4), 7)
                || shouldBlockOpponent(player1, listOf(5, 3), 7))
                rnd = 7
            else if (shouldBlockOpponent(player1, listOf(7, 9), 8)
                || shouldBlockOpponent(player1, listOf(2, 5), 8))
                rnd = 8
            else if (shouldBlockOpponent(player1, listOf(7, 8), 9)
                || shouldBlockOpponent(player1, listOf(3, 6), 9)
                || shouldBlockOpponent(player1, listOf(1, 5), 9))
                rnd = 9

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

    private fun drawWinningLine(linenum: Int) {
        // Draw a line or change colors in your UI based on the cells involved in the winningCombination
        // For instance, if using buttons, change background colors of winning cells to indicate the win

        when (linenum) {
            1 -> binding.line1.visibility = View.VISIBLE
            2 -> binding.line2.visibility = View.VISIBLE
            3 ->binding.line3.visibility = View.VISIBLE
            4 ->binding.line4.visibility = View.VISIBLE
            5 ->binding.line5.visibility = View.VISIBLE
            6 ->binding.line6.visibility = View.VISIBLE
            7 ->binding.line7.visibility = View.VISIBLE
            8 -> binding.line8.visibility = View.VISIBLE
        }
    }

}