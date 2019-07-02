package com.example.cheesecakenews

import android.content.Context
import com.example.cheesecakenews.api.NetworkModule
import com.example.cheesecakenews.view.NewsNotice.NewsNoticeActivity
import com.example.cheesecakenews.view.news.NewsActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton


@Module(includes = [
    NetworkModule::class,
    SchedulerModule::class
])
class AppModule

@Module
abstract class AndroidInjectorsModule {
    @ContributesAndroidInjector
    abstract fun newsActivity(): NewsActivity

    @ContributesAndroidInjector
    abstract fun newsNoticeActivity(): NewsNoticeActivity
}

@Singleton
@Component(modules = arrayOf(
    AndroidInjectionModule::class,
    AppModule::class,
    AndroidInjectorsModule::class

))
interface AppComponent : AndroidInjector<MyApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApp>() {
        @BindsInstance
        abstract fun appContext(appContext: Context): Builder
    }
}