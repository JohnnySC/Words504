package ru.easycode.words504.languages.data.cloud

import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import ru.easycode.words504.data.cloud.ProvideConverterFactory
import ru.easycode.words504.data.cloud.ProvideLoggingInterceptor
import ru.easycode.words504.data.cloud.ProvideOkHttpClientBuilder
import ru.easycode.words504.data.cloud.ProvideRetrofitBuilder
import ru.easycode.words504.languages.domain.HandleDomainError
import ru.easycode.words504.languages.domain.HandleHttpError

// TODO: Remove before the release
class LanguagesServiceCallTest {

    @Test
    @Ignore("Call manually from Android Studio")
    fun `test api call to get list of languages`(): Unit = runBlocking {
        val converterFactory = ProvideConverterFactory.Base()

        val authHeaderInterceptorProvider = AuthHeaderInterceptorProvider()
        val loggingProvider = ProvideLoggingInterceptor.Debug()
        val baseClientBuilder = ProvideOkHttpClientBuilder.AddInterceptor(
            loggingProvider,
            ProvideOkHttpClientBuilder.Base()
        )

        val clientBuilder = ProvideOkHttpClientBuilder.AddInterceptor(
            authHeaderInterceptorProvider,
            baseClientBuilder
        )
        val retrofitBuilder = ProvideRetrofitBuilder.Base(converterFactory, clientBuilder)
        val service = LanguagesMakeService(retrofitBuilder).service(LanguagesService::class.java)
        val dataSource = LanguagesCloudDataSource.Base(
            service,
            errorHandler = HandleDomainError(HandleHttpError())
        )
        val result = try {
            dataSource.languages()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println(result)
    }

    @Test
    @Ignore("Call manually from Android Studio")
    fun `test api call to try to translate`(): Unit = runBlocking {
        val converterFactory = ProvideConverterFactory.Base()

        val authHeaderInterceptorProvider = AuthHeaderInterceptorProvider()
        val loggingProvider = ProvideLoggingInterceptor.Debug()
        val baseClientBuilder = ProvideOkHttpClientBuilder.AddInterceptor(
            loggingProvider,
            ProvideOkHttpClientBuilder.Base()
        )

        val clientBuilder = ProvideOkHttpClientBuilder.AddInterceptor(
            authHeaderInterceptorProvider,
            baseClientBuilder
        )
        val retrofitBuilder = ProvideRetrofitBuilder.Base(converterFactory, clientBuilder)
        val service = LanguagesMakeService(retrofitBuilder).service(TranslateService::class.java)
        val result = service.translate("ru", "hello").execute().body()
        println(result)
    }
}
