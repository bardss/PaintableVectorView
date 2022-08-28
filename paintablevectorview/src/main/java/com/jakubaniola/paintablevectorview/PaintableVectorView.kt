package com.jakubaniola.paintablevectorview

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.use

class PaintableVectorView : AppCompatImageView {

    constructor(
        context: Context,
        drawableId: Int,
        paintType: PaintType = PaintType.PAINT_PATH,
        paintColor: Int
    ) : super(context) {
        this.drawableId = drawableId
        this.paintType = paintType
        this.paintColor = paintColor
        initBackground()
    }

    constructor(
        context: Context, attrs: AttributeSet
    ) : super(context, attrs) {
        initArgs(context, attrs)
        initBackground()
    }

    constructor(
        context: Context, attrs: AttributeSet, defStyle: Int = 0
    ) : super(context, attrs, defStyle) {
        initArgs(context, attrs)
        initBackground()
    }

    private var drawableId: Int = INVALID_RESOURCE
    var paintColor: Int = INVALID_RESOURCE
    var paintType: PaintType = PaintType.PAINT_PATH
    private lateinit var vectorShape: LayeredVectorShape

    private fun initArgs(context: Context, attrsSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attrsSet, R.styleable.PaintableVectorView)
        attrs.use {
            drawableId =
                attrs.getResourceId(R.styleable.PaintableVectorView_drawable, INVALID_RESOURCE)
            paintColor =
                attrs.getResourceId(R.styleable.PaintableVectorView_paintColor, INVALID_RESOURCE)
            val paintTypeInt =
                attrs.getInt(R.styleable.PaintableVectorView_paintType, INVALID_RESOURCE)
            paintType = PaintType.values()[paintTypeInt]
        }
    }

    private fun initBackground() {
        checkIfArgumentsAreValid()
        vectorShape = LayeredVectorShape(context, drawableId)
        background = ShapeDrawable(vectorShape)
    }

    private fun checkIfArgumentsAreValid() {
        if (drawableId == INVALID_RESOURCE) {
            throw IllegalArgumentException("Missing obligatory drawableId")
        } else if (paintColor == INVALID_RESOURCE) {
            throw IllegalArgumentException("Missing obligatory paintColor")
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (paintType) {
            PaintType.PAINT_PATH -> onPathPaint(event)
            PaintType.PAINT_GROUP -> onGroupPaint(event)
        }
        return false
    }

    private fun onGroupPaint(event: MotionEvent) {
        val clickedLayers = vectorShape.getGroupLayersAt(event.x.toInt(), event.y.toInt())
        if (clickedLayers.isEmpty()) {
            onPathPaint(event)
        } else {
            paintLayers(clickedLayers)
        }
    }

    private fun onPathPaint(event: MotionEvent) {
        val clickedLayer = vectorShape.getLayersAt(event.x.toInt(), event.y.toInt())
        paintLayer(clickedLayer)
    }

    private fun paintLayers(clickedLayers: List<LayeredVectorShape.Layer>) {
        clickedLayers.forEach {
            paintLayer(it)
        }
    }

    private fun paintLayer(it: LayeredVectorShape.Layer) {
        it.paint.color = paintColor
        invalidate()
    }

    fun resetColors() {
        vectorShape.resetColors()
        invalidate()
    }

    companion object {
        private const val INVALID_RESOURCE = -1
    }
}