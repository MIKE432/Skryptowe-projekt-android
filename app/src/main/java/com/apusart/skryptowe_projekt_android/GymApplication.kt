package com.apusart.skryptowe_projekt_android

import android.app.Application
import android.content.Context
import com.apusart.skryptowe_projekt_android.di.AppModule
import com.apusart.skryptowe_projekt_android.ui.guest.initial_activity.InitialActivity
import com.apusart.skryptowe_projekt_android.ui.guest.login_activity.LoginActivity
import com.apusart.skryptowe_projekt_android.ui.guest.register_activity.RegisterActivity
import com.apusart.skryptowe_projekt_android.ui.logged.add_training.add_training_process.AddTrainingAddSeriesFragment
import com.apusart.skryptowe_projekt_android.ui.logged.add_training.add_training_process.AddTrainingDetailsFragment
import com.apusart.skryptowe_projekt_android.ui.logged.add_training.add_training_process.AddTrainingProcessActivity
import com.apusart.skryptowe_projekt_android.ui.logged.profile.ProfileFragment
import com.apusart.skryptowe_projekt_android.ui.logged.search.SearchFragment
import com.apusart.skryptowe_projekt_android.ui.logged.trainings.TrainingsFragment
import com.apusart.skryptowe_projekt_android.ui.logged.trainings.training_details.TrainingDetails
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {
    fun inject(activity: InitialActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: RegisterActivity)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: TrainingsFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: TrainingDetails)
    fun inject(fragment: AddTrainingProcessActivity)
    fun inject(fragment: AddTrainingDetailsFragment)
    fun inject(fragment: AddTrainingAddSeriesFragment)



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