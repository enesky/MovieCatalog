package dev.enesky.core.network.interceptor

import dev.enesky.core.network.BuildConfig
import dev.enesky.core.network.api.ApiEndpoints
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.verify
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import okhttp3.HttpUrl
import org.junit.Ignore
import java.util.Locale

/**
 * Created by Enes Kamil YILMAZ on 27/02/2025
 */
class ApiKeyInterceptorTest {

    private lateinit var interceptor: ApiKeyInterceptor
    private lateinit var chain: Interceptor.Chain
    private lateinit var originalRequest: Request
    private lateinit var originalHttpUrl: HttpUrl
    private lateinit var modifiedHttpUrl: HttpUrl.Builder
    private lateinit var modifiedRequest: Request.Builder
    private lateinit var mockResponse: Response
    private lateinit var mockLocale: Locale

    private val testApiKey = "test_api_key"
    private val defaultLanguage = "en-US"
    private val turkishLanguage = "tr-TR"

    @Before
    fun setup() {
        interceptor = ApiKeyInterceptor()

        chain = mockk()
        originalRequest = mockk()
        originalHttpUrl = mockk()
        modifiedHttpUrl = mockk()
        modifiedRequest = mockk()
        mockResponse = mockk()
        mockLocale = mockk()

        // TODO: Fix this -> mockkStatic(BuildConfig::class)
        every { BuildConfig.MOVIE_API_KEY } returns testApiKey

        mockkObject(ApiEndpoints.DefaultValues)
        every { ApiEndpoints.DefaultValues.DEFAULT_LANGUAGE } returns defaultLanguage
        every { ApiEndpoints.DefaultValues.TURKISH_LANGUAGE } returns turkishLanguage
        every { ApiEndpoints.DefaultValues.TURKISH } returns "tr"

        mockkStatic(Locale::class)
        every { Locale.getDefault() } returns mockLocale

        val urlSlot = slot<HttpUrl>()
        every { originalRequest.url } returns originalHttpUrl
        every { originalHttpUrl.newBuilder() } returns modifiedHttpUrl
        every { modifiedHttpUrl.addQueryParameter(any(), any()) } returns modifiedHttpUrl
        every { modifiedHttpUrl.build() } returns originalHttpUrl

        every { originalRequest.newBuilder() } returns modifiedRequest
        every { modifiedRequest.url(capture(urlSlot)) } returns modifiedRequest
        every { modifiedRequest.build() } returns originalRequest

        every { chain.request() } returns originalRequest
        every { chain.proceed(any()) } returns mockResponse
    }

    @Ignore("Open this after the fixing of BuildConfig mocking")
    @Test
    fun intercept_whenDefaultLanguage_expectEnglishLanguageAdded() {
        // Given
        every { mockLocale.language } returns "en"

        // When
        val result = interceptor.intercept(chain)

        // Then
        verify(exactly = 1) { modifiedHttpUrl.addQueryParameter(ApiEndpoints.Query.API_KEY, testApiKey) }
        verify(exactly = 1) { modifiedHttpUrl.addQueryParameter(ApiEndpoints.Query.LANGUAGE, defaultLanguage) }
        verify(exactly = 1) { chain.proceed(originalRequest) }
        assertEquals(mockResponse, result)
    }

    @Ignore("Open this after the fixing of BuildConfig mocking")
    @Test
    fun intercept_whenTurkishLanguage_expectTurkishLanguageAdded() {
        // Given
        every { mockLocale.language } returns "tr"

        // When
        val result = interceptor.intercept(chain)

        // Then
        verify(exactly = 1) { modifiedHttpUrl.addQueryParameter(ApiEndpoints.Query.API_KEY, testApiKey) }
        verify(exactly = 1) { modifiedHttpUrl.addQueryParameter(ApiEndpoints.Query.LANGUAGE, turkishLanguage) }
        verify(exactly = 1) { chain.proceed(originalRequest) }
        assertEquals(mockResponse, result)
    }

    @Ignore("Open this after the fixing of BuildConfig mocking")
    @Test
    fun intercept_whenOtherLanguage_expectDefaultLanguageAdded() {
        // Given
        every { mockLocale.language } returns "fr"

        // When
        val result = interceptor.intercept(chain)

        // Then
        verify(exactly = 1) { modifiedHttpUrl.addQueryParameter(ApiEndpoints.Query.API_KEY, testApiKey) }
        verify(exactly = 1) { modifiedHttpUrl.addQueryParameter(ApiEndpoints.Query.LANGUAGE, defaultLanguage) }
        verify(exactly = 1) { chain.proceed(originalRequest) }
        assertEquals(mockResponse, result)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}