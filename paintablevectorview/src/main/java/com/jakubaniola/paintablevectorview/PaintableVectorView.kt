package com.jakubaniola.paintablevectorview

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.view.MotionEvent
import android.widget.ImageView

class PaintableVectorView(
    context: Context,
    drawableId: Int,
    var paintType: PaintType = PaintType.PAINT_PATH
) : ImageView(context) {
    private var vectorShape: LayeredVectorShape = LayeredVectorShape(context, drawableId)
    var paintColor: Int? = null

    init {
        background = ShapeDrawable(vectorShape)
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
        val clickedLayers = vectorShape.getLayersAt(event.x.toInt(), event.y.toInt())
        paintLayers(clickedLayers)
    }

    private fun paintLayers(clickedLayers: List<LayeredVectorShape.Layer>) {
        paintColor?.let { paintColor ->
            clickedLayers.forEach {
                it.paint.color = paintColor
                invalidate()
            }
        }
    }
}