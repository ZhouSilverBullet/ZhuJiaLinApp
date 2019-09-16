package com.sdxxtop.trackerlibrary.test;

import android.text.TextUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-14 16:32
 * Version: 1.0
 * Description:
 */
public class PathCategroyUtil {
    /**
     * 名字排序
     */
    static ConcurrentMap<String, String> map = new ConcurrentHashMap<>();

    static {
        //首页
        map.put("MainActivity$HomeFragment", "首页-首页");
        map.put("HomeWorkActivity", "首页-作业-作业列表");
        map.put("HomeWorkDetailActivity", "首页-作业-作业详情");

        map.put("CourseTableActivity", "首页-课程表");

        map.put("QingJiaActivity", "首页-请假-请假列表");
        map.put("AddApplyActivity-1", "首页-请假-请假创建");
        map.put("ReadDetailActivity-21", "首页-请假-详情页面");

        map.put("BaiFangActivity", "首页-拜访-拜访列表");
        map.put("AddApplyActivity-2", "首页-拜访-拜访创建");
        map.put("ReadDetailActivity-22", "首页-拜访-拜访详情");

        map.put("SessionActivity", "首页-联系孩子");
        map.put("LearingReportActivity", "首页-学情报告");
        map.put("KaoQinActivity", "首页-出勤");


        //健康数据
        map.put("HealthDataActivity$DataAllFragment", "健康数据-健康数据总览");

        map.put("HealthDataActivity$DataExerciseFragment", "健康数据-运动-运动数据");
        map.put("ExerciseHistoryActivity", "健康数据-运动-运动历史数据");

        map.put("HealthDataActivity$DataSleepFragment", "健康数据-睡眠-睡眠数据");
        map.put("SleepHistoryActivity", "健康数据-睡眠-睡眠历史数据");

        map.put("HealthDataActivity$DataHealthFragment", "健康数据-健康-健康数据");
        map.put("HeartHistoryActivity", "健康数据-健康-健康历史数据");


        //课堂报告
        map.put("StudentGrowthActivity", "课堂报告-课堂报告首页");
        map.put("ClassDetailsActivity", "课堂报告-最佳表现");
        map.put("StudentMoreActivity", "课堂报告-成长记录");
        map.put("StudentSpeakActivity", "课堂报告-课堂发言更多");
        map.put("StudentMoodActivity", "课堂报告-情绪占比更多");
        map.put("StudentConcentrationActivity", "课堂报告-课堂专注度更多");
        map.put("StudentActiveActivity", "课堂报告-课堂活跃度更多");


        //我的
        map.put("MainActivity$MineFragment", "我的-我的首页");
        map.put("DifficultApplyActivity", "我的-困难申请-困难申请列表");
        map.put("DifficultApplyDetailActivity", "我的-困难申请-困难申请创建");
        map.put("DifficultApplyShowActivity", "我的-困难申请-困难申请详情");

        map.put("AddStudentActivity", "我的-添加学生");
        map.put("BindChildActivity", "我的-绑定还在");


        //通讯录

        map.put("MainActivity$ContactFragment", "通讯录-通讯录首页");
        map.put("ContactDetailActivity", "通讯录-详情资料");


        //消息
        map.put("MessageCenterNoticeActivity", "消息-公告-公告列表");
        map.put("NoticeDetailActivity", "消息-公告-公告详情");
        map.put("ScoreActivity", "消息-成绩-成绩列表");
        map.put("ScoreDetailActivity", "消息-成绩-成绩详情");

        map.put("ChatActivity", "消息-消息发送");
        map.put("MainActivity$ConversationFragment", "消息-消息首页");

        map.put("TrackerTestActivity", "消息-消息首页");
        map.put("LoginActivity", "登录界面");


    }

    public static String getPathName(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }

        return map.get(name);
    }
}
