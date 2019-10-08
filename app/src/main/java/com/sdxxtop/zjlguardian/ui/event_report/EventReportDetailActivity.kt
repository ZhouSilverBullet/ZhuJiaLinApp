package com.sdxxtop.zjlguardian.ui.event_report

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.mapcore.util.it
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityEventReportDetailBinding
import com.sdxxtop.zjlguardian.ui.event_report.adapter.ImageHorizontalAdapter
import com.sdxxtop.zjlguardian.ui.event_report.data.DetailData
import com.sdxxtop.zjlguardian.ui.event_report.viewmodel.EventReportDetailViewModel
import com.sdxxtop.zjlguardian.widget.people_picker.PeoplePickerView
import com.sdxxtop.zjlguardian.widget.people_picker.data.DepartmentData
import com.sdxxtop.zjlguardian.widget.people_picker.data.IPickerData
import com.sdxxtop.zjlguardian.widget.people_picker.data.PeopleData
import kotlinx.android.synthetic.main.activity_event_report_detail.view.*

class EventReportDetailActivity : BaseActivity<ActivityEventReportDetailBinding, EventReportDetailViewModel>() {
    companion object {
        //请求的时候，item里面获取的
        val REQUEST_TYPE = "request_type"
        val KEY_EVENT_TYPE = "type"
        val TYPE_EVENT = 0 //事件处理详情
        val TYPE_SELF = 1 //自行处理
        val TYPE_COMMISSION = 2 //代办
        val TYPE_DEPARTMENT = 3 //部门事件
    }

    var path: String = ""

    val keyEventType by lazy {
        intent.getIntExtra(KEY_EVENT_TYPE, 0)
    }

    val requestType by lazy {
        intent.getIntExtra(REQUEST_TYPE, 0)
    }

    val eventId by lazy {
        intent.getIntExtra("eventId", 0)
    }

    val mAdapter by lazy {
        ImageHorizontalAdapter()
    }

    var mPeoplePickerView: PeoplePickerView? = null

    override fun vmClazz() = EventReportDetailViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun layoutId() = R.layout.activity_event_report_detail

    override fun initObserve() {
        mViewModel.mDetailData.observe(this, Observer {
            handleDetailData(it)
        })

        mViewModel.mSettleSuccess.observe(this, Observer {
            //受理成功，刷新界面
            initData()
        })

        mViewModel.mTransSuccess.observe(this, Observer {
            //流转成功，要结束页面
//            initData()
            finish()
        })

        mViewModel.mFinishSuccess.observe(this, Observer {
            //已完成，刷新界面
            initData()
        })

        mViewModel.mPartListData.observe(this, Observer {
            if (mPeoplePickerView == null) {
                val departmentData = ArrayList<DepartmentData>()
                it.forEach { partData ->
                    //增加部门List
                    val dData = DepartmentData()
                    dData.label = partData.part_name
                    dData.departmentId = partData.part_id
                    departmentData.add(dData)
                    val peopleData = ArrayList<PeopleData>()
                    //增加用户List
                    dData.peopleDataList = peopleData
                    partData.users.forEach { user ->
                        val pData = PeopleData()
                        pData.label = user.username
                        pData.peopleId = user.id
                        peopleData.add(pData)
                    }

                }
                mPeoplePickerView = PeoplePickerView(this, departmentData)
                mPeoplePickerView?.setSelectorListener { departmentData, userData ->
                    mViewModel.eventTrans(eventId, departmentData.id, userData.id)
                }
                mPeoplePickerView?.show()
            }
        })

    }

