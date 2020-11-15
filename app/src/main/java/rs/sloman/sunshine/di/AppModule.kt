package rs.sloman.sunshine.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rs.sloman.sunshine.util.Constants
import rs.sloman.sunshine.network.WeatherApi
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
/**Application level Dagger-Hilt Module in charge of providing in-time needed objects.*/
object AppModule {

    /**Function that returns a singleton WeatherApi*/
    @Singleton
    @Provides
    fun provideCryptoApi(): WeatherApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(WeatherApi::class.java)

}