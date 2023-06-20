package com.fitness.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.fitness.app.R
import com.fitness.app.core.Constants.Companion.HEALTH_SCREEN
import com.fitness.app.core.Constants.Companion.HEALTH_SCREEN_TITLE
import com.fitness.app.core.Constants.Companion.HOME_SCREEN
import com.fitness.app.core.Constants.Companion.HOME_SCREEN_TITLE
import com.fitness.app.core.Constants.Companion.ONBOARDING_SCREEN
import com.fitness.app.core.Constants.Companion.ONBOARDING_SCREEN_TITLE
import com.fitness.app.core.Constants.Companion.TRACKER_SCREEN
import com.fitness.app.core.Constants.Companion.TRACKER_SCREEN_TITLE

sealed class Screen(
    val route: String,
    val title: String,
    val icon: Int? = null
) {
    object HomeScreen : Screen(
        route = HOME_SCREEN,
        title = HOME_SCREEN_TITLE,
        icon = R.drawable.workout
    )

    object OnBoardingScreen : Screen(
        route = ONBOARDING_SCREEN,
        title = ONBOARDING_SCREEN_TITLE
    )

    object TrackerScreen : Screen(
        route = TRACKER_SCREEN,
        title = TRACKER_SCREEN_TITLE,
        icon = R.drawable.analysis
    )

    object HealthScreen : Screen(
        route = HEALTH_SCREEN,
        title = HEALTH_SCREEN_TITLE,
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
