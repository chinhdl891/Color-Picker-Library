package com.chinchin.palletview

interface ColorPickerListener {
    open fun onColorSelect(colorHex : String, colorInt : Int)
}