package com.lifting.app.core.common.dispatchers

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: LiftingDispatchers)

enum class LiftingDispatchers {
    Default,
    IO,
}