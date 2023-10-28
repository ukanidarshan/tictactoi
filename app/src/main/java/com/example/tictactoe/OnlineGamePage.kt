package com.example.tictactoe

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.tictactoe.databinding.ActivityGamePageBinding
import com.example.tictactoe.databinding.ActivityOnlineGamePageBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink

var link = false
class OnlineGamePage : AppCompatActivity() {

    var count: Int = 0
    lateinit var binding: ActivityOnlineGamePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_game_page)

        if (intent.getStringExtra("bylink").equals("true")) {
//            Handler.
        }

        ////             Initialize Firebase Dynamic Links
        //            val dynamicLinks = FirebaseDynamicLinks.getInstance()
        //            code = generateRandomCode(8)
        //            // Create dynamic link parameters
        //            val dynamicLinkUri =
        //                "https://tictactoigame.page.link/online" // Replace with your dynamic link domain
        //
        //            val linkBuilder = dynamicLinks.createDynamicLink()
        //                .setLink(Uri.parse("https://tictactoigame.page.link/?code=$code"))
        //                .setDomainUriPrefix("https://tictactoigame.page.link/")
        //
        //
        //            linkBuilder
        //                .setAndroidParameters(
        //                    DynamicLink.AndroidParameters.Builder("com.example.tictactoe")
        //                    .setMinimumVersion(1)
        //                    .build())
        //            val dynamicLink = linkBuilder.buildDynamicLink()
        //
        //// Generate a short dynamic link
        //            dynamicLinks.createDynamicLink()
        //                .setLongLink(dynamicLink.uri)
        //                .buildShortDynamicLink(ShortDynamicLink.Suffix.SHORT)
        //                .addOnSuccessListener(object : OnSuccessListener<ShortDynamicLink> {
        //                    override fun onSuccess(shortDynamicLink: ShortDynamicLink) {
        //                        val shortLink = shortDynamicLink.shortLink
        //                        val previewLink = shortDynamicLink.previewLink
        //                        // Handle the generated short link as needed (e.g., share it)
        //
        //                        val intent = Intent(Intent.ACTION_SEND)
        //                        intent.type = "text/plain"
        //                        intent.putExtra(Intent.EXTRA_TEXT, dynamicLinkUri)
        //                        startActivity(Intent.createChooser(intent, "Share Dynamic Link"))
        //                    }
        //                })
        //                .addOnFailureListener(object : OnFailureListener {
        //                    override fun onFailure(e: Exception) {
        //                        // Handle any errors that occur during the link generation process
        //                    }
        //                })

        binding.generateLink.setOnClickListener {
            code = generateRandomCode(8)



//            val dynamicLinkUri = Uri.Builder()
//                .scheme("https")
//                .authority("tictactoigame.page.link") // Your Dynamic Links domain
//                .appendPath("online") // Path for deep link
//                .appendQueryParameter("code", code)
//
//            // Share the Dynamic Link
//            val dynamicLinkUrl = dynamicLinkUri.build().toString()
//
//
////            FirebaseDatabase.getInstance().reference.child("link").child()
//            val intent = Intent(Intent.ACTION_SEND)
//            intent.type = "text/plain"
//            intent.putExtra(Intent.EXTRA_TEXT, dynamicLinkUri.toString()+code)
//            startActivity(Intent.createChooser(intent, "Share Dynamic Link"))

            link = true

            val dynamicLinkUrl = "https://tictactoigame.page.link/online"
             // Replace with the unique code you want
            FirebaseDatabase.getInstance().reference.child(code).child("codes").push()
                .setValue(code)

            val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("$dynamicLinkUrl?code=$code"))
                .setDomainUriPrefix("https://tictactoigame.page.link") // Replace with your dynamic link domain
                .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
                .setSocialMetaTagParameters(
                    DynamicLink.SocialMetaTagParameters.Builder()
                        .setTitle("My App")
                        .setDescription("Check out my app with code: $code")
                        .build()
                )
                .buildDynamicLink()

            val dynamicLinkUri = dynamicLink.uri
            isCodeMaker = true

            FirebaseDatabase.getInstance().reference.child(code)
                .child("link").push()
                .setValue("true")

// Create an intent to share the link
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, dynamicLinkUri.toString())
            }

// Launch the share dialog
            startActivity(Intent.createChooser(sendIntent, "Share via"))
            Handler().postDelayed({
                FirebaseDatabase.getInstance().reference.child(code).child("go")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {

                            if (isValueAvailable(snapshot, "true") ) {
//                                                        binding.progressBar.visibility = View.VISIBLE
//                                                        binding.Create.visibility = View.GONE
//                                                        binding.idIVQrcode.visibility = View.GONE
//                                                        binding.textView4.visibility = View.GONE
//                                                        binding.getCode.visibility = View.GONE

                                startActivity(
                                    Intent(
                                        this@OnlineGamePage,
                                        Game_Page::class.java
                                    )
                                )
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


        }
    }
    fun errorMsg(value: String) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
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
}
