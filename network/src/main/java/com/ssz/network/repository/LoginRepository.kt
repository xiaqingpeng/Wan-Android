package com.ssz.network.repository

import com.ssz.network.model.BaseResponse
import com.ssz.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepository {

    fun login(username: String, password: String): Flow<Result<BaseResponse<Any>>> = flow {
        // Mock：任意用户名密码均登录成功
        emit(Result.Success(BaseResponse(data = null, errorCode = 0, errorMsg = "")))
    }

    fun logout(): Flow<Result<BaseResponse<Any>>> = flow {
        emit(Result.Success(BaseResponse(data = null, errorCode = 0, errorMsg = "")))
    }
}