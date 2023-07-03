package org.android.go.sopt.data.repository

import org.android.go.sopt.data.source.remote.FollowerDataSource
import org.android.go.sopt.domain.model.FollowerModel
import org.android.go.sopt.domain.repository.FollowerRepository
import javax.inject.Inject

class FollowerRepositoryImpl @Inject constructor(
    private val followerDataSource: FollowerDataSource
) : FollowerRepository {
    override suspend fun getData(): List<FollowerModel> {
        val page1List = followerDataSource.getFollowerList(1).toFollowerModel()
        val page2List = followerDataSource.getFollowerList(2).toFollowerModel()
        return page1List + page2List
    }
}