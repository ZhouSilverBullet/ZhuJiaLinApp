package com.sdxxtop.zjlguardian.ui.event_report

import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityEventReportDetailBinding
import com.sdxxtop.zjlguardian.ui.event_report.adapter.ImageHorizontalAdapter
import com.sdxxtop.zjlguardian.ui.event_report.data.DetailData
import com.sdxxtop.zjlguardian.ui.event_report.viewmodel.EventReportDetailViewModel
import com.sdxxtop.zjlguardian.widget.people_picker.PeoplePickerView
import com.sdxxtop.zjlguardian.widget.people_picker.data.DepartmentData
import com.sdxxtop.zjlguardian.widget.people_picker.data.PeopleData
import kotlinx.android.synthetic.main.activity_event_report_detail.view.*

class EventReportDetailActivity : BaseActivity<ActivityEventReportDetailBinding, EventReportDetailViewModel>() {
    companion object {
        //请求的时候，item里面获取的
        val REQUEST_TYPE = "request_type"
        val KEY_EVENT_TYPE = "type"
        val TYPE_EVENT = 0 //事件处理详情
        val TYPE_SELF = 1 //自行处理
        val TYPE_COMMISSION = 2 //待办
        val TYPE_DEPARTMENT = 3 //部门事件

        val EMPTY_TIME = "1000-01-01"

        //是个boolean值的参数
        //如果是真正的commission过来的，就按钮和回复
        val IS_COMMISSION = "is_commission"
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

    val isCommission by lazy {
        intent.getBooleanExtra(IS_COMMISSION, false)
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

            showLoadSir(it == null)
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
        mBinding.tcvDate.tvContentText = it.settle_date
        mBinding.tvContentText.text = it.content

        //判断图片rv
        if (it.img != null && it.img.isNotEmpty()) {
            mBinding.vLine.visibility = View.VISIBLE
            mBinding.rv.visibility = View.VISIBLE
            mBinding.llPhotoTitle.visibility = View.VISIBLE
            mAdapter.replaceData(it.img)
        } else {
            mBinding.vLine.visibility = View.GONE
            mBinding.rv.visibility = View.GONE
            mBinding.llPhotoTitle.visibility = View.GONE
        }


        //事件上报人
        mBinding.tcvReportPeople.tvContentText = it.user_name
        mBinding.tcvReportDate.tvContentText = it.add_time
        mBinding.tcvJiejueDate.tvContentText = it.settle_date

        //事件责任人
        mBinding.tcvPeople.tvContentText = it.duty_name

        if (EMPTY_TIME == it.settle_time || TextUtils.isEmpty(it.settle_time)) {
            mBinding.tcvShouliDate.tvContentText = "待受理"
        } else {
            mBinding.tcvShouliDate.tvContentText = it.settle_time
        }

        if (EMPTY_TIME == it.finish_time || TextUtils.isEmpty(it.finish_time)) {
            mBinding.tcvCompeteDate.tvContentText = "未完成"
        } else {
            mBinding.tcvCompeteDate.tvContentText = it.finish_time
        }

        //回复
        //当为自行处理的时候，这个会出现空指针异常，所以要判断一下
        if (it.reply != null) {
            val replyEmpty = it.reply.isEmpty()
            mBinding.llResponseContent.visibility = if (replyEmpty) View.GONE else View.VISIBLE
            if (!replyEmpty) {
                mBinding.tvResponseContentText.text = it.reply[0].desc
                mBinding.tcvCompeteDate.setShowLine(true)
            } else {
                mBinding.tcvCompeteDate.setShowLine(false)
            }
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

        when (keyEventType) {

            //处理事件详情
            TYPE_EVENT -> {
                handleTypeEvent()
            }

            //处理自行处理
            TYPE_SELF -> {
                handleTypeSelf()
            }

            //处理我的待办
            TYPE_COMMISSION -> {
                handleRequestType(it)

                if (isCommission) {
                    handleTypeCommission(it.status)
                }
            }

        }


    }

    private fun handleRequestType(it: DetailData) {
        when (requestType) {
            1 -> {
//                mBinding.stvTitle.setTitleValue("咨询详情")
                mBinding.tcvTitle.setEditNameText("标题：")
                mBinding.tcvTitle.tvContentText = it.title
                mBinding.tcvClassify.setEditNameText("姓名：")
                mBinding.tcvClassify.tvContentText = it.name

                mBinding.tcvPlace.setEditNameText("手机号码：")
                mBinding.tcvPlace.tvContentText = it.mobile

                mBinding.tcvDate.visibility = View.GONE
                mBinding.tvContentTitle.text = "咨询内容："
                mBinding.tvContentText.text = it.desc

                mBinding.llReport.visibility = View.GONE

                mBinding.tvDutyPeopleDec.text = "咨询受理"

                mBinding.tcvPeople.setEditNameText("提交日期：")
                mBinding.tcvPeople.tvContentText = it.add_time

                mBinding.tcvShouliDate.setEditNameText("受理日期：")
                mBinding.tcvShouliDate.tvContentText = it.settle_time

                mBinding.tcvCompeteDate.setEditNameText("回复日期：")
                mBinding.tcvCompeteDate.tvContentText = it.finish_time

                mBinding.tvContentTitle.text = "咨询内容："
            }
            2 -> {
//                mBinding.stvTitle.setTitleValue("投诉详情")

                mBinding.tcvTitle.setEditNameText("被举报人：")
                mBinding.tcvTitle.tvContentText = it.name
                mBinding.tcvClassify.setEditNameText("单位：")
                mBinding.tcvClassify.tvContentText = it.part

                mBinding.tcvPlace.setEditNameText("职务：")
                mBinding.tcvPlace.tvContentText = it.job

                if (it.prove_img != null && it.prove_img.isNotEmpty()) {
                    mBinding.vLine.visibility = View.VISIBLE
                    mBinding.rv.visibility = View.VISIBLE
                    mBinding.llPhotoTitle.visibility = View.VISIBLE
                    mAdapter.replaceData(it.prove_img)
                } else {
                    mBinding.vLine.visibility = View.GONE
                    mBinding.rv.visibility = View.GONE
                    mBinding.llPhotoTitle.visibility = View.GONE
                }

                val lp = mBinding.tcvDate.layoutParams as LinearLayout.LayoutParams
                lp.topMargin = UIUtils.dip2px(16)
                mBinding.tcvDate.layoutParams = lp

                mBinding.tcvDate.setEditNameText("标题：")
                mBinding.tcvDate.tvContentText = it.title

                mBinding.tvContentTitle.text = "问题描述："
                mBinding.tvContentText.text = it.desc

                mBinding.llReport.visibility = View.GONE


                mBinding.tvDutyPeopleDec.text = "投诉受理"

                mBinding.tcvPeople.setEditNameText("提交日期：")
                mBinding.tcvPeople.tvContentText = it.add_time

                mBinding.tcvShouliDate.setEditNameText("受理日期：")
                mBinding.tcvShouliDate.tvContentText = it.settle_time

                mBinding.tcvCompeteDate.setEditNameText("回复日期：")
                mBinding.tcvCompeteDate.tvContentText = it.finish_time

                mBinding.tvContentTitle.text = "问题描述："
            }
            3 -> {
                mBinding.llReport.visibility = View.VISIBLE
//                mBinding.stvTitle.setTitleValue("事件详情")
            }
            4 -> {
                mBinding.llReport.visibility = View.VISIBLE
//                mBinding.stvTitle.setTitleValue("自行处理详情")
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
     * 我的待办
     */
    private fun handleTypeCommission(status: Int) {
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

    override fun loadSirBindView(): View {
        return mBinding.nsvView
    }

    /**
     * 处理
     *   事件详情
     *   自行处理详情
     *   我的待办详情
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
