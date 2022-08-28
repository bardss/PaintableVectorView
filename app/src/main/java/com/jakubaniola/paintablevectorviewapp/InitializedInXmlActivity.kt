package com.jakubaniola.paintablevectorviewapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakubaniola.paintablevectorview.PaintType
import com.jakubaniola.paintablevectorview.PaintableVectorView
import kotlinx.android.synthetic.main.activity_initialized_in_xml.*
import kotlinx.android.synthetic.main.activity_main.*

class InitializedInXmlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initialized_in_xml)
    }

    override fun onStart() {
        super.onStart()
        setupResetButton()
        setupPaintTypeButtons()
        setupPaintColorButtons()
    }

    private fun setupResetButton() {
        resetColorsButton.setOnClickListener {
            paintableView.resetColors()
        }
    }

    private fun setupPaintTypeButtons() {
        drawPathButton.setOnClickListener {
            paintableView.paintType = PaintType.PAINT_PATH
        }
        drawGroupButton.setOnClickListener {
            paintableView.paintType = PaintType.PAINT_GROUP
        }
    }

    private fun setupPaintColorButtons() {
        paintBlueButton.setOnClickListener {
            paintableView.paintColor = resources.getColor(R.color.blue)
        }
        paintRedButton.setOnClickListener {
            paintableView.paintColor = resources.getColor(R.color.red)
        }
        paintGreenButton.setOnClickListener {
            paintableView.paintColor = resources.getColor(R.color.green)
        }
        paintYellowButton.setOnClickListener {
            paintableView.paintColor = resources.getColor(R.color.yellow)
        }
    }
}