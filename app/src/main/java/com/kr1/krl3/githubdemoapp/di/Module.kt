package com.kr1.krl3.githubdemoapp.di

import android.app.Application
import androidx.room.Room
import com.kr1.krl3.data.CommitsRepositoryImpl
import com.kr1.krl3.data.RepoRepositoryImpl
import com.kr1.krl3.data.UserRepositoryImpl
import com.kr1.krl3.domain.repository.RepoRepository
import com.kr1.krl3.domain.repository.UserRepository
import com.kr1.krl3.domain.usecase.GetReposAndCommits
import com.kr1.krl3.domain.usecase.GetUser
import com.kr1.krl3.githubdemoapp.datasource.local.AppDatabase
import com.kr1.krl3.githubdemoapp.datasource.local.LocalDataSourceImpl
import com.kr1.krl3.githubdemoapp.datasource.network.NetworkDataSourceImpl
import com.kr1.krl3.githubdemoapp.datasource.network.NetworkHandler
import com.kr1.krl3.githubdemoapp.datasource.network.RestApi
import com.kr1.krl3.githubdemoapp.viewmodel.ReposViewModel
import com.kr1.krl3.githubdemoapp.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { provideAppDatabase(androidApplication()) }
    single { RestApi() }
    single { NetworkHandler(androidApplication()) }

    viewModel { UserViewModel(get()) }
    viewModel { ReposViewModel(get()) }

    factory { GetUser(provideUserRepository(get(), get(), get())) }
    factory { GetReposAndCommits(provideRepoRepository(get(), get(), get())) }

}

private fun provideAppDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "app_database.db"
    ).fallbackToDestructiveMigration().build()
}

private fun provideUserRepository(
    appDatabase: AppDatabase,
    restApi: RestApi,
    networkHandler: NetworkHandler
): UserRepository =
    UserRepositoryImpl(
        LocalDataSourceImpl(appDatabase),
        NetworkDataSourceImpl(restApi, networkHandler)
    )

private fun provideRepoRepository(
    appDatabase: AppDatabase,
    restApi: RestApi,
    networkHandler: NetworkHandler
): RepoRepository {
    val localDataSource = LocalDataSourceImpl(appDatabase)
    val networkDataSource = NetworkDataSourceImpl(restApi, networkHandler)
    return RepoRepositoryImpl(
        localDataSource,
        networkDataSource,
        CommitsRepositoryImpl(localDataSource, networkDataSource)
    )
}