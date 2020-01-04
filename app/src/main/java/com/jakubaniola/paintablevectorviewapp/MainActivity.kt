package com.jakubaniola.paintablevectorviewapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakubaniola.paintablevectorview.PaintType
import com.jakubaniola.paintablevectorview.PaintableVectorView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var paintableView: PaintableVectorView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        paintableView = PaintableVectorView(
            this,
            R.drawable.ic_car,
            PaintType.PAINT_GROUP,
            resources.getColor(R.color.blue)
        )
        setupPaintableView()
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

    private fun setupPaintableView() {
        paintableBoxLayout.addView(paintableView)
    }
}