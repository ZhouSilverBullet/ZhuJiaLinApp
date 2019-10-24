package com.sdxxtop.zjlguardian.ui.department

import android.content.Intent
import android.view.View
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

class DepartmentListActivity : BaseActivity<ActivityDepartmentListBinding, DepartmentListViewModel>(), SelectorPopupWindow.DisplayStatusListener, SelectorPopupWindow.SelectedListener {

    val mAdapter by lazy {
        DepartmentListAdapter()
    }

    val pop by lazy {

        val p = SelectorPopupWindow(this)
        p.setDisplayStatusListener(this)
        p.setSelectedListenerListener(this)
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

            showLoadSir(it.isEmpty())

            if (mBinding.srlLayout != null) {
                mBinding.srlLayout.finishRefresh()
            }
        })
    }

    override fun initView() {
        mBinding.rv.layoutManager = LinearLayoutManager(this)
        mBinding.rv.addItemDecoration(ItemDivider().setDividerColor(0xE6E6E6).setDividerWidth(UIUtils.dip2px(10)))
        mBinding.rv.adapter = mAdapter
    }

    override fun loadSirBindView(): View {
        return mBinding.srlLayout
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val eventReportItem = mAdapter.data[position]
            val intent = Intent(view.context, EventReportDetailActivity::class.java)
            if(eventReportItem.type == 3) {
                intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, EventReportDetailActivity.TYPE_EVENT)
                intent.putExtra("eventId", eventReportItem.event_id)
                startActivity(intent)
            } else {
                intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, EventReportDetailActivity.TYPE_COMMISSION)
                intent.putExtra(EventReportDetailActivity.REQUEST_TYPE, eventReportItem.type)
                intent.putExtra("eventId", eventReportItem.event_id)
                startActivity(intent)
            }
        }

        mBinding.stvTitle.tvRight.setOnClickListener {
            startActivity(Intent(this, CommissionDoneActivity::class.java))
        }

        mBinding.rlSelector.setOnClickListener {
            pop.showPop(it)
        }

        mBinding.srlLayout.setEnableLoadMore(false)
        mBinding.srlLayout.setOnRefreshListener {
            //刷新
            mViewModel.loadData(statusType, classificationType, sortType)
        }
    }

    override fun onShow() {
//        ViewCompat.animate(mBinding.ivRank)
//                .rotation(-90f)
//                .setDuration(500)
//                .start()
    }

    override fun onDismiss() {
//        ViewCompat.animate(mBinding.ivRank)
//                .rotation(90f)
//                .setDuration(500)
//                .start()
    }

    var statusType = 0
    var classificationType = 0
    var sortType = 0

    override fun onSelected(statusType: Int, classificationType: Int, sortType: Int) {
        this.statusType = statusType;
        this.classificationType = classificationType;
        this.sortType = sortType;

        mViewModel.loadData(statusType, classificationType, sortType)
    }


    override fun loadData() {
        mViewModel.loadData(0, 0, 0)
    }
}