    private fun handleDetailData(it: DetailData) {
        mBinding.tcvTitle.tvContentText = it.title
        mBinding.tcvClassify.tvContentText = it.cat_name
        mBinding.tcvPlace.tvContentText = it.place
        mBinding.tcvDate.tvContentText = it.settle_time
        mBinding.tvContentText.text = it.content

        //判断图片rv
        if (it.img.isNotEmpty()) {
            mBinding.vLine.visibility = View.VISIBLE
            mBinding.rv.visibility = View.VISIBLE
            mAdapter.replaceData(it.img)
        } else {
            mBinding.vLine.visibility = View.GONE
            mBinding.rv.visibility = View.GONE
        }


        //事件上报人
        mBinding.tcvReportPeople.tvContentText = it.user_name
        mBinding.tcvReportDate.tvContentText = it.add_time
        mBinding.tcvJiejueDate.tvContentText = it.settle_date

        //事件责任人
        mBinding.tcvPeople.tvContentText = it.duty_name
        mBinding.tcvShouliDate.tvContentText = it.settle_time
        mBinding.tcvCompeteDate.tvContentText = it.finish_time

        //回复
        val replyEmpty = it.reply.isEmpty()
        mBinding.llResponseContent.visibility = if (replyEmpty) View.GONE else View.VISIBLE
        if (!replyEmpty) {
            mBinding.tvResponseContentText.text = it.reply[0].desc
            mBinding.tcvCompeteDate.setShowLine(true)
        } else {
            mBinding.tcvCompeteDate.setShowLine(false)
        }

        //处理状态
        // 1:待受理
        //  mBinding.btnShouli
        //  mBinding.btnTurnPeople
        // 2:处理中
        //  mBinding.llNetResponse
        //  mBinding.btnJiejue
        // 3:已完成
        //
        // 4:无法解决
        //
        // status=1： 显示受理按钮、流转按钮
        // status=2： 显示回复框、解决按钮
        // status=3： 已完成
        // status=4： 已完成

        when(keyEventType) {

            //处理事件详情
            TYPE_EVENT -> {
                handleTypeEvent()
            }

            //处理自行处理
            TYPE_SELF -> {
                handleTypeSelf()
            }

            //处理我的代办
            TYPE_COMMISSION -> {
                handleTypeCommission(it.status)
            }

        }


    }

    private fun handleTypeSelf() {
        //事件责任人
        mBinding.llReplay.visibility = View.GONE
        //事件上报人
        mBinding.llReport.visibility = View.GONE
    }

    /**
     * 事件详情
     */
    private fun handleTypeEvent() {
        mBinding.llReport.visibility = View.GONE
    }

    /**
     * 我的代办
     */
    private fun handleTypeCommission(status: Int) {
        mBinding.llReport.visibility = View.VISIBLE
        when (status) {
            1 -> {
                mBinding.btnShouli.visibility = View.VISIBLE
                mBinding.btnTurnPeople.visibility = View.VISIBLE

                mBinding.llNetResponse.visibility = View.GONE
                mBinding.btnJiejue.visibility = View.GONE
            }
            2 -> {

                mBinding.btnShouli.visibility = View.GONE
                mBinding.btnTurnPeople.visibility = View.GONE

                mBinding.llNetResponse.visibility = View.VISIBLE
                mBinding.btnJiejue.visibility = View.VISIBLE
            }
//                3 -> {
//
//                }

            //3、4 先放一块，都是已解决
            else -> {
                mBinding.btnShouli.visibility = View.GONE
                mBinding.btnTurnPeople.visibility = View.GONE

                mBinding.llNetResponse.visibility = View.GONE
                mBinding.btnJiejue.visibility = View.GONE
            }
        }
    }

    override fun initView() {
        mBinding.rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mBinding.rv.adapter = mAdapter

        handleEventType()
    }

    /**
     * 处理
     *   事件详情
     *   自行处理详情
     *   我的代办详情
     */
    private fun handleEventType() {

        when (keyEventType) {
            TYPE_EVENT -> {
                path = "event/details"
                mBinding.stvTitle.setTitleValue("事件详情")
            }
            TYPE_SELF -> {
                path = "eventself/details"
                mBinding.tcvDate.visibility = View.GONE
                mBinding.stvTitle.setTitleValue("自行处理详情")
            }
            TYPE_COMMISSION -> {
                path = "event/todo"
                //（ 1咨询 2投诉 3上报事件 4自行处理）
                when (requestType) {
                    1 -> {
                        mBinding.stvTitle.setTitleValue("咨询详情")
                    }
                    2 -> {
                        mBinding.stvTitle.setTitleValue("投诉详情")
                    }
                    3 -> {
                        mBinding.stvTitle.setTitleValue("事件详情")
                    }
                    4 -> {
                        mBinding.stvTitle.setTitleValue("自行处理详情")
                    }
                }

            }
            else -> {

            }
        }
    }

    override fun initData() {

        mViewModel.loadData(eventId, path, requestType, keyEventType)

    }

    override fun onClick(v: View) {
        when (v) {
            mBinding.btnShouli -> {
                mViewModel.eventSettle(eventId)
            }
            mBinding.btnTurnPeople -> {
                if (mPeoplePickerView == null) {
                    mViewModel.loadPartData(true)
                    return
                }
                mPeoplePickerView?.show()
            }

            mBinding.btnJiejue -> {
                mViewModel.eventFinish(eventId, 3)
            }

            mBinding.btnNotJiejue -> {
                mViewModel.eventFinish(eventId, 4)
            }
        }
    }
}
