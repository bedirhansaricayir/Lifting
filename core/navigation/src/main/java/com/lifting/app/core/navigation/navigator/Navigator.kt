package com.lifting.app.core.navigation.navigator


import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/*
val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("No LocalNavigator given")
}

@Composable
fun NavigatorHost(
    viewModel: NavigatorViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalNavigator provides viewModel.navigator, content = content)
}

sealed class NavigationEvent(open val route: String) {
    object Back : NavigationEvent("Back")
    data class Destination(override val route: String, val root: String? = null) :
        NavigationEvent(route)

    override fun toString() = route
}

class Navigator {
    private val navigationQueue = Channel<NavigationEvent>(Channel.CONFLATED)

    fun navigate(route: String) {
        val basePath = route.split("/").firstOrNull()
        val root = if (NAV_BAR_SCREENS.any { it.route == basePath }) basePath else null
        navigationQueue.trySend(NavigationEvent.Destination(route, root))
    }

    fun goBack() {
        navigationQueue.trySend(NavigationEvent.Back)
    }

    val queue = navigationQueue.receiveAsFlow()
}
*/
