package com.wsr

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import java.lang.Float.max

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
        window.statusBarColor = getColor(R.color.moss_green).actionBarColorToStatusBarColor()
    }

    //確実に動作させるためにdispatchTouchEventで実行
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            closeIfTapOutsideFocusedEditView(event.rawX.toInt(), event.rawY.toInt())
        }
        return super.dispatchTouchEvent(event)
    }

    private fun closeIfTapOutsideFocusedEditView(tappedX: Int, tappedY: Int) {

        val view = currentFocus as? EditText ?: return

        //focusしているところの座標を取得
        val rect = Rect().also { view.getGlobalVisibleRect(it) }

        //focusしているところ以外がタップされたとき
        if (!rect.contains(tappedX, tappedY)) {
            view.clearFocus()
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}

private fun Int.actionBarColorToStatusBarColor(): Int {
    val hsl = FloatArray(3)
    ColorUtils.colorToHSL(this, hsl)
    hsl[0] = max(0F, hsl[0] - 6)
    hsl[2] = max(0F, hsl[2] - 0.09F)
    return ColorUtils.HSLToColor(hsl)
}