package com.example.tictactoe

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.tictactoe.databinding.ActivityMainBinding
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.close.setOnClickListener{
            binding.gif.visibility = View.GONE
        }
        binding.close2.setOnClickListener{
            binding.gif2.visibility = View.GONE
        }
        binding.close3.setOnClickListener{
            binding.gif3.visibility = View.GONE
        }


        binding.infol.setOnClickListener{
            binding.gif.visibility = View.VISIBLE
            binding.gif2.visibility = View.GONE
            binding.gif3.visibility = View.GONE
        }
        binding.infoo.setOnClickListener{
            binding.gif2.visibility = View.VISIBLE
            binding.gif3.visibility = View.GONE
            binding.gif.visibility = View.GONE
        }
        binding.infoof.setOnClickListener{
            binding.gif3.visibility = View.VISIBLE
            binding.gif2.visibility = View.GONE
            binding.gif.visibility = View.GONE
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.local.setOnClickListener{
            startActivity(Intent(this,MainActivity2::class.java))
        }

        binding.Online.setOnClickListener{
            startActivity(Intent(this,OnlineGamePage::class.java))
        }

        binding.Offline.setOnClickListener{
            startActivity(Intent(this,info::class.java))
        }

    }
}