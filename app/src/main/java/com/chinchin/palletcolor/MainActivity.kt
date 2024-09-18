package com.chinchin.palletcolor

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chinchin.palletview.PalletColor
import com.chinchin.palletview.SpectrumColor

class MainActivity : AppCompatActivity() {
    private var currentColor = 0x00FFFFFF
    private var currentProgress = 100
    private lateinit var backgroundSelect: View
    private lateinit var palletView: PalletColor
    private lateinit var spectrumColor: SpectrumColor
    private lateinit var seekBar: SeekBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        backgroundSelect = findViewById<View>(R.id.bg_choose)
//        palletView = findViewById<PalletColor>(R.id.imageGridView)
        spectrumColor = findViewById<SpectrumColor>(R.id.spectrumColorView)
        seekBar = findViewById<SeekBar>(R.id.customSeekBar)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Update the alpha of currentColor based on SeekBar progress (0 to 100)
                seekBar?.let {
                    currentProgress = it.progress
                    updateBackground()
                }
            }
        })

//        palletView.setColorPickerLister(object : com.chinchin.palletview.ColorPickerListener {
//            override fun onColorSelect(colorHex: String, colorInt: Int) {
//              backgroundSelect.setBackgroundColor(colorInt)
//                currentColor = colorInt
//                seekBar.background = createGradientDrawable(endColor = colorInt)
//                updateBackground()
//                seekBar.visibility = View.VISIBLE
//            }
//        })
//

        spectrumColor.setColorPickerLister(object : com.chinchin.palletview.ColorPickerListener {
            override fun onColorSelect(colorHex: String, colorInt: Int) {
                backgroundSelect.setBackgroundColor(colorInt)
                currentColor = colorInt
                seekBar.background = createGradientDrawable(endColor = colorInt)
                updateBackground()
                seekBar.visibility = View.VISIBLE
            }
        })
    }

    private fun updateBackground() {
        if (currentColor != 0x00FFFFFF) {

            val alphaPercentage =
                currentProgress // Progress is already 0-100, which is the percentage
            val alphaValue =
                (alphaPercentage * 255) / 100 // Convert percentage (0-100) to ARGB alpha (0-255)

            currentColor =
                (currentColor and 0x00FFFFFF) or (alphaValue shl 24) // Set the new alpha in the color
            // Now, you can use the updated currentColor with new alpha
            println("Updated currentColor with alpha: ${Integer.toHexString(currentColor)}")
            backgroundSelect.setBackgroundColor(currentColor)
        }
    }

    private fun createGradientDrawable(endColor: Int): GradientDrawable {
        // Automatically calculate startColor as endColor with 0% alpha (transparent)
        val startColor = endColor and 0x00FFFFFF // Mask the color to set alpha to 0%

        return GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(startColor, endColor)
        ).apply {
            shape = GradientDrawable.RECTANGLE
            this.cornerRadius = 50f // Set the desired corner radius
        }
    }

}