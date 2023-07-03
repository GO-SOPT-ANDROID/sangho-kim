package org.android.go.sopt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.go.sopt.data.source.remote.FollowerDataSource
import org.android.go.sopt.data.source.remote.FollowerDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindFollowerDataSource(
        followerDataSourceImpl: FollowerDataSourceImpl
    ): FollowerDataSource
}