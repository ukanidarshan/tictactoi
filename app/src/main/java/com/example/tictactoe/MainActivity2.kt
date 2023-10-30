package com.example.tictactoe

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.tictactoe.databinding.ActivityMain2Binding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

var isCodeMaker = true;
var code = "null";
var codeFound = false
var checkTemp = true
var keyValue: String = "null"

class MainActivity2 : AppCompatActivity() {
    private var uri: Uri? = null
    lateinit var binding: ActivityMain2Binding
    var data = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.Create.setOnClickListener {
            code = "null";
            codeFound = false
            checkTemp = true
            keyValue = "null"

            code = generateRandomCode(8)

            if (code != "null" && code != null && code != "") {

                val qrCodeBitmap = generateQRCode(code)
                binding.idIVQrcode.setImageBitmap(qrCodeBitmap)

                binding.progressBar.visibility = View.VISIBLE
                binding.Create.visibility = View.GONE
                binding.idIVQrcode.visibility = View.VISIBLE
                binding.textView4.visibility = View.GONE
                binding.getCode.visibility = View.GONE
                binding.back.visibility = View.GONE

                isCodeMaker = true;
                FirebaseDatabase.getInstance().reference.child(code).child("codes")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            var check = isValueAvailable(snapshot, code)

                            Handler().postDelayed({

                                if (check == true) {

                                } else {


                                    FirebaseDatabase.getInstance().reference.child(code)
                                        .child("codes").push()
                                        .setValue(code)
                                    isValueAvailable(snapshot, code)

                                    checkTemp = false
                                    Handler().postDelayed({
                                        FirebaseDatabase.getInstance().reference.child(code)
                                            .child("go")
                                            .addValueEventListener(object : ValueEventListener {
                                                override fun onCancelled(error: DatabaseError) {
                                                    TODO("Not yet implemented")
                                                }

                                                override fun onDataChange(snapshot: DataSnapshot) {

                                                    if (isValueAvailable(snapshot, "true")) {

                                                        startActivity(
                                                            Intent(
                                                                this@MainActivity2,
                                                                Game_Page::class.java
                                                            )
                                                        )
                                                        binding.Create.visibility = View.VISIBLE
                                                        binding.textView4.visibility = View.VISIBLE
                                                        binding.getCode.visibility = View.VISIBLE
                                                        binding.back.visibility = View.VISIBLE
                                                        binding.progressBar.visibility = View.GONE
                                                        errorMsg("Please don't go back")
                                                    }
                                                }
                                            })
                                    }, 1)

                                }
                            }, 2000)


                        }

                    })
            } else {
                binding.Create.visibility = View.VISIBLE
                binding.textView4.visibility = View.VISIBLE
                binding.getCode.visibility = View.VISIBLE
                binding.back.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                errorMsg("Enter Code Properly")
            }
        }

        binding.getCode.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setOrientationLocked(true)
            integrator.initiateScan()
            isCodeMaker = false;


        }

    }

    fun accepted() {

        if (data) {
            startActivity(Intent(this, Game_Page::class.java))
            binding.Create.visibility = View.VISIBLE
            binding.textView4.visibility = View.VISIBLE
            binding.back.visibility = View.VISIBLE
            binding.getCode.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            errorMsg("Please don't go back")
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

    private fun generateQRCode(textToEncode: String): Bitmap {
        val width = 500
        val height = 500

        val hints = mutableMapOf<EncodeHintType, Any>()
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
        hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix =
            qrCodeWriter.encode(textToEncode, BarcodeFormat.QR_CODE, width, height, hints)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(
                    x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
                )
            }
        }

        return bitmap
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
        if (result != null) {
            if (result.contents != null) {
                // Handle the scanned QR code result
                val scannedText = result.contents
                code = scannedText.toString()

                binding.progressBar.visibility = View.VISIBLE
                binding.Create.visibility = View.GONE
                binding.textView4.visibility = View.GONE
                binding.back.visibility = View.GONE
                binding.getCode.visibility = View.GONE

                FirebaseDatabase.getInstance().reference.child(code).child("codes")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {

                            data = isValueAvailable(snapshot, code)
                            Handler().postDelayed({
                                if (data == true) {
                                    FirebaseDatabase.getInstance().reference.child(code).child("go")
                                        .push()
                                        .setValue("true")
                                    codeFound = true
                                    accepted()
                                    binding.Create.visibility = View.VISIBLE
                                    binding.textView4.visibility = View.VISIBLE
                                    binding.getCode.visibility = View.VISIBLE
                                    binding.back.visibility = View.VISIBLE
                                    binding.progressBar.visibility = View.GONE
                                } else {
                                    binding.Create.visibility = View.VISIBLE
                                    binding.textView4.visibility = View.VISIBLE
                                    binding.getCode.visibility = View.VISIBLE
                                    binding.back.visibility = View.VISIBLE
                                    binding.progressBar.visibility = View.GONE
                                    errorMsg("Invalid Code")
                                }
                            }, 2000)


                        }


                    })

            } else {
                super.onActivityResult(requestCode, resultCode, intent)
            }

        } else {
            // Handle canceled scan or no QR code found
        }
    }
}

fun generateRandomCode(length: Int): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length).map { _ -> charPool.random() }.joinToString("")
}
