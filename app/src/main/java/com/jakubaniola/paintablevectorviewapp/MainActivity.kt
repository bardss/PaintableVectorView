package com.jakubaniola.paintablevectorviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jakubaniola.paintablevectorview.PaintType
import com.jakubaniola.paintablevectorview.PaintableVectorView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val view = PaintableVectorView(this, R.drawable.ic_car, PaintType.PAINT_GROUP)
        view.paintColor = resources.getColor(R.color.colorPrimaryDark)
        drawableBoxLayout.addView(view)
    }
}