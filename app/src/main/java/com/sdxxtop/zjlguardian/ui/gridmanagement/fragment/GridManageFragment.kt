package com.sdxxtop.zjlguardian.ui.gridmanagement.fragment


import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sdxxtop.base.BaseFragment
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.ui.autotext.AutoTextViewManager
import com.sdxxtop.ui.autotext.IAutoValue
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.FragmentGridManageBinding
import com.sdxxtop.zjlguardian.ui.event_report.EventReportActivity
import com.sdxxtop.zjlguardian.ui.gridmanagement.adapter.GridManageAdapter
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.GridManagerData
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.ITEM_CELL
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.ITEM_EMPTY_LINE
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.ITEM_MORE
import com.sdxxtop.zjlguardian.ui.gridmanagement.viewmodel.GridManageViewModel
import com.sdxxtop.zjlguardian.ui.message.MessageActivity
import com.sdxxtop.zjlguardian.ui.message.MessageDetailActivity
import com.sdxxtop.zjlguardian.ui.self_handle.SelfHandleActivity
import com.youth.banner.loader.ImageLoader


/**
 * A simple [Fragment] subclass.
 */
class GridManageFragment : BaseFragment<FragmentGridManageBinding, GridManageViewModel>() {
    var isBannerLoad = false

    val mAdapter = GridManageAdapter()

    override fun bindVM() {
        mBinding.vm = mViewModel
        mBinding.click = this
    }

    override fun vmClazz() = GridManageViewModel::class.java

    override fun layoutId() = R.layout.fragment_grid_manage

    override fun initObserve() {
        mViewModel.mGridManagerData.observe(this, Observer {
            mAdapter.replaceData(it)
        })

        mViewModel.mGruadeEntryData.observe(this, Observer {
            //轮播图
            if (!isBannerLoad) {
                mBinding.bView.setImages(it.broadcast)
                mBinding.bView.start()
                isBannerLoad = true
            }

            mBinding.tvMessage.text = it.message.title
            val messageId = it.message.message_id
            mBinding.llNotice.setOnClickListener { view
                val intent = Intent(view?.context, MessageDetailActivity::class.java)
                intent.putExtra("messageId", messageId)
                startActivity(intent)
            }

//            mBinding.llNotice.visibility = if (TextUtils.isEmpty(it.message.title)) View.GONE else View.VISIBLE

            mLoadService.showSuccess()
//            val autoTextViewManager = AutoTextViewManager(mBinding.atvView)
//            val autoValueList = ArrayList<IAutoValue>()
//
//            autoValueList.add(AutoValue("你好111111111"))
//            autoTextViewManager.setData(autoValueList)
//            autoTextViewManager.start()
        })
    }

    override fun initView() {
        mBinding.bView.setImageLoader(GlideImageLoader())
//        val arrayList = ArrayList<Int>()
//        arrayList.add(R.drawable.test0)
//        arrayList.add(R.drawable.test1)
//        arrayList.add(R.drawable.test2)
//        arrayList.add(R.drawable.test3)


//        val autoTextViewManager = AutoTextViewManager(mBinding.atvView)
//        val autoValueList = ArrayList<IAutoValue>()
//        autoValueList.add(AutoValue("你好111111111"))
//        autoValueList.add(AutoValue("你好222222222"))
//        autoValueList.add(AutoValue("你好3333333333"))
//        autoValueList.add(AutoValue("你好55555555555555555555555555555555555555555555555555555555"))
//        autoTextViewManager.setData(autoValueList)
//        autoTextViewManager.start()

        mBinding.rv.layoutManager = LinearLayoutManager(activity)
        mBinding.rv.adapter = mAdapter

//        var gridManagerData1 = GridManagerData(ITEM_MORE)
//        var gridManagerData2 = GridManagerData(ITEM_CELL)
//        var gridManagerData3 = GridManagerData(ITEM_CELL)
//        var gridManagerData4 = GridManagerData(ITEM_EMPTY_LINE)
//
//        var gridManagerData5 = GridManagerData(ITEM_MORE)
//        var gridManagerData6 = GridManagerData(ITEM_CELL)
//        var gridManagerData9 = GridManagerData(ITEM_EMPTY_LINE)
//
//        var gridManagerData7 = GridManagerData(ITEM_MORE)
//        var gridManagerData8 = GridManagerData(ITEM_CELL)
//        var gridManagerData10 = GridManagerData(ITEM_EMPTY_LINE)
//
//        var list = ArrayList<GridManagerData>()
//        list.add(gridManagerData1)
//        list.add(gridManagerData2)
//        list.add(gridManagerData3)
//        list.add(gridManagerData4)
//        list.add(gridManagerData5)
//        list.add(gridManagerData6)
//        list.add(gridManagerData9)
//        list.add(gridManagerData7)
//        list.add(gridManagerData8)
//        list.add(gridManagerData10)


    }

