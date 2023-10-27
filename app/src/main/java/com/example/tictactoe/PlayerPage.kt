package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.tictactoe.databinding.ActivityPlayerPageBinding
var singleUser = true
class PlayerPage : AppCompatActivity() {
    lateinit var binding:ActivityPlayerPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_player_page)

        binding.button11.setOnClickListener{
            startActivity(Intent(this,AiGamePlay::class.java))
        }

        binding.button12.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}