package com.lifting.app

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.Window
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.AppLanguage
import com.lifting.app.core.model.AppTheme
import com.lifting.app.core.service.RestTimerService
import com.lifting.app.core.ui.common.AppLocale
import com.lifting.app.navigation.LiftingNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainScreenViewModel by viewModels()
    private var bound: Boolean = false

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {}
        override fun onServiceDisconnected(className: ComponentName) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateAppLanguage(AppLanguage.getLanguage(AppLocale.getLocaleCode(this)))
        actionBar?.hide()
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        setContent {
            val mainScreenState by viewModel.mainScreenState.collectAsStateWithLifecycle()
            splashScreen.setKeepOnScreenCondition { mainScreenState.shouldKeepOnScreen }
            val isDarkTheme = when (mainScreenState.appTheme) {
                AppTheme.Light -> false
                AppTheme.Dark -> true
                AppTheme.System -> isSystemInDarkTheme()
            }

            LiftingTheme(
                isDarkTheme = isDarkTheme
            ) {
                LiftingNavHost(mainScreenState)
                window.forceNavigationBarContrast()
                StatusBarProtection()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, RestTimerService::class.java).also { intent ->
            bindService(intent, mConnection, BIND_AUTO_CREATE)
        }
        bound = true
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            unbindService(mConnection)
            bound = false
        }
    }
}

@Composable
private fun StatusBarProtection(
    color: Color = LiftingTheme.colors.background,
    heightProvider: () -> Float = calculateGradientHeight(),
) {

    Canvas(Modifier.fillMaxSize()) {
        val calculatedHeight = heightProvider()
        val gradient = Brush.verticalGradient(
            colors = listOf(
                color.copy(alpha = 1f),
                color.copy(alpha = .8f),
                Color.Transparent
            ),
            startY = 0f,
            endY = calculatedHeight
        )
        drawRect(
            brush = gradient,
            size = Size(size.width, calculatedHeight),
        )
    }
}

@Composable
private fun calculateGradientHeight(): () -> Float {
    val statusBars = WindowInsets.statusBars
    val density = LocalDensity.current
    return { statusBars.getTop(density).times(1.2f) }
}

private fun Window.forceNavigationBarContrast() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.isNavigationBarContrastEnforced = false
    }
}