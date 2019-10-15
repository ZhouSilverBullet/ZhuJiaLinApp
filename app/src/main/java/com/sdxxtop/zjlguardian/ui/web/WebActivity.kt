package com.sdxxtop.zjlguardian.ui.web

import android.content.Context
import android.content.Intent
import com.sdxxtop.base.BaseNormalActivity
import com.sdxxtop.webview.CommonWebFragment
import com.sdxxtop.webview.command.Command
import com.sdxxtop.webview.command.CommandsManager
import com.sdxxtop.webview.command.ResultBack
import com.sdxxtop.webview.utils.WebConstants
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityWebBinding

class WebActivity : BaseNormalActivity<ActivityWebBinding>() {
    val url by lazy {
        intent.getStringExtra("url")
    }

    var fragment:CommonWebFragment?=null

    override fun layoutId() = R.layout.activity_web

    override fun initView() {
        //注册这个
        CommandsManager.getInstance().registerCommand(WebConstants.LEVEL_LOCAL, titleUpdateCommand)

        fragment = CommonWebFragment.newInstance(url)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container, fragment!!)
                .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        fragment?.onActivityResult2(requestCode, resultCode, data)
    }

    /**
     * 页面路由
     */
    private val titleUpdateCommand = object : Command {
        override fun name(): String {
            return Command.COMMAND_UPDATE_TITLE
        }

        override fun exec(context: Context, params: Map<*, *>, resultBack: ResultBack) {
            if (params.containsKey(Command.COMMAND_UPDATE_TITLE_PARAMS_TITLE)) {
                mBinding.stvTitle.setTitleValue(params[Command.COMMAND_UPDATE_TITLE_PARAMS_TITLE] as String)
            }
        }
    }

    companion object {
        @JvmStatic
        fun skipWebActivity(context: Context, url: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }
}
