package com.sdxxtop.trackerlibrary.test;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sdxxtop.trackerlibrary.R;
import com.sdxxtop.trackerlibrary.db.TrackerPathEntryDao;
import com.sdxxtop.trackerlibrary.db.control.DaoManager;
import com.sdxxtop.trackerlibrary.db.entity.TrackerPathEntry;
import com.sdxxtop.trackerlibrary.util.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TrackerTestActivity extends AppCompatActivity {
    private static final String TAG = "TrackerTestActivity";

    private RecyclerView recyclerView;
    private TTAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker_test);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DaoManager.getInstance().getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                queryData();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("知点云教室版");
    }

    private void queryData() {
        List<TrackerPathEntry> list = DaoManager.getInstance()
                .getDaoMaster().newSession()
                .getTrackerPathEntryDao()
                .queryBuilder()
//                .where(TrackerPathEntryDao.Properties.IsComplete.eq(true))
                .where(TrackerPathEntryDao.Properties.Duration.gt(0L))
                .build()
                .list();

        for (TrackerPathEntry trackerPathEntry : list) {
            LogUtil.i(TAG, trackerPathEntry.toString());
        }

        HashMap<String, Integer> countMap = new HashMap<>(0);
//        HashMap<String, Long> timeMap = new HashMap<>(0);
        HashMap<String, TrackerPathEntry> beanMap = new HashMap<>(0);

        //算好次数
        for (TrackerPathEntry bean : list) {
            Integer value = countMap.get(bean.getPath());
            if (value == null) {
                countMap.put(bean.getPath(), 1);
            } else {
                countMap.put(bean.getPath(), ++value);
            }

            TrackerPathEntry trackerPathEntry = beanMap.get(bean.getPath());
            if (trackerPathEntry == null) {
                beanMap.put(bean.getPath(), bean);
            } else {
                Long duration = bean.getDuration();
                trackerPathEntry.setDuration(duration + trackerPathEntry.getDuration());
            }

        }

        ArrayList<EventTestBean> newBeans = new ArrayList<>();
        for (String path : beanMap.keySet()) {
            Integer integer = countMap.get(path);
            int count = integer == null ? 0 : integer;
            String pathName = PathCategroyUtil.getPathName(path);
            if (!TextUtils.isEmpty(pathName)) {
                TrackerPathEntry trackerPathEntry = beanMap.get(path);
//                Long longDuration = timeMap.get(bean.getPath());

                EventTestBean eventTestBean = new EventTestBean();
                eventTestBean.setPath(pathName);
                eventTestBean.count = count;
                Long duration = trackerPathEntry.getDuration();
                duration = duration == null ? 0 : duration;
//                eventTestBean.allDuration = longDuration / 1000.0;
                eventTestBean.setDuration(duration / 1000.0);
//                longDuration = longDuration == null ? 0 : longDuration;
//                eventTestBean.allDuration = longDuration / 1000.0;
                newBeans.add(eventTestBean);
            }
        }

        Collections.sort(newBeans, new PinyinComparator());

        runOnUiThread(() -> {
            mAdapter = new TTAdapter(newBeans);
            recyclerView.addItemDecoration(new ItemDivider());
            recyclerView.setAdapter(mAdapter);
        });
    }

    private class TTAdapter extends RecyclerView.Adapter<TTAdapter.MyViewHolder> {
        List<EventTestBean> list;

        public TTAdapter(List<EventTestBean> list) {
            this.list = list;
        }

        @Override
        public TTAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(getLayoutInflater().inflate(R.layout.recycler_tracker_test, parent, false));
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public void onBindViewHolder(TTAdapter.MyViewHolder holder, int position) {
            EventTestBean item = list.get(position);
            holder.tvName.setText(item.getPath());
            holder.tvTime.setText("" + item.getDuration() + "秒");
            holder.tvCount.setText(item.count + "次");
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;
            TextView tvTime;
            TextView tvCount;

            public MyViewHolder(View itemView) {
                super(itemView);

                tvName = itemView.findViewById(R.id.tv_name);
                tvTime = itemView.findViewById(R.id.tv_time);
                tvCount = itemView.findViewById(R.id.tv_count);
            }
        }
    }

//    private class TTAdapter extends BaseQuickAdapter<EventBean, BaseViewHolder> {
//
//        public TTAdapter() {
//            super(R.layout.recycler_tracker_test);
//        }
//
//        @Override
//        protected void convert(BaseViewHolder helper, EventBean item) {
//
//
//            tvName.setText(item.getPath());
//            tvTime.setText("" + item.getDuration());
//            tvCount.setText(item.count + "次");
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                showDelete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDelete() {
        new AlertDialog.Builder(this)
                .setTitle("删除")
                .setMessage("是否删除本地记录")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DaoManager.getInstance()
                                .getDaoSession()
                                .getTrackerPathEntryDao()
                                .deleteAll();

                        if (mAdapter != null) {
                            mAdapter.list.clear();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

}
