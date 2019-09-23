package com.sdxxtop.zjlguardian.ui.report.fragment

import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bin.david.form.data.CellInfo
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat
import com.bin.david.form.data.format.bg.IBackgroundFormat
import com.bin.david.form.data.format.selected.BaseSelectFormat
import com.bin.david.form.data.style.FontStyle
import com.bin.david.form.data.table.TableData
import com.bin.david.form.utils.DensityUtils
import com.sdxxtop.base.BaseFragment
import com.sdxxtop.base.ext.searchGone
import com.sdxxtop.base.ext.searchShow
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.FragmentReportMessageDataBinding
import com.sdxxtop.zjlguardian.ui.report.data.ChildData
import com.sdxxtop.zjlguardian.ui.report.data.UserInfo
import com.sdxxtop.zjlguardian.ui.report.viewmodel.ReportMessageDataViewModel
import java.util.*

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-23 11:31
 * Version: 1.0
 * Description:
 */
class ReportMessageDataFragment : BaseFragment<FragmentReportMessageDataBinding, ReportMessageDataViewModel>() {

    var mSvViewHeight = 0
    var mSvViewWidth = 0

    override fun vmClazz() = ReportMessageDataViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun initObserve() {
        mViewModel.mSearchShowData.observe(this, Observer {
            if (it) {
                mBinding.vBg.visibility = View.VISIBLE
                searchShow(mBinding.rlSearch, mBinding.etSearch)
//                UIUtils.showSoftInputFromWindow(mBinding.etSearch)
            }
        })
    }

    override fun layoutId() = R.layout.fragment_report_message_data

    override fun initView() {
    }

    override fun initData() {

        FontStyle.setDefaultTextSize(DensityUtils.sp2px(activity, 16f))

        val testData = ArrayList<UserInfo>()
        for (i in 0..10) {
            val userInfo = UserInfo("名字$i", i, 11111L + i.toLong(), i % 2 == 0, ChildData("child$i"))
            testData.add(userInfo)
        }

        val userInfo = UserInfo("名字afadfasfda", 100, 33333333, false, ChildData("asdfasdfadsfasdfasdfasdf"))
        testData.add(userInfo)

        val columns = ArrayList<Column<*>>()
        columns.add(Column<Any>("序号", "age"))
        columns.add(Column<Any>("姓名", "name"))
        columns.add(Column<Any>("年龄", "age"))
        columns.add(Column<Any>("出生日期", "time"))

        val tableData = TableData("标题测试", testData, columns)
//        tableData.setOnItemClickListener { column, value, t, col, row ->
//            UIUtils.showToast("aaa->$t")
//        }
        tableData.setOnRowClickListener { column: Column<Any>, userInfo: UserInfo, col: Int, row: Int ->
            UIUtils.showToast("aaa->$userInfo")
        }

        val backgroundFormat = object : BaseCellBackgroundFormat<CellInfo<*>>() {
            override fun getBackGroundColor(cellInfo: CellInfo<*>): Int {
//                return if (cellInfo.row % 2 == 0) {
//                    ContextCompat.getColor(this@AlignActivity, R.color.content_bg)
//                } else {
//                    TableConfig.INVALID_COLOR //返回无效颜色，不会绘制
//                }
                return ContextCompat.getColor(activity!!, R.color.white)
            }
        }

        mBinding.stTable.setSelectFormat(BaseSelectFormat())

        val iBackgroundFormat = IBackgroundFormat { canvas, rect, paint ->
            paint.color = resources.getColor(R.color.color_EFEFF4)
            paint.style = Paint.Style.FILL
            canvas.drawRect(rect, paint)
        }

        mBinding.stTable.getConfig().setShowTableTitle(false)
                .setShowXSequence(false)
                .setShowYSequence(false)
                .setMinTableWidth(resources.displayMetrics.widthPixels)
                .setContentBackground(iBackgroundFormat)
                .setContentCellBackgroundFormat(backgroundFormat)
                .setColumnTitleBackground(iBackgroundFormat)
                .setColumnTitleStyle(FontStyle().setTextColor(resources.getColor(R.color.colorPrimary)))
                .setColumnTitleVerticalPadding(UIUtils.dip2px(10))
                .setVerticalPadding(UIUtils.dip2px(10))

        mBinding.stTable.setSelectFormat { canvas, rect, showRect, config ->
            val paint = config.paint
            paint.color = resources.getColor(R.color.color_880CBEB5)
            paint.style = Paint.Style.FILL
            canvas.drawRect(rect, paint)
        }
        mBinding.stTable.setTableData(tableData)


    }

    override fun loadData() {
        mLoadService.showSuccess()
    }

    override fun initEvent() {
        mBinding.svView.viewTreeObserver.addOnGlobalLayoutListener {
            if (mSvViewHeight == 0) {
                mSvViewHeight = mBinding.svView.height
            }
            if (mSvViewWidth == 0) {
                mSvViewWidth = mBinding.svView.width
            }
        }
    }

    override fun onBackPressedSupport(): Boolean {
        if (mBinding.rlSearch.visibility == View.VISIBLE) {
            //当search显示的时候，按返回键，就隐藏search
            searchGone(mBinding.rlSearch, mBinding.vBg)

            return true
        }
        return super.onBackPressedSupport()
    }
}