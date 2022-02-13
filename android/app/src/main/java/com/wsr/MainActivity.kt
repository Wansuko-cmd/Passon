package com.wsr

import android.os.Bundle
import android.view.WindowManager
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

    override fun onSupportNavigateUp() = navController.navigateUp()
}

private fun Int.actionBarColorToStatusBarColor(): Int {
    val hsl = FloatArray(3)
    ColorUtils.colorToHSL(this, hsl)
    hsl[0] = max(0F, hsl[0] - 6)
    hsl[2] = max(0F, hsl[2] - 0.09F)
    return ColorUtils.HSLToColor(hsl)
}