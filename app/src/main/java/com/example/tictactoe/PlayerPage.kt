package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.tictactoe.databinding.ActivityPlayerPageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

var singleUser = true

class PlayerPage : AppCompatActivity() {
    lateinit var binding: ActivityPlayerPageBinding

    var data = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player_page)


        Handler().postDelayed({
            FirebaseDatabase.getInstance().reference.child(code).child("link")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {

                        Toast.makeText(this@PlayerPage, ""+isValueAvailable(snapshot, "true"), Toast.LENGTH_SHORT).show()
                        if (isValueAvailable(snapshot, "true") ) {



                            val pendingDynamicLinkData = FirebaseDynamicLinks.getInstance().getDynamicLink(intent)
                            if (pendingDynamicLinkData != null) {
                                pendingDynamicLinkData.addOnSuccessListener(this@PlayerPage) { pendingDynamicLinkData ->
                                    val dynamicLink = pendingDynamicLinkData.link
                                    if (dynamicLink != null) {
                                        code = dynamicLink.getQueryParameter("code").toString()

                                        Toast.makeText(this@PlayerPage, "" + code, Toast.LENGTH_SHORT).show()

                                        isCodeMaker = false;

                                        FirebaseDatabase.getInstance().reference.child(code).child("codes")
                                            .addValueEventListener(object : ValueEventListener {
                                                override fun onCancelled(error: DatabaseError) {
                                                    TODO("Not yet implemented")
                                                }

                                                override fun onDataChange(snapshot: DataSnapshot) {

                                                    data = isValueAvailable(snapshot, code)
                                                    Handler().postDelayed({
                                                        if (data == true) {
                                                            FirebaseDatabase.getInstance().reference.child(code)
                                                                .child("go").push()
                                                                .setValue("true")
                                                            codeFound = true
                                                            accepted()
//                                        binding.Create.visibility = View.VISIBLE
//                                        binding.textView4.visibility = View.VISIBLE
//                                        binding.getCode.visibility = View.VISIBLE
//                                        binding.progressBar.visibility = View.GONE
                                                        } else {
//                                        binding.Create.visibility = View.VISIBLE
//                                        binding.textView4.visibility = View.VISIBLE
//                                        binding.getCode.visibility = View.VISIBLE
//                                        binding.progressBar.visibility = View.GONE
                                                            errorMsg("Invalid Code")
                                                        }
                                                    }, 2000)


                                                }


                                            })
//                    val intent = Intent(this,OnlineGamePage::class.java)
//                    intent.putExtra("code",code)
//                    startActivity(intent)

                                        if (!code.isNullOrBlank()) {
                                            // You have successfully retrieved the custom code
                                            // You can now use the "code" variable in your app
                                            // For example, you can display it or perform specific actions
                                            // based on the value of the code
                                        }
                                    }
                                }
                            }




//                                                        binding.progressBar.visibility = View.VISIBLE
//                                                        binding.Create.visibility = View.GONE
//                                                        binding.idIVQrcode.visibility = View.GONE
//                                                        binding.textView4.visibility = View.GONE
//                                                        binding.getCode.visibility = View.GONE


//                                binding.Create.visibility = View.VISIBLE
//                                binding.textView4.visibility =
//                                    View.VISIBLE
//                                binding.getCode.visibility =
//                                    View.VISIBLE
//                                binding.progressBar.visibility =
//                                    View.GONE
                            errorMsg("Please don't go back")
                        }
                    }
                })
        }, 1)


        binding.play.setOnClickListener {



        }
        binding.button11.setOnClickListener {
            startActivity(Intent(this, AiGamePlay::class.java))
        }

        binding.button12.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun isValueAvailable(snapshot: DataSnapshot, code: String): Boolean {
        var data = snapshot.children
        var found = false
        data.forEach {
            var value = it.getValue().toString()
            if (value == code) {
                keyValue = it.key.toString()
                found = true
                return@forEach
            }
        }
        return found
    }

    fun accepted() {

        if (data) {
            startActivity(Intent(this, Game_Page::class.java))
//            binding.Create.visibility = View.VISIBLE
//            binding.textView4.visibility = View.VISIBLE
//            binding.getCode.visibility = View.VISIBLE
//            binding.progressBar.visibility = View.GONE
            errorMsg("Please don't go back")
        }
    }

    fun errorMsg(value: String) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }
}