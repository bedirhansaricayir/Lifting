package com.fitness.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.fitness.app.R

/**
 * Uygulamada sahip olduğumuz tüm ekranların tanımlamasının yapıldığı dosya
 */
sealed class Screen(
    val route: String,
    val title: String,
    val icon: Int? = null
) {
    object HomeScreen : Screen(
        route = "home_screen",
        title = "Programlar",
        icon = R.drawable.workout
    )

    object OnBoardingScreen : Screen(
        route = "onboarding_screen",
        title = "OnBoarding"
    )

    object TrackerScreen : Screen(
        route = "tracker_screen",
        title = "Analiz",
        icon = R.drawable.analysis
    )

    object HealthScreen : Screen(
        route = "health_screen",
        title = "Sağlık",
        icon = R.drawable.pulse
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
                    image = R.drawable.onboarding_image1,
                    title = R.string.FirstOnBoardingPageTitle,
                    description = R.string.FirstOnBoardingPageDescription
                ),
                OnBoardingPage(
                    image = R.drawable.onboarding_image2,
                    title = R.string.SecondOnBoardingPageTitle,
                    description = R.string.SecondOnBoardingPageDescription
                ),
                OnBoardingPage(
                    image = R.drawable.onboarding_image3,
                    title = R.string.ThirdOnBoardingPageTitle,
                    description = R.string.ThirdOnBoardingPageDescription
                )
            )
        }
    }
}
