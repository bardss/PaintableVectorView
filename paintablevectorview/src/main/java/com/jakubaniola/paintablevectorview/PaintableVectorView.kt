package com.jakubaniola.paintablevectorview

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.view.MotionEvent
import android.widget.ImageView

class PaintableVectorView(
    context: Context,
    drawableId: Int,
    var paintType: PaintType = PaintType.PAINT_PATH,
    var paintColor: Int
) : ImageView(context) {
    private var vectorShape: LayeredVectorShape = LayeredVectorShape(context, drawableId)

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
}