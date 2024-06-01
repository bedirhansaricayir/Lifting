package com.lifting.app.core.network.interceptor

import com.lifting.app.core.common.dispatchers.Dispatcher
import com.lifting.app.core.common.dispatchers.LiftingDispatchers.IO
import com.lifting.app.core.common.dispatchers.di.ApplicationScope
import com.lifting.app.core.datastore.SessionManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        scope.launch(ioDispatcher) {
            sessionManager.getToken().let { token ->
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
        }
        return chain.proceed(requestBuilder.build())
    }

}