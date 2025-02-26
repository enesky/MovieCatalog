package dev.enesky.core.network.interceptor

import dev.enesky.core.network.BuildConfig
import dev.enesky.core.network.api.ApiEndpoints
import okhttp3.Interceptor
import java.util.Locale

/**
 * Interceptor for adding api key and language to the request
 */
class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val language = if (Locale.getDefault().language == ApiEndpoints.DefaultValues.TURKISH) {
            ApiEndpoints.DefaultValues.TURKISH_LANGUAGE
        } else {
            ApiEndpoints.DefaultValues.DEFAULT_LANGUAGE
        }

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(ApiEndpoints.Query.API_KEY, BuildConfig.MOVIE_API_KEY)
            .addQueryParameter(ApiEndpoints.Query.LANGUAGE, language)
            .build()

        val request = original.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}