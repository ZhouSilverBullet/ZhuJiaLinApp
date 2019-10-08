package com.sdxxtop.zjlguardian.ui.mine.fragment


import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.sdxxtop.base.BaseFragment
import com.sdxxtop.base.BaseNormalFragment
import com.sdxxtop.base.ext.topViewPadding
import com.sdxxtop.common.dialog.IosAlertDialog
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.FragmentMineBinding
import com.sdxxtop.zjlguardian.model.db.UserSession
import com.sdxxtop.zjlguardian.ui.commission.CommissionActivity
import com.sdxxtop.zjlguardian.ui.department.DepartmentListActivity
import com.sdxxtop.zjlguardian.ui.event_report.EventReportDetailActivity
import com.sdxxtop.zjlguardian.ui.event_report.EventReportListActivity
import com.sdxxtop.zjlguardian.ui.login.LoginActivity
import com.sdxxtop.zjlguardian.ui.message.MessageActivity
import com.sdxxtop.zjlguardian.ui.mine.viewmodel.MineViewModel

/**
 * A simple [Fragment] subclass.
 */
class MineFragment : BaseFragment<FragmentMineBinding, MineViewModel>() {
    override fun vmClazz() = MineViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
        mBinding.click = this
    }

    override fun layoutId() = R.layout.fragment_mine

    override fun initObserve() {

    }

    override fun initView() {
        topViewPadding(mBinding.stvTitle)
    }

    override fun initData() {

        mViewModel.partName.set(UserSession.getInstance().partName)
        mViewModel.phone.set(UserSession.getInstance().mobile)
        mViewModel.userName.set(UserSession.getInstance().userName)
    }

    override fun loadData() {
        mLoadService.showSuccess()
    }

    override fun onClick(v: View) {
        when (v) {
            mBinding.btnLogout -> {
                logout()
            }
            mBinding.tatvEventReport -> {
                val intent = Intent(activity, EventReportListActivity::class.java)
                startActivity(intent)
            }
            mBinding.tatvSelfHandle -> {
                val intent = Intent(activity, EventReportListActivity::class.java)
                intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, EventReportDetailActivity.TYPE_SELF)
                startActivity(intent)
            }
            mBinding.tatvDepartmentEvent -> {
                val intent = Intent(activity, DepartmentListActivity::class.java)
                startActivity(intent)
            }

            mBinding.tatvDaiban -> {
                val intent = Intent(activity, CommissionActivity::class.java)
                startActivity(intent)
            }

            //消息
            mBinding.tatvMessage -> {
                val intent = Intent(activity, MessageActivity::class.java)
                startActivity(intent)
            }
            else -> {
            }
        }
    }

    private fun logout() {
        IosAlertDialog(activity).builder()
                .setTitle("提示")
                .setMsg("确定退出本账号吗？")
                .setNegativeButton("") {

                }.setPositiveButton("退出") {
                    UserSession.getInstance().logout()
                    startActivity(Intent(activity, LoginActivity::class.java))
                    activity?.finishAffinity()
                }
                .show()
    }
}
