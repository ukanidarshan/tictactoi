package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.tictactoe.databinding.ActivityInfoBinding

class info : AppCompatActivity() {
    lateinit var binding : ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_info)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.play.setOnClickListener{
            if (binding.player1.text.isEmpty())
            {
                binding.player1.setError("enter Player name")
                return@setOnClickListener
            }
            else if(binding.player2.text.isEmpty())
            {
                binding.player2.setError("enter Player name")
                return@setOnClickListener
            }
            else{
                singleUser = false
                var i = Intent(this,AiGamePlay::class.java)
                i.putExtra("player1",binding.player1.text.toString())
                i.putExtra("player2",binding.player2.text.toString())
                startActivity(i)
            }
        }
    }
}