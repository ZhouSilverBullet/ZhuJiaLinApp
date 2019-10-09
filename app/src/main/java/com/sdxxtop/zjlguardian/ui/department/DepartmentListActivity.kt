package com.sdxxtop.zjlguardian.ui.department

import android.content.Intent
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.mapcore.util.it
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.common.utils.ItemDivider
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityDepartmentListBinding
import com.sdxxtop.zjlguardian.ui.commission.CommissionDoneActivity
import com.sdxxtop.zjlguardian.ui.commission.adapter.CommissionListAdapter
import com.sdxxtop.zjlguardian.ui.department.adapter.DepartmentListAdapter
import com.sdxxtop.zjlguardian.ui.department.viewmodel.DepartmentListViewModel
import com.sdxxtop.zjlguardian.ui.department.widget.SelectorPopupWindow
import com.sdxxtop.zjlguardian.ui.event_report.EventReportDetailActivity

class DepartmentListActivity : BaseActivity<ActivityDepartmentListBinding, DepartmentListViewModel>(), SelectorPopupWindow.DisplayStatusListener {
    val mAdapter by lazy {
        DepartmentListAdapter()
    }

    val pop by lazy {

        val p = SelectorPopupWindow(this)
        p.setDisplayStatusListener(this)
        p
    }

    override fun vmClazz() = DepartmentListViewModel::class.java

    override fun layoutId() = R.layout.activity_department_list

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun initObserve() {
        mViewModel.mDepartmentList.observe(this, Observer {
            mAdapter.replaceData(it)
        })
    }

    override fun initView() {
        mBinding.rv.layoutManager = LinearLayoutManager(this)
        mBinding.rv.addItemDecoration(ItemDivider().setDividerColor(0xE6E6E6).setDividerWidth(UIUtils.dip2px(10)))
        mBinding.rv.adapter = mAdapter
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val eventReportItem = mAdapter.data[position]
            val intent = Intent(view.context, EventReportDetailActivity::class.java)
            intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, EventReportDetailActivity.TYPE_EVENT)
            intent.putExtra("eventId", eventReportItem.event_id)
            startActivity(intent)
        }

        mBinding.stvTitle.tvRight.setOnClickListener {
            startActivity(Intent(this, CommissionDoneActivity::class.java))
        }

        mBinding.rlSelector.setOnClickListener {
            pop.showPop(it)
        }
    }

    override fun onShow() {
        ViewCompat.animate(mBinding.ivRank).rotation(90f)
                .setDuration(500)
                .start()
    }

    override fun onDismiss() {
        ViewCompat.animate(mBinding.ivRank).rotation(270f)
                .setDuration(500)
                .start()
    }


    override fun loadData() {
        mViewModel.loadData()
    }
}
