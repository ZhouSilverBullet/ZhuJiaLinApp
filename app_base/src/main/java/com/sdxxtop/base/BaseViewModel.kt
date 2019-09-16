package com.sdxxtop.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdxxtop.network.helper.data.BaseResponse
import com.sdxxtop.network.load.ILoadData
import com.sdxxtop.network.load.LoadDataImpl
import kotlinx.coroutines.CoroutineScope

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-24 20:01
 * Version: 1.0
 * Description:
 */
abstract class BaseViewModel : ViewModel() {
    val mThrowable = MutableLiveData<Throwable>()
    //抽取了LoadData
    private val loadData: ILoadData = LoadDataImpl(BaseApplication.INSTANCE, viewModelScope)


    fun <T> loadCatchOnUI(block: suspend CoroutineScope.() -> BaseResponse<T>,
                          successBlock: (T) -> Unit,
                          failBlock: (code: Int, msg: String, t: Throwable) -> Unit,
                          catchBack: suspend CoroutineScope.(t: Throwable) -> Unit = {},
                          finallyBack: suspend CoroutineScope.() -> Unit = {}) {
        loadData.loadCatchOnUI(block, successBlock, failBlock, catchBack, finallyBack)
    }


    fun <T> loadOnUI(block: suspend CoroutineScope.() -> BaseResponse<T>,
                     successBlock: (T) -> Unit,
            //空实现带参方法
                     failBlock: (code: Int, msg: String, t: Throwable) -> Unit = { code, msg, t -> },
                     finallyBack: suspend CoroutineScope.() -> Unit = {}) {
        loadData.loadOnUI(block, successBlock, failBlock, finallyBack)
    }


    fun <T> loadBaseOnUI(block: suspend CoroutineScope.() -> BaseResponse<T>,
                         successBlock: (BaseResponse<T>) -> Unit,
                         failBlock: (code: Int, msg: String, t: Throwable) -> Unit,
                         finallyBack: suspend CoroutineScope.() -> Unit = {}) {
        loadData.loadBaseOnUI(block, successBlock, failBlock, finallyBack)
    }
}