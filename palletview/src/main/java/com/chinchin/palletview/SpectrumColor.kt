package com.chinchin.palletview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat

private const val TAG = "SpectrumColor"

class SpectrumColor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var circleX = 0f
    private var circleY = 0f

    // Circle stroke width set to 2dp
    private val strokeWidth = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 2f, resources.displayMetrics
    )

    // Circle radius in pixels, converted from 12dp (for a 24dp diameter)
    private val circleRadius = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 12f, resources.displayMetrics
    )

    private val paint = Paint()
    private var backgroundBitmap: Bitmap? = null
    private var backgroundDrawable: BitmapDrawable? = null
    private var scaledBackgroundBitmap: Bitmap? = null
    private var colorPickerListener: ColorPickerListener? = null

    fun setColorPickerLister(colorPickerListener: ColorPickerListener) {
        this.colorPickerListener = colorPickerListener
    }

    init {
        // Get the custom attributes from XML
        context.theme.obtainStyledAttributes(attrs, R.styleable.MovableCircleView, 0, 0).apply {
            try {
                // Load drawable resource
                val drawableResId = getResourceId(R.styleable.MovableCircleView_viewBackgroundDrawable, -1)
                if (drawableResId != -1) {
                    backgroundDrawable = ContextCompat.getDrawable(context, drawableResId) as BitmapDrawable?
                    backgroundBitmap = backgroundDrawable?.bitmap
                }
            } finally {
                recycle()
            }
        }

        // Configure the paint object for stroke
        paint.style = Paint.Style.STROKE // Set to draw only the stroke
        paint.strokeWidth = strokeWidth // Set the stroke width to 2dp
        paint.color = Color.WHITE // Default stroke color is white
        paint.isAntiAlias = true // Smooth edges of the circle
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Resize the background bitmap to match the view dimensions
        backgroundBitmap?.let {
            scaledBackgroundBitmap = Bitmap.createScaledBitmap(it, w, h, false)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the scaled background bitmap
        scaledBackgroundBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
        }

        // Draw the circle with stroke and transparent inside
        canvas.drawCircle(circleX, circleY, circleRadius, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                // Allow the circle center to reach the edges but not beyond
                circleX = event.x.coerceIn(0f, width.toFloat())
                circleY = event.y.coerceIn(0f, height.toFloat())

                // Log the center of the circle during movement
                Log.d(TAG, "Circle center: ($circleX, $circleY)")

                // Set the stroke color to white while moving
                paint.color = Color.WHITE
                invalidate() // Redraw the view
            }

            MotionEvent.ACTION_UP -> {
                // Log the center of the circle when the user lifts their finger
                Log.d(TAG, "Final Circle center: ($circleX, $circleY)")

                // On release, set the stroke color to the background color at the center
                updateCircleColorToBackground()
                invalidate() // Redraw the view
            }
        }
        return true
    }

    private fun updateCircleColorToBackground() {
        // Ensure the touch is within the bounds of the scaled bitmap
        scaledBackgroundBitmap?.let {
            if (circleX >= 0 && circleX < it.width && circleY >= 0 && circleY < it.height) {

                // Get the pixel color at the center of the circle from the scaled background image
                val pixelColor = it.getPixel(circleX.toInt(), circleY.toInt())
                // Set the stroke color to the pixel color
                val hexColor = String.format("#%06X", 0xFFFFFF and pixelColor)
                Log.d(TAG, "updateCircleColorToBackground: $hexColor")
                colorPickerListener?.onColorSelect(hexColor, pixelColor)
            }
        }
    }
}
