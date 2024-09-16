### `PalletColor` - GitHub Setup, Tutorial, and Usage

`PalletColor` is a custom Android View that provides a color palette for users to select colors interactively. It works by displaying a grid of color cells, and users can click on any cell to select the corresponding color. The class supports drawing custom background images, tracking user clicks, and notifying listeners when a color is selected.

---

### Setup

To integrate the `PalletColor` library into your project, follow these steps:

#### Step 1: Add the JitPack repository to your build file

Add the JitPack repository in your root `build.gradle` or `settings.gradle` file under `dependencyResolutionManagement`.

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

#### Step 2: Add the dependency

In your module's `build.gradle` file, add the following dependency:

```groovy
dependencies {
    implementation 'com.github.chinhdl891:Color-Picker-Library:1.0.1'
}
```

### Demo

Here is a demonstration of how the library works:

 ![](https://github.com/chinhdl891/Color-Picker-Library/blob/master/demo.gif)


### Features:
- **Interactive Color Selection**: Users can select colors by tapping on cells in a grid.
- **Customizable Grid Size**: The number of columns and rows in the color grid can be customized.
- **Background Image Support**: Allows setting a custom background image behind the color grid.
- **Color Selection Listener**: Notifies listeners when a user selects a color from the grid.

---

### Installation

1. **Add the class to your project**: Copy the `PalletColor.kt` file into your Android project.

2. **Declare the `PalletColor` in your XML layout file**:

```xml
<com.chinchin.palletview.PalletColor
    android:id="@+id/palletView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cellX="12"
    app:cellY="9"
    app:backgroundImage="@drawable/color_palette_image"
    app:sizeStokeChoose="3" />
```

---

### Usage

1. **Initialize in your Activity or Fragment**:

In your `Activity` or `Fragment`, declare and initialize `PalletColor` using `findViewById()`.

```kotlin
class MainActivity : AppCompatActivity() {
    
    private lateinit var palletView: PalletColor
    private lateinit var backgroundSelect: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the PalletColor view
        palletView = findViewById(R.id.palletView)
        backgroundSelect = findViewById(R.id.bg_choose)

        // Handle color selection
        palletView.setColorPickerLister(object : ColorPickerListener {
            override fun onColorSelect(colorHex: String, colorInt: Int) {
                // Set the background of a view to the selected color
                backgroundSelect.setBackgroundColor(colorInt)
            }
        })
    }
}
```

2. **Set a Color Selection Listener**:

The `PalletColor` view provides a method `setColorPickerLister()` to handle color selections. Implement the `ColorPickerListener` interface to receive color selection events.

```kotlin
palletView.setColorPickerLister(object : ColorPickerListener {
    override fun onColorSelect(colorHex: String, colorInt: Int) {
        // Change the background color or handle the selected color
        backgroundSelect.setBackgroundColor(colorInt)
    }
})
```

---

### Customization

You can customize various aspects of the `PalletColor` view:

1. **Number of Columns and Rows**:  
   You can specify the number of columns (`cellX`) and rows (`cellY`) in the color grid using XML attributes:

   ```xml
   app:cellX="12"
   app:cellY="9"
   ```

2. **Background Image**:  
   Set a custom background image for the color grid using the `backgroundImage` attribute in your XML layout:

   ```xml
   app:backgroundImage="@drawable/color_palette_image"
   ```

3. **Selection Stroke Size**:  
   Customize the stroke size around the selected color using the `sizeStokeChoose` attribute:

   ```xml
   app:sizeStokeChoose="3"
   ```

---

### Key Methods

- **`setColorPickerLister(listener: ColorPickerListener)`**:  
  Register a listener to handle color selection events.

  ```kotlin
  palletView.setColorPickerLister(object : ColorPickerListener {
      override fun onColorSelect(colorHex: String, colorInt: Int) {
          // Your custom code to handle color selection
      }
  })
  ```

- **`getColorFromCenterOfSelectedRect(centerX: Int, centerY: Int): String`**:  
  A private helper method to get the color at the center of the selected rectangle (color cell) on the grid.

  This method returns the color in hex format (`#RRGGBB`).

---

### Example

Below is a complete example that demonstrates how to use `PalletColor` in an Android activity:

```kotlin
import android.graphics.Color
import android.os.Bundle
import android.widget.View
import androidx.appcompat.app.AppCompatActivity
import com.chinchin.palletview.PalletColor
import com.chinchin.palletview.ColorPickerListener

class MainActivity : AppCompatActivity() {

    private lateinit var palletView: PalletColor
    private lateinit var backgroundSelect: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the PalletColor view
        palletView = findViewById(R.id.palletView)
        backgroundSelect = findViewById(R.id.bg_choose)

        // Set up color selection listener
        palletView.setColorPickerLister(object : ColorPickerListener {
            override fun onColorSelect(colorHex: String, colorInt: Int) {
                // Set the background of another view to the selected color
                backgroundSelect.setBackgroundColor(colorInt)
            }
        })
    }
}
```

---

### Conclusion

`PalletColor` is a powerful and customizable tool for adding interactive color selection to your Android apps. Its ability to handle grid-based color selection with customizable backgrounds and size makes it ideal for applications that require a color picker.

Make sure to properly implement the `ColorPickerListener` interface to capture the selected color and customize the grid to match your app's design needs.
