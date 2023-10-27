package com.example.tictactoe

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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



            val dynamicLinkUri = Uri.Builder()
                .scheme("https")
                .authority("tictactoigame.page.link") // Your Dynamic Links domain
                .appendPath("online") // Path for deep link
                .appendQueryParameter("code", code)

            // Share the Dynamic Link
            val dynamicLinkUrl = dynamicLinkUri.build().toString()


//            FirebaseDatabase.getInstance().reference.child("link").child()
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, dynamicLinkUri.toString()+code)
            startActivity(Intent.createChooser(intent, "Share Dynamic Link"))


        }
    }
}
