package com.onepointsixtwo.github_trending_android.dagger

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.onepointsixtwo.github_trending_android.retrofit.CustomConverterFactory
import com.onepointsixtwo.github_trending_android.retrofit.GitHubApi
import com.onepointsixtwo.github_trending_android.retrofit.GitHubService
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


@Module
class AppModule() {

    @Provides
    fun provideGitHubService(api: GitHubApi): GitHubService {
        return GitHubService(api)
    }

    @Provides
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi {
        return retrofit.create(GitHubApi::class.java)
    }

    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        val builder = OkHttpClient.Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)

        val client = builder.build()

        val converterFactory = CustomConverterFactory(ScalarsConverterFactory.create(),
                GsonConverterFactory.create())

        return Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(converterFactory)
                .build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}