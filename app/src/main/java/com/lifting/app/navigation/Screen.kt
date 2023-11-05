package com.lifting.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lifting.app.R
import com.lifting.app.common.constants.NavigationConstants.CALCULATOR_SCREEN
import com.lifting.app.common.constants.NavigationConstants.HOME_SCREEN
import com.lifting.app.common.constants.NavigationConstants.TRACKER_SCREEN

sealed class Screen(
    val route: String,
    @StringRes
    val title: Int,
    val icon: Int? = null
) {
    object HomeScreen : Screen(
        route = HOME_SCREEN,
        title = R.string.label_navbar_workouts,
        icon = R.drawable.workout
    )

    object TrackerScreen : Screen(
        route = TRACKER_SCREEN,
        title = R.string.label_navbar_analysis,
        icon = R.drawable.analysis
    )

    object CalculatorScreen : Screen(
        route = CALCULATOR_SCREEN,
        title = R.string.label_navbar_calculators,
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
