package com.qiantang.smartparty.module.assistant.view;

import android.databinding.DataBindingUtil;

import com.qiantang.smartparty.BaseBindActivity;
import com.qiantang.smartparty.R;
import com.qiantang.smartparty.databinding.ActivityReportBinding;
import com.qiantang.smartparty.module.assistant.viewmodel.ReportViewModel;

/**
 * Created by zhaoyong bai on 2018/6/1.
 */
public class ReportActivity extends BaseBindActivity {
    private ReportViewModel viewModel;
    private ActivityReportBinding binding;

    @Override
    protected void initBind() {
        viewModel = new ReportViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        binding.toolbar.setTitle("思想汇报");
        binding.toolbar.setIsHide(true);
    }

    @Override
    protected void viewModelDestroy() {

    }
}