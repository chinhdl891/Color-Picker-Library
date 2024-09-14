package com.chinchin.palletcolor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chinchin.palletview.PalletColor

class MainActivity : AppCompatActivity() {
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

        val backgroundSelect  = findViewById<View>(R.id.bg_choose)
        val palletView = findViewById<PalletColor>(R.id.imageGridView)

        palletView.setColorPickerLister(object : com.chinchin.palletview.ColorPickerListener {
            override fun onColorSelect(colorHex: String, colorInt: Int) {
              backgroundSelect.setBackgroundColor(colorInt)
            }
        })
    }
}