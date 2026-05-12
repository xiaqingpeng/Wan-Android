package com.ssz.network.repository

import com.ssz.network.model.BaseResponse
import com.ssz.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterRepository {

    fun register(username: String, password: String, repassword: String): Flow<Result<BaseResponse<Any>>> = flow {
        // Mock：注册始终成功
        emit(Result.Success(BaseResponse(data = null, errorCode = 0, errorMsg = "")))
    }
}