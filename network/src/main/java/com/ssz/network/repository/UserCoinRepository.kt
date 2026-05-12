package com.ssz.network.repository

import com.ssz.network.model.BaseResponse
import com.ssz.network.Result
import com.ssz.network.model.UserCoinData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserCoinRepository {

    private val mockUserCoinData = UserCoinData(
        coinCount = 2048,
        level = 8,
        nickname = "Android 开发者",
        rank = "88",
        userId = 10001,
        username = "mock_user"
    )

    fun getUserCoinInfo(): Flow<Result<BaseResponse<UserCoinData>>> = flow {
        emit(Result.Success(BaseResponse(data = mockUserCoinData, errorCode = 0, errorMsg = "")))
    }
}