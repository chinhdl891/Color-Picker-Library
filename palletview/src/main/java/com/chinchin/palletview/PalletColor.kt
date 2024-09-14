package com.chinchin.palletview

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class PalletColor @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Default properties
    private var numColumns = 12
    private var numRows = 9
    private var backgroundDrawable = 0
    private val paint = Paint()

    // Size of each cell
    private var cellWidth: Int = 0
    private var cellHeight: Int = 0
    private var selectedRect: Rect? = null
    private var sizeStokeChoose = 5f
    private var colorPickerListener: ColorPickerListener? = null

    fun setColorPickerLister(colorPickerListener: ColorPickerListener) {
        this.colorPickerListener = colorPickerListener
    }

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ImageGridView, 0, 0)
            numColumns = typedArray.getInteger(R.styleable.ImageGridView_cellX, 12)
            numRows = typedArray.getInteger(R.styleable.ImageGridView_cellY, 9)
            backgroundDrawable =
                typedArray.getResourceId(R.styleable.ImageGridView_backgroundImage, 0)
            sizeStokeChoose = typedArray.getFloat(R.styleable.ImageGridView_sizeStokeChoose, 2f)

            val strokeWidthInPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                sizeStokeChoose,
                resources.displayMetrics
            )

            paint.style = Paint.Style.STROKE
            paint.color = Color.WHITE
            paint.strokeWidth = strokeWidthInPx
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // If backgroundDrawable is set, draw the background image
        if (backgroundDrawable != 0) {
            val bitmap = BitmapFactory.decodeResource(resources, backgroundDrawable)
            canvas.drawBitmap(bitmap, null, Rect(0, 0, width, height), null)
        }

        // Calculate the size of each cell
        cellWidth = width / numColumns
        cellHeight = height / numRows

        // Draw the selected rectangle (if available)
        selectedRect?.let {
            canvas.drawRect(it, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            // Get x and y coordinates when the user clicks
            val x = event.x
            val y = event.y

            // Determine the column and row based on coordinates
            val column = (x / cellWidth).toInt()
            val row = (y / cellHeight).toInt()

            // Print out the clicked cell's information
            println("User clicked on: column $column, row $row")

            // Create a rectangle at the position where the user clicked
            val left = column * cellWidth
            val top = row * cellHeight
            val right = left + cellWidth
            val bottom = top + cellHeight
            selectedRect = Rect(left, top, right, bottom)

            // Calculate the center coordinates of the selectedRect
            val centerX = (left + right) / 2
            val centerY = (top + bottom) / 2

            // Get the color from the center of the selectedRect from the background image
            val hexColor = getColorFromCenterOfSelectedRect(centerX, centerY)
            colorPickerListener?.onColorSelect(colorHex = hexColor, colorInt = Color.parseColor(hexColor))

            // Redraw the view to display the selected rectangle
            invalidate()
        }
        return true
    }

    // Function to get the hex color of the pixel at the center of the selectedRect
    private fun getColorFromCenterOfSelectedRect(centerX: Int, centerY: Int): String {
        // Check if the background bitmap exists
        val bitmap = BitmapFactory.decodeResource(resources, backgroundDrawable)
        bitmap?.let {
            // Calculate the corresponding position in the bitmap of the background image
            val scaledX = (centerX.toFloat() / width * it.width).toInt()
            val scaledY = (centerY.toFloat() / height * it.height).toInt()

            // Get the pixel color from the bitmap at the center position
            val color = it.getPixel(scaledX, scaledY)

            // Convert the color value to a Hex color string (#RRGGBB)
            return String.format("#%06X", 0xFFFFFF and color)
        }
        // If no color is found, return default white color
        return "#FFFFFF"
    }
}