    override fun initData() {
    }

    override fun initEvent() {
        mBinding.stvTitle.ivRight.setOnClickListener {
            val intent = Intent(activity, MessageActivity::class.java)
            startActivity(intent)
        }
    }

    override fun loadData() {
        mViewModel.loadData()
    }

    override fun preLoad() {
        UIUtils.showToast("点击重新加载")
    }

    class GlideImageLoader : ImageLoader() {

        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {


            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.placeholder_icon) //设置“加载中”状态时显示的图片
                    .error(R.drawable.error_icon) //设置“加载失败”状态时显示的图片
            if (path is String) {
                Glide.with(context!!).load(path).apply(requestOptions).into(imageView!!)
            } else {
                Glide.with(context!!).load(R.drawable.error_icon).apply(requestOptions).into(imageView!!)
            }

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

//    val peoplePickerView by lazy {
//        val departmentData = ArrayList<DepartmentData>()
//        var dData = DepartmentData()
//        dData.setLabel("部门A")
//        departmentData.add(dData)
//        dData = DepartmentData()
//        dData.setLabel("部门B")
//        departmentData.add(dData)
//        dData = DepartmentData()
//        dData.setLabel("部门C")
//        departmentData.add(dData)
//        dData = DepartmentData()
//        dData.setLabel("部门D")
//        departmentData.add(dData)
//        dData = DepartmentData()
//        dData.setLabel("部门E")
//        departmentData.add(dData)
//        dData = DepartmentData()
//        dData.setLabel("部门F")
//        departmentData.add(dData)
//        dData = DepartmentData()
//        dData.setLabel("部门F")
//        departmentData.add(dData)
//        dData = DepartmentData()
//        dData.setLabel("部门F")
//        departmentData.add(dData)
//        dData = DepartmentData()
//        dData.setLabel("部门F")
//        departmentData.add(dData)
//        dData = DepartmentData()
//        dData.setLabel("部门F")
//        departmentData.add(dData)
//
//
//        val peopleData = ArrayList<PeopleData>()
//        var pData = PeopleData()
//        pData.setLabel("用户1")
//        peopleData.add(pData)
//
//        pData = PeopleData()
//        pData.setLabel("用户2")
//        peopleData.add(pData)
//        pData = PeopleData()
//        pData.setLabel("用户3")
//        peopleData.add(pData)
//        pData = PeopleData()
//        pData.setLabel("用户4")
//        peopleData.add(pData)
//        pData = PeopleData()
//        pData.setLabel("用户5")
//        peopleData.add(pData)
//        for (i in departmentData.indices) {
//            departmentData.get(i).setPeopleDataList(ArrayList(peopleData))
//        }
//
//        dData = DepartmentData()
//        dData.setLabel("部门F")
//        departmentData.add(dData)
//
//        val p = PeoplePickerView(activity!!, departmentData)
//        p
//    }

    override fun onClick(v: View) {
        when (v) {
            mBinding.btnReport -> {
                startActivity(Intent(activity, EventReportActivity::class.java))
//                peoplePickerView.show()
            }
            mBinding.btnSelfHandle -> {
                startActivity(Intent(activity, SelfHandleActivity::class.java))
            }
        }
    }
}
