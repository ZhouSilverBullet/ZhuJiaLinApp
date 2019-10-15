package com.sdxxtop.zjlguardian.ui.self_handle

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivitySelfHandleBinding
import com.sdxxtop.zjlguardian.ui.event_report.EventReportDetailActivity
import com.sdxxtop.zjlguardian.ui.event_report.EventReportDetailActivity.Companion.TYPE_SELF
import com.sdxxtop.zjlguardian.ui.event_report.EventReportListActivity
import com.sdxxtop.zjlguardian.ui.event_report.data.CatData
import com.sdxxtop.zjlguardian.ui.gridmanagement.fragment.GridManageFragment
import com.sdxxtop.zjlguardian.ui.map.AmapPoiActivity
import com.sdxxtop.zjlguardian.ui.self_handle.viewmodel.SelfHandleViewModel
import com.sdxxtop.zjlguardian.widget.pop.SingleStyleView

class SelfHandleActivity : BaseActivity<ActivitySelfHandleBinding, SelfHandleViewModel>() {
    override fun vmClazz() = SelfHandleViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
        mBinding.click = this
    }

    override fun layoutId() = R.layout.activity_self_handle

    override fun initObserve() {
        mViewModel.mCatClassifyData.observe(this, Observer {
            catList.addAll(it)
        })

        mViewModel.mPushSuccessData.observe(this, Observer {
            val intent = Intent(this@SelfHandleActivity, EventReportDetailActivity::class.java)
            intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, TYPE_SELF)
            intent.putExtra("eventId", it.toInt())
            startActivity(intent)
            finish()
        })
    }

    override fun initView() {
        mBinding.stvTitle.tvRight.setOnClickListener {
            val intent = Intent(this@SelfHandleActivity, EventReportListActivity::class.java)
            intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, TYPE_SELF)
            startActivity(intent)
        }
    }

    override fun initData() {
        super.initData()

        mViewModel.loadClassify(3)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBinding.phsv.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == 10087 && data != null) {
            val address = data.getStringExtra("ad")
            val lt = data.getStringExtra("lt")
            mViewModel.mlonLng.set(lt)
            mViewModel.mEventPlace.set(address)
        }
    }

    override fun onClick(v: View) {
        UIUtils.hideInput(v)
        when (v) {
            mBinding.btnPush -> {
                mViewModel.pushData(mBinding.phsv.imagePushPath)
            }
            mBinding.tcvClassify -> {
                showReportPathSelect()
            }
            mBinding.tcvPlace -> {
                val intent = Intent(this, AmapPoiActivity::class.java)
                startActivityForResult(intent, 100)
            }
            else -> {
            }
        }
    }

    private val queryData by lazy {
        ArrayList<String>()
    }

    val catList = ArrayList<CatData>()

    private val singleReportPathDataView by lazy {
        val styleView = SingleStyleView(this, queryData)
        styleView.setOnItemSelectLintener(object : SingleStyleView.OnItemSelectListener {

            override fun onItemSelect(result: String) {
                mViewModel.mClassifyStr.set(result)
                if (catList.size >= 0) {
                    for (partBean in catList) {
                        if (result == partBean.name) {
                            mViewModel.mClassify.set(partBean.cat_id)
                            return
                        }
                    }
                }
                mViewModel.mClassify.set(0)
            }
        })
        styleView
    }

    private fun showReportPathSelect() {
        if (catList.size == 0) {
            mViewModel.loadClassify(3)
            UIUtils.showToast("数据拉取中...")
            return
        }
        if (queryData.size == 0) {
            catList.forEach {
                queryData.add(it.name)
            }
        }
        singleReportPathDataView.show()
    }
}
