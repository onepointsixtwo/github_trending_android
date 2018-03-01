package com.onepointsixtwo.github_trending_android.dagger

import com.onepointsixtwo.github_trending_android.application.GitHubTrendingApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class,
        Contributors::class,
        AppModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun converterModule(module: AppModule) : Builder
        fun build() : AppComponent
    }

    fun inject(app: GitHubTrendingApplication)
}