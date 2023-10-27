package com.example.tictactoe

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.tictactoe.databinding.ActivityMainBinding
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)


//        val data: Uri? = intent.data
//        if (data != null) {
//            val param1 = data.getQueryParameter("code")
//
//            Toast.makeText(this, ""+param1, Toast.LENGTH_SHORT).show()
//
//            // Use the parameters in your app
//        }

//        FirebaseDynamicLinks.getInstance()
//            .getDynamicLink(intent)
//            .addOnSuccessListener { pendingDynamicLinkData ->
//                if (pendingDynamicLinkData != null) {
//                    // The app was opened via a Dynamic Link
//                    val dynamicLink = pendingDynamicLinkData.link
//
//                    if (dynamicLink != null) {
//                        Toast.makeText(this, ""+dynamicLink, Toast.LENGTH_SHORT).show()
//
//                            val intent = Intent(this,OnlineGamePage::class.java)
//                            intent.putExtra("bylink","true")
//                            startActivity(intent)
//                    }
//                }
//            }

        binding.local.setOnClickListener{
            startActivity(Intent(this,MainActivity2::class.java))
        }

        binding.Online.setOnClickListener{
            startActivity(Intent(this,OnlineGamePage::class.java))
        }

    }
}