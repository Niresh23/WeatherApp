package com.nik.weather_app.di;

import com.nik.weather_app.base.Consts;
import com.nik.weather_app.rest.OpenWeatherAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;

import dagger.hilt.components.SingletonComponent;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class NetworkModule {

    @Singleton
    @AuthInterceptorOkHttpClient
    @Provides
    public static OkHttpClient provideAuthClient() {
        HttpLoggingInterceptor interceptor =  new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(
                chain -> {
                    Request original = chain.request();
                    HttpUrl originalUrl = original.url();

                    HttpUrl url = originalUrl.newBuilder()
                            .addQueryParameter(
                                    "appid", Consts.API_KEY
                            ).build();
                    Request.Builder requestBuilder = original.newBuilder().url(url);
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
        ).addInterceptor(interceptor).build();
    }

    @Singleton
    @Provides
    public static Retrofit provideAuthRetrofit(
            @AuthInterceptorOkHttpClient OkHttpClient client
    ) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Consts.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public static OpenWeatherAPI provideWeatherAPI(
            Retrofit retrofit
    ) {
        return retrofit.create(OpenWeatherAPI.class);
    }
}
