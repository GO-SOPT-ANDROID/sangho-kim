package org.android.go.sopt.domain.repository

import org.android.go.sopt.domain.model.FollowerListModel

interface FollowerRepository {
    suspend fun getData(): FollowerListModel
}