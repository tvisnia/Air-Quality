package pl.wisniewskit.airquality.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import pl.wisniewskit.airquality.data.AirlyStationDataSource
import pl.wisniewskit.airquality.logic.RemoteStationsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AirQualityProvider {

    @Provides
    @Singleton
    fun provideRemoteStationsRepository(airlyService: AirlyStationDataSource.AirlyService): RemoteStationsRepository {
        return AirlyStationDataSource(airlyService)
    }

    @Provides
    @Singleton
    fun provideAirlyAuthOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(AirlyAuthInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AirlyStationDataSource.HOST)
            .build()
    }

    @Provides
    @Singleton
    fun provideAirlyService(retrofit: Retrofit): AirlyStationDataSource.AirlyService {
        return retrofit.create(AirlyStationDataSource.AirlyService::class.java)
    }

}

class AirlyAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("apikey", "YOUR_API_KEY")
        return chain.proceed(requestBuilder.build())
    }
}