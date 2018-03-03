package com.onepointsixtwo.github_trending_android.helpers

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * A scheduler for testing to avoid having to test async - it simply fires immediately
 * upon having a runnable scheduled to it
 */
class TestScheduler: Scheduler() {

    override fun createWorker(): Worker {
        return TestWorker()
    }
}

class TestWorker: Scheduler.Worker() {

    override fun isDisposed(): Boolean {
        return false
    }

    override fun schedule(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
        run.run()
        return EmptyDisposable()
    }

    override fun dispose() {}
}

class EmptyDisposable: Disposable {

    override fun isDisposed(): Boolean {
        return false
    }

    override fun dispose() {}
}