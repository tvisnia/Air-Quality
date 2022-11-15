package pl.wisniewskit.airquality.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import pl.wisniewskit.airquality.data.AirlyStationDataSource
import pl.wisniewskit.airquality.data.airly.AirlyService
import pl.wisniewskit.airquality.data.airly.AirlyEndpoint
import pl.wisniewskit.airquality.data.local.db.AppDatabase
import pl.wisniewskit.airquality.data.local.db.DatabaseStationsRepository
import pl.wisniewskit.airquality.logic.repository.LocalStationsRepository
import pl.wisniewskit.airquality.logic.repository.RemoteStationsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AirQualityProvider {

    @Provides
    @Singleton
    fun provideRemoteStationsRepository(airlyService: AirlyService): RemoteStationsRepository {
        return AirlyStationDataSource(airlyService)
    }

    @Provides
    @Singleton

    fun provideLocalStationsRepository(@ApplicationContext context: Context): LocalStationsRepository {
        val database =
            Room.databaseBuilder(context, AppDatabase::class.java, "AirQualityDb").build()
        return DatabaseStationsRepository(database)
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
            .baseUrl(AirlyEndpoint.HOST)
            .build()
    }

    @Provides
    @Singleton
    fun provideAirlyService(retrofit: Retrofit): AirlyService {
        return retrofit.create(AirlyService::class.java)
    }

}

class AirlyAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("apikey", "dTWGJTxThgqm9yYhE9xOk1xS4zxkYJZs")
        return chain.proceed(requestBuilder.build())
    }
}