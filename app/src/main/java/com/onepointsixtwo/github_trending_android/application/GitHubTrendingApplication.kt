package com.onepointsixtwo.github_trending_android.application

import android.app.Activity
import android.app.Application
import com.onepointsixtwo.github_trending_android.dagger.AppComponent
import com.onepointsixtwo.github_trending_android.dagger.AppModule
import com.onepointsixtwo.github_trending_android.dagger.DaggerAppComponent
import com.onepointsixtwo.github_trending_android.dagger.Injector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class GitHubTrendingApplication : Application(), HasActivityInjector {

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .converterModule(AppModule())
                .build()
    }
    val injector = Injector()
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        registerActivityLifecycleCallbacks(injector)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(injector)
    }


    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}