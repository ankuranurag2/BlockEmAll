package ankuranurag2.blockemall.di

import android.content.Context
import ankuranurag2.blockemall.data.local.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { getAppDataBase(androidApplication()) }
    single { getContactDao(get()) }
}

private fun getAppDataBase(context: Context) = AppDatabase.getInstance(context)

private fun getContactDao(appDatabase: AppDatabase) = appDatabase.getContactDao()