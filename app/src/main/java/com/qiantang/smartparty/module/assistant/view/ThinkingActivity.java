package com.qiantang.smartparty.module.assistant.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qiantang.smartparty.BaseBindActivity;
import com.qiantang.smartparty.R;
import com.qiantang.smartparty.config.Config;
import com.qiantang.smartparty.databinding.ActivityRecycleviewBinding;
import com.qiantang.smartparty.module.assistant.viewmodel.MienViewModel;
import com.qiantang.smartparty.module.index.adapter.IndexCommonAdapter;
import com.qiantang.smartparty.utils.ActivityUtil;
import com.qiantang.smartparty.utils.RecycleViewUtils;

/**
 * Created by zhaoyong bai on 2018/6/8.
 */
public class ThinkingActivity extends BaseBindActivity {
    private IndexCommonAdapter adapter;
    private MienViewModel viewModel;
    private ActivityRecycleviewBinding binding;

    @Override
    protected void initBind() {
        adapter = new IndexCommonAdapter(R.layout.item_meeting_record);
        viewModel = new MienViewModel(this, adapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycleview);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("思想汇报");
        binding.toolbar.setRight("发表");
        initRv(binding.rv);
        viewModel.getThinkingData(7);
    }

    private void initRv(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(RecycleViewUtils.getLoadMoreView());
        rv.addOnItemTouchListener(viewModel.onItemTouchListener());
        adapter.setOnLoadMoreListener(() -> viewModel.loadMore(), rv);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_right:
                ActivityUtil.startReportActivity(this);
                break;
        }
    }

    @Override
    protected void viewModelDestroy() {
        viewModel.destroy();
    }
}