package com.sdxxtop.zjlguardian.ui.gridmanagement.fragment


import android.content.Context
import android.os.Handler
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kingja.loadsir.core.LoadSir
import com.sdxxtop.base.BaseFragment
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.ui.autotext.AutoTextViewManager
import com.sdxxtop.ui.autotext.IAutoValue
import com.sdxxtop.ui.loadsir.ErrorCallback
import com.sdxxtop.zjlguardian.databinding.FragmentGridManageBinding
import com.sdxxtop.zjlguardian.ui.gridmanagement.adapter.GridManageAdapter
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.GridManagerData
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.ITEM_CELL
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.ITEM_EMPTY_LINE
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.ITEM_MORE
import com.sdxxtop.zjlguardian.ui.gridmanagement.viewmodel.GridManageViewModel
import com.youth.banner.loader.ImageLoader


/**
 * A simple [Fragment] subclass.
 */
class GridManageFragment : BaseFragment<FragmentGridManageBinding, GridManageViewModel>() {
    val mAdapter = GridManageAdapter()

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun vmClazz() = GridManageViewModel::class.java

    override fun layoutId() = com.sdxxtop.zjlguardian.R.layout.fragment_grid_manage

    override fun initObserve() {
    }

    override fun initView() {
        mBinding.bView.setImageLoader(GlideImageLoader())
        val arrayList = ArrayList<Int>()
        arrayList.add(com.sdxxtop.zjlguardian.R.drawable.test0)
        arrayList.add(com.sdxxtop.zjlguardian.R.drawable.test1)
        arrayList.add(com.sdxxtop.zjlguardian.R.drawable.test2)
        arrayList.add(com.sdxxtop.zjlguardian.R.drawable.test3)

        mBinding.bView.setImages(arrayList)
        mBinding.bView.start()


        val autoTextViewManager = AutoTextViewManager(mBinding.atvView)
        val autoValueList = ArrayList<IAutoValue>()
        autoValueList.add(AutoValue("你好111111111"))
        autoValueList.add(AutoValue("你好222222222"))
        autoValueList.add(AutoValue("你好3333333333"))
        autoValueList.add(AutoValue("你好55555555555555555555555555555555555555555555555555555555"))
        autoTextViewManager.setData(autoValueList)
        autoTextViewManager.start()

        mBinding.rv.layoutManager = LinearLayoutManager(activity)
        mBinding.rv.adapter = mAdapter

        var gridManagerData1 = GridManagerData(ITEM_MORE)
        var gridManagerData2 = GridManagerData(ITEM_CELL)
        var gridManagerData3 = GridManagerData(ITEM_CELL)
        var gridManagerData4 = GridManagerData(ITEM_EMPTY_LINE)

        var gridManagerData5 = GridManagerData(ITEM_MORE)
        var gridManagerData6 = GridManagerData(ITEM_CELL)
        var gridManagerData9 = GridManagerData(ITEM_EMPTY_LINE)

        var gridManagerData7 = GridManagerData(ITEM_MORE)
        var gridManagerData8 = GridManagerData(ITEM_CELL)
        var gridManagerData10 = GridManagerData(ITEM_EMPTY_LINE)

        var list = ArrayList<GridManagerData>()
        list.add(gridManagerData1)
        list.add(gridManagerData2)
        list.add(gridManagerData3)
        list.add(gridManagerData4)
        list.add(gridManagerData5)
        list.add(gridManagerData6)
        list.add(gridManagerData9)
        list.add(gridManagerData7)
        list.add(gridManagerData8)
        list.add(gridManagerData10)

        mAdapter.addData(list)
    }

    override fun initData() {
    }

    override fun loadData() {
        mLoadService.showSuccess()
    }

    override fun preLoad() {
        UIUtils.showToast("点击重新加载")
    }

    class GlideImageLoader : ImageLoader() {

        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {


            val requestOptions = RequestOptions()
            requestOptions.placeholder(com.sdxxtop.zjlguardian.R.drawable.placeholder_icon) //设置“加载中”状态时显示的图片
                    .error(com.sdxxtop.zjlguardian.R.drawable.error_icon) //设置“加载失败”状态时显示的图片
            Glide.with(context!!).load(path as Int).apply(requestOptions).into(imageView!!)


//            imageView?.setImageResource(path as Int)
//            GlideUtil.displayImage(context!!, path as String, imageView!!)
//            Glide.with(context!!).load(path.image).into(imageView!!)
//            if (path is Img) {
//                Glide.with(context!!).load(path.image).into(imageView!!)
//            }
        }
    }

    class AutoValue(val value: String) : IAutoValue {
        override fun getTitle(): String {
            return value
        }

    }

}
