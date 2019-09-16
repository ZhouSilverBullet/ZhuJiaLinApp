package com.sdxxtop.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-25 18:29
 * Version: 1.0
 * Description:
 */
abstract class BaseFragment<DB : ViewDataBinding, VM : ViewModel> : Fragment(), IVMView<VM> {
    companion object {
        const val TAG = "BaseFragment"
    }

    lateinit var mBinding: DB

    val mViewModel: VM by lazy {
        ViewModelProviders.of(this@BaseFragment)[vmClazz()]

//        下面这种方式是反射获取的，有时候会比较影响性能
//        ViewModelProviders.of(this@BaseActivity)[getVmClass()]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate<DB>(inflater, layoutId(), container, false)
        mBinding.lifecycleOwner = this

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindVM()
        mBinding.executePendingBindings()

        initView()
        initObserve()
        initEvent()
        initData()
    }

    override fun bindVM() {
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

    /**
     * 不一定有页面一定要重写这个方法
     */
    override fun loadData() {
    }

}