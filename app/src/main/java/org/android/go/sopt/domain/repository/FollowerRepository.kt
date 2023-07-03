package org.android.go.sopt.domain.repository

import org.android.go.sopt.domain.model.FollowerModel

interface FollowerRepository {
    suspend fun getData(): List<FollowerModel>
}