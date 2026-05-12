package com.ssz.wan.main.mine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssz.network.Result
import com.ssz.network.repository.UserCoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MineUiState {
    object Loading : MineUiState()
    data class Success(
        val coinCount: Int = 0,
        val username: String? = null,
        val nickname: String? = null,
        val userCoinData: com.ssz.network.model.UserCoinData? = null
    ) : MineUiState()
    data class Error(val message: String) : MineUiState()
    object NotLoggedIn : MineUiState()
}

class MineViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserCoinRepository()
    private val _uiState = MutableStateFlow<MineUiState>(MineUiState.Loading)
    val uiState: StateFlow<MineUiState> = _uiState.asStateFlow()

    init {
        loadUserInfo()
    }

    fun loadUserInfo() {
        viewModelScope.launch {
            // Mock 模式：跳过登录状态检查，直接加载用户信息
            _uiState.value = MineUiState.Loading
            repository.getUserCoinInfo().collect { result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.data.data
                        if (data != null) {
                            _uiState.value = MineUiState.Success(
                                coinCount = data.coinCount,
                                username = data.username,
                                nickname = data.nickname,
                                userCoinData = data
                            )
                        } else {
                            _uiState.value = MineUiState.NotLoggedIn
                        }
                    }
                    is Result.Failure -> {
                        _uiState.value = MineUiState.Error(
                            result.exception.message ?: "获取用户信息失败"
                        )
                    }
                }
            }
        }
    }

    fun retry() {
        loadUserInfo()
    }
}

