package com.apusart.skryptowe_projekt_android

import android.app.Application
import android.content.Context
import com.apusart.skryptowe_projekt_android.di.AppModule
import com.apusart.skryptowe_projekt_android.ui.guest.initial_activity.InitialActivity
import com.apusart.skryptowe_projekt_android.ui.guest.login_activity.LoginActivity
import com.apusart.skryptowe_projekt_android.ui.guest.register_activity.RegisterActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {
    fun inject(activity: InitialActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: RegisterActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: GymApplication): Builder

        fun build(): ApplicationComponent
    }
}
class GymApplication: Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
    }
}

val Context.appComponent get() = (applicationContext as GymApplication).appComponent