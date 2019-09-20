package com.sdxxtop.base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.sdxxtop.base.lifecycle.ActivityLifecycleImpl
import com.sdxxtop.base.navigationstatus.INavigationColorStatus
import com.sdxxtop.common.dialog.LoadingDialog
import me.yokeyword.fragmentation.SupportActivity

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-25 15:19
 * Version: 1.0
 * Description:
 */
abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : SupportActivity(), IVMView<VM>, INavigationColorStatus, View.OnClickListener {
    companion object {
        const val TAG = "BaseActivity"
    }

    val mBinding: DB by lazy {
        setContentView<DB>(this, layoutId())
    }

    val mViewModel: VM by lazy {
        ViewModelProviders.of(this@BaseActivity)[vmClazz()]

//        下面这种方式是反射获取的，有时候会比较影响性能
//        ViewModelProviders.of(this@BaseActivity)[getVmClass()]
    }

    val mLoadingDialog by lazy {
        LoadingDialog(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(ActivityLifecycleImpl(this))

        mBinding.lifecycleOwner = this
        bindVM()
        mBinding.executePendingBindings()

        setDarkStatusIcon(window, isDarkStatusIcon())

        initView()
        initSelfObserve()
        initObserve()
        initEvent()
        initData()
        loadData()
    }

    private fun initSelfObserve() {
        mViewModel.mIsLoadingShow.observe(this, Observer {
            if (it) {
                mLoadingDialog.show()
            } else {
                mLoadingDialog.dismiss()
            }
        })
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    /**
     * 不一定有页面一定要重写这个方法
     */
    override fun loadData() {
    }

    override fun onClick(v: View) {
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

    override fun setDarkStatusIcon(window: Window, bDark: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window.decorView
            var vis = decorView.systemUiVisibility
            vis = if (bDark) {
                vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            decorView.systemUiVisibility = vis
        }
    }

    open fun isDarkStatusIcon(): Boolean {
        return false
    }

//    inline fun <reified vm : ViewModel> bindViewModel(): vm {
//        return ViewModelProviders.of(this@BaseActivity)[vm::class.java]
//    }

    //反射获取到baseActivity上面的泛型
//    fun getVmClass(): Class<VM> {
//        return (this@BaseActivity::javaClass.get().genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
//    }

}