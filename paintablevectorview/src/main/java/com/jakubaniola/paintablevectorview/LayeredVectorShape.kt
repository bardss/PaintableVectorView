package com.jakubaniola.paintablevectorview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.shapes.Shape
import android.util.AttributeSet
import android.util.Log
import android.util.Xml
import androidx.core.graphics.PathParser
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.util.*

private const val TAG_VECTOR = "vector"
private const val TAG_PATH = "path"
private const val TAG_GROUP = "group"

private const val attrName = android.R.attr.name
private const val attrColor = android.R.attr.fillColor
private const val attrPathData = android.R.attr.pathData
private const val attrWidth = android.R.attr.viewportWidth
private const val attrHeight = android.R.attr.viewportHeight

private const val defaultViewportValue = 0f
private const val defaultColorValue = -0x21523f22

class LayeredVectorShape(
    context: Context,
    id: Int
) : Shape() {
    private val viewportRect = RectF()
    private val layers = ArrayList<Layer>()

    init {
        parseVectorDrawableXml(context, id)
    }

    private fun parseVectorDrawableXml(context: Context, id: Int) {
        val parser = context.resources.getXml(id)
        val attributeSet = Xml.asAttributeSet(parser)
        try {
            var parserEventType = parser.eventType
            var group: String? = null
            while (parserEventType != XmlPullParser.END_DOCUMENT) {
                when (parserEventType) {
                    XmlPullParser.START_TAG ->
                        when (parser.name) {
                            TAG_GROUP -> group = handleGroupTag(context, attributeSet)
                            TAG_VECTOR -> {
                                val vector = handleVectorTag(context, attributeSet)
                                vector.recycle()
                            }
                            TAG_PATH -> {
                                val path = handlePathTag(context, attributeSet, group)
                                path.recycle()
                            }
                        }
                    XmlPullParser.END_TAG ->
                        when (parser.name) {
                            TAG_GROUP -> group = null
                        }
                }
                parserEventType = parser.next()
            }
        } catch (e: XmlPullParserException) {
            handleException(e)
        } catch (e: IOException) {
            handleException(e)
        }
    }

    private fun handleGroupTag(
        context: Context,
        attributeSet: AttributeSet?
    ): String? {
        val attrs = intArrayOf(attrName)
        val positionOfNameAttr = attrs.indexOf(attrName)
        val obtainedAttributeSet = context.obtainStyledAttributes(attributeSet, attrs)
        return obtainedAttributeSet.getString(positionOfNameAttr)
    }

    private fun handleVectorTag(
        context: Context,
        attributeSet: AttributeSet?
    ): TypedArray {
        val attrs = intArrayOf(attrWidth, attrHeight)
        val positionOfWidthAttr = attrs.indexOf(attrWidth)
        val positionOfHeightAttr = attrs.indexOf(attrHeight)
        val obtainedAttributeSet = context.obtainStyledAttributes(attributeSet, attrs)
        val viewportRight = obtainedAttributeSet.getFloat(positionOfWidthAttr, defaultViewportValue)
        val viewportBottom =
            obtainedAttributeSet.getFloat(positionOfHeightAttr, defaultViewportValue)
        viewportRect.set(0f, 0f, viewportRight, viewportBottom)
        return obtainedAttributeSet
    }

    private fun handlePathTag(
        context: Context,
        attributeSet: AttributeSet?,
        group: String?
    ): TypedArray {
        val attrs = intArrayOf(attrName, attrColor, attrPathData)
        val positionOfNameAttr = attrs.indexOf(attrName)
        val positionOfColorAttr = attrs.indexOf(attrColor)
        val positionOfPathDataAttr = attrs.indexOf(attrPathData)
        val obtainedAttributeSet = context.obtainStyledAttributes(attributeSet, attrs)
        val pathName = obtainedAttributeSet.getString(positionOfNameAttr) ?: "noNamePath"
        val pathFillColor = obtainedAttributeSet.getColor(positionOfColorAttr, defaultColorValue)
        val pathData = obtainedAttributeSet.getString(positionOfPathDataAttr)
        if (pathData != null) {
            val layer = Layer(pathData, pathFillColor, pathName, group)
            layers.add(layer)
        }
        return obtainedAttributeSet
    }

    private fun handleException(e: Exception) {
        Log.e("LayeredVectorShape", "LayeredVectorShape constructor error")
        e.printStackTrace()
    }

    fun getLayersAt(x: Int, y: Int): List<Layer> {
        val foundLayers = mutableListOf<Layer>()
        layers.forEach { layer ->
            if (layer.region.contains(x, y)) {
                foundLayers.add(layer)
            }
        }
        return foundLayers
    }

    fun getGroupLayersAt(x: Int, y: Int): List<Layer> {
        var groupAtPosition: String? = null
        val listOfLayersWithGroup = mutableListOf<Layer>()
        layers.forEach { layer ->
            if (layer.region.contains(x, y)) {
                groupAtPosition = layer.group
            }
        }
        if (groupAtPosition != null) {
            layers.forEach { layer ->
                if (layer.group == groupAtPosition) {
                    listOfLayersWithGroup.add(layer)
                }
            }
        }
        return listOfLayersWithGroup
    }

    override fun onResize(width: Float, height: Float) {
        val matrix = Matrix()
        val shapeRegion = Region(0, 0, width.toInt(), height.toInt())
        matrix.setRectToRect(viewportRect, RectF(0f, 0f, width, height), Matrix.ScaleToFit.FILL)
        for (layer in layers) {
            layer.transform(matrix, shapeRegion)
        }
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        for (layer in layers) {
            canvas.drawPath(layer.transformedPath, layer.paint)
        }
    }

    class Layer(
        data: String,
        color: Int,
        var name: String,
        var group: String?
    ) {
        private var originalPath: Path = PathParser.createPathFromPathData(data)
        var transformedPath = Path()
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        var region = Region()

        init {
            paint.color = color
        }

        fun transform(matrix: Matrix, clip: Region) {
            originalPath.transform(matrix, transformedPath)
            region.setPath(transformedPath, clip)
        }

        override fun toString(): String {
            return name
        }
    }
}