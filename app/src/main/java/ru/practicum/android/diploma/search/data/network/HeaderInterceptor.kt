package ru.practicum.android.diploma.search.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.common.util.constants.NetworkConst.APP_NAME
import ru.practicum.android.diploma.common.util.constants.NetworkConst.AUTHORIZATION
import ru.practicum.android.diploma.common.util.constants.NetworkConst.BEARER
import ru.practicum.android.diploma.common.util.constants.NetworkConst.EMAIL_ADDRESS
import ru.practicum.android.diploma.common.util.constants.NetworkConst.HH_USER_AGENT

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION, "$BEARER ${BuildConfig.HH_ACCESS_TOKEN}")
            .addHeader(HH_USER_AGENT, "$APP_NAME $EMAIL_ADDRESS")
        return chain.proceed(request.build())
    }
}