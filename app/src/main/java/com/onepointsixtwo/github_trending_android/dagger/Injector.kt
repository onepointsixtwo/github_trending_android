package com.onepointsixtwo.github_trending_android.dagger

import android.app.Activity
import android.app.Application
import android.os.Bundle
import dagger.android.AndroidInjection


class Injector: Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        // Inject the newly created activity
        AndroidInjection.inject(activity)
    }

    override fun onActivityStarted(p0: Activity?) {}

    override fun onActivityResumed(p0: Activity?) {}

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {}

    override fun onActivityPaused(p0: Activity?) {}

    override fun onActivityStopped(p0: Activity?) {}

    override fun onActivityDestroyed(p0: Activity?) {}
}