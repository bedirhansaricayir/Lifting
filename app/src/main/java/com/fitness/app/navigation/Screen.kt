package com.fitness.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.fitness.app.R

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object HomeScreen : Screen(
        route = "home_screen",
        title = "Programlar",
        icon = Icons.Filled.Home
    )

    object OnBoardingScreen : Screen(
        route = "onboarding_screen",
        title = "OnBoarding",
        icon = Icons.Filled.Home
    )

    object TrackerScreen : Screen(
        route = "tracker_screen",
        title = "Analiz",
        icon = Icons.Filled.Home
    )

    object OptionalScreen : Screen(
        route = "optional_screen",
        title = "Optional",
        icon = Icons.Filled.Home
    )

}

class OnBoardingPage(
    @DrawableRes
    val image: Int,
    @StringRes
    val title: Int,
    @StringRes
    val description: Int
) {
    companion object {
        fun getData(): List<OnBoardingPage> {
            return listOf(
                OnBoardingPage(
                    image = R.drawable.ic_launcher_foreground,
                    title = R.string.FirstOnBoardingPageTitle,
                    description = R.string.FirstOnBoardingPageDescription
                ),
                OnBoardingPage(
                    image = R.drawable.ic_launcher_foreground,
                    title = R.string.SecondOnBoardingPageTitle,
                    description = R.string.SecondOnBoardingPageDescription
                ),
                OnBoardingPage(
                    image = R.drawable.ic_launcher_foreground,
                    title = R.string.ThirdOnBoardingPageTitle,
                    description = R.string.ThirdOnBoardingPageDescription
                )
            )
        }
    }
}
