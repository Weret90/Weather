package corp.umbrella.weather

import android.app.Application
import corp.umbrella.weather.di.DataDi
import corp.umbrella.weather.di.DomainDi
import corp.umbrella.weather.di.PresentationDi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                DataDi.localModule,
                DataDi.remoteModule,
                DataDi.repositoryModule,
                DomainDi.useCasesModule,
                PresentationDi.viewModelsModule
            )
        }
    }
}