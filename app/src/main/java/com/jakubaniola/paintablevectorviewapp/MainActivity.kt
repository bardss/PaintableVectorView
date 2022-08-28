package com.jakubaniola.paintablevectorviewapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        codeButton.setOnClickListener {
            startActivity(
                Intent(this, InitializedInCodeActivity::class.java)
            )
        }
        xmlButton.setOnClickListener {
            startActivity(
                Intent(this, InitializedInXmlActivity::class.java)
            )
        }
    }

}