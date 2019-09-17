package com.sdxxtop.zjlguardian.ui.home

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import com.cy.translucentparent.StatusNavUtils
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.ui.DotView
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityMainTabBinding
import com.sdxxtop.zjlguardian.ui.contact.fragment.ContactFragment
import com.sdxxtop.zjlguardian.ui.gridmanagement.fragment.GridManageFragment
import com.sdxxtop.zjlguardian.ui.home.viewmodel.MainTabViewModel
import com.sdxxtop.zjlguardian.ui.mine.fragment.MineFragment
import com.sdxxtop.zjlguardian.ui.report.fragment.ReportFragment
import me.yokeyword.fragmentation.SupportFragment
import java.util.*


class MainTabActivity : BaseActivity<ActivityMainTabBinding, MainTabViewModel>() {
    override fun vmClazz() = MainTabViewModel::class.java

    override fun layoutId() = R.layout.activity_main_tab

    override fun bindVM() {

    }

    override fun initObserve() {

    }

    override fun initView() {
        switchFragment(0)
        StatusNavUtils.setStatusBarColor(this, 0x00000000)
        initUnReadMessageViews()
    }

    override fun initEvent() {
        mBinding.bnvView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                com.sdxxtop.zjlguardian.R.id.item_main -> {
                    switchFragment(0)
                    StatusNavUtils.setStatusBarColor(this, 0x00000000)
                }
                com.sdxxtop.zjlguardian.R.id.item_small_video -> {
                    switchFragment(1)
                    StatusNavUtils.setStatusBarColor(this, 0x0CBEB5)
                }
                com.sdxxtop.zjlguardian.R.id.item_video -> {
                    switchFragment(2)
                    StatusNavUtils.setStatusBarColor(this, 0x0CBEB5)
                }
                com.sdxxtop.zjlguardian.R.id.item_mine -> {
                    switchFragment(3)
                    StatusNavUtils.setStatusBarColor(this, 0x0CBEB5)
                }
                else -> {
                }
            }

            true
        }
    }

    private lateinit var dotViews: Array<DotView?>
    var mFragments: ArrayList<SupportFragment> = arrayListOf<SupportFragment>()
    var mPrePosition: Int = 0

    private fun switchFragment(position: Int) {
        val fragment = findFragment(GridManageFragment::class.java)
        if (fragment == null) {

            mFragments.add(GridManageFragment())
            mFragments.add(ReportFragment())
            mFragments.add(ContactFragment())
            mFragments.add(MineFragment())

            loadMultipleRootFragment(com.sdxxtop.zjlguardian.R.id.fl_home_container, position,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3])
//            if (isAdmin) { //有权限的，就显示网格员
//
//                mFragments[0] = HomeFragment()
//                mFragments[1] = ServerPeopleFragment.Companion.newInstance(1)
//                //                mFragments[2] = new LearningFragment();
//                mFragments[2] = NewsListFragment.newInstance(2)
//                mFragments[3] = MineFragment.newInstance(isAdmin)
//
//                loadMultipleRootFragment(R.id.fl_home_container, position,
//                        mFragments[0],
//                        mFragments[1],
//                        mFragments[2], mFragments[3])
//            } else { //无权限的，不显示网格员
//
//                if (mFragments == null) {
//                    mFragments = arrayOfNulls<SupportFragment>(3)
//                }
//                mFragments[0] = ServerPeopleFragment.Companion.newInstance(2)
//                //                mFragments[1] = new LearningFragment();
//                mFragments[1] = NewsListFragment.newInstance(2)
//                mFragments[2] = MineFragment.newInstance(isAdmin)
//
//                loadMultipleRootFragment(R.id.fl_home_container, position,
//                        mFragments[0],
//                        mFragments[1],
//                        mFragments[2])
//            }


        } else {
            showHideFragment(mFragments[position], mFragments[mPrePosition])
        }
        mPrePosition = position
    }

    private fun initUnReadMessageViews() {
        //初始化红点view
        var menuView: BottomNavigationMenuView? = null
        for (i in 0 until mBinding.bnvView.childCount) {
            val child = mBinding.bnvView.getChildAt(i)
            if (child is BottomNavigationMenuView) {
                menuView = child
                break
            }
        }
        if (menuView != null) {
            val dp8 = UIUtils.dip2px(8)
            dotViews = arrayOfNulls<DotView>(menuView.childCount)
            for (i in 0 until menuView.childCount) {
                val params = FrameLayout.LayoutParams(if (i == menuView.childCount - 1) dp8 else dp8 * 2, 0)
                params.gravity = Gravity.CENTER_HORIZONTAL
                params.leftMargin = dp8 * 3
                params.topMargin = dp8 / 2
                val itemView = menuView.getChildAt(i) as BottomNavigationItemView
                val dotView = DotView(this)
                dotView.setBackgroundColor(Color.RED)
                dotView.setTextColor(Color.WHITE)
                dotView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                if (i < menuView.childCount - 1) {
                    val count = Random().nextInt(220) + 1
                    dotView.setUnReadCount(count)
                }
                itemView.addView(dotView, params)
                dotViews[i] = dotView
            }
        }
    }
}
