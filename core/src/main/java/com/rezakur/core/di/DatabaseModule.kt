package com.rezakur.core.di

import androidx.room.Room
import com.rezakur.core.BuildConfig
import com.rezakur.core.database.MVExploreDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        val passPhrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.PASS_PHRASE.toCharArray())
        val factory = SupportFactory(passPhrase)
        Room.databaseBuilder(
            androidContext(),
            MVExploreDatabase::class.java, "MVExplore.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
    factory { get<MVExploreDatabase>().favoriteDao() }
}