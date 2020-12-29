package ankuranurag2.blockemall

import android.app.Application
import ankuranurag2.blockemall.di.databaseModule
import ankuranurag2.blockemall.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BlockEmAllApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@BlockEmAllApp)
            modules(listOf(databaseModule, mainModule))
        }
    }
}