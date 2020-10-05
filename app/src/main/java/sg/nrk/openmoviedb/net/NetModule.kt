package sg.nrk.openmoviedb.net

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object NetModule {

    fun provideMovieService(): MovieService {
        val httpClient = getBasicHttpClientBuilder()
            .addInterceptor(getHttpLogInterceptor())
            .addInterceptor(getApiKeyIntercept())
            .build()

        val retrofitBuilder = getBasicRetrofitBuilder(httpClient)

        return retrofitBuilder.baseUrl("https://www.omdbapi.com/")
            .build()
            .create(MovieService::class.java)

    }

    private fun getBasicHttpClientBuilder() =
        OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)

    private fun getBasicRetrofitBuilder(httpClient: OkHttpClient) =
        Retrofit.Builder().client(httpClient).addConverterFactory(GsonConverterFactory.create())


    private fun getHttpLogInterceptor() =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }


    private fun getApiKeyIntercept() = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var request: Request = chain.request()
            val url = request.url
                .newBuilder()
                .addQueryParameter("apikey", "b9bd48a6").build()
            request = request
                .newBuilder()
                .url(url)
                .build()
            return chain.proceed(request)
        }
    }

}