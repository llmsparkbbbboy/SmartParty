package com.qiantang.smartparty.module.index.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.orhanobut.logger.Logger;
import com.qiantang.smartparty.BaseBindActivity;
import com.qiantang.smartparty.R;
import com.qiantang.smartparty.module.index.viewmodel.SignViewModel;
import com.qiantang.smartparty.databinding.ActivitySignBinding;
import com.qiantang.smartparty.utils.ToastUtil;
import com.qiantang.smartparty.utils.permissions.EasyPermission;
import com.qiantang.smartparty.utils.permissions.PermissionCode;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by zhaoyong bai on 2018/5/31.
 */
public class SignActivity extends BaseBindActivity implements EasyPermission.PermissionCallback {
    private SignViewModel viewModel;
    private ActivitySignBinding binding;
    private LocationClient locationClient;

    @Override
    protected void initBind() {
        viewModel = new SignViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign);
        binding.setViewModel(viewModel);
    }

    @Override
    public void initView() {
        locationClient = new LocationClient(getApplicationContext());
        initClient();
    }

    private void initClient() {
        LocationClientOption mOption = new LocationClientOption();
        mOption.setOpenGps(true);
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        mOption.setScanSpan(60 * 1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
        mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
        mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        mOption.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.SetIgnoreCacheException(true);//可选，默认false，设置是否收集CRASH信息，默认收集
        mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationClient.setLocOption(mOption);
        locationClient.registerLocationListener(listener);
    }

    private BDLocationListener listener = bdLocation -> {

        if (bdLocation == null) {
            ToastUtil.toast("获取位置失败,请打开定位重试");
        } else {
           String descibe=bdLocation.getLocationDescribe();
           if (TextUtils.isEmpty(descibe)){
               descibe=bdLocation.getAddrStr();
           }else {
               descibe=descibe.substring(1,descibe.length()-2);
               descibe=bdLocation.getStreet()+" "+descibe;
           }
            viewModel.setLocation(descibe);
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermission();
            return;
        }
        locationClient.start();
    }

    public void requestPermission() {
        EasyPermission.with(this)
                .rationale(getString(R.string.rationale_gps))
                .addRequestCode(PermissionCode.RG_LOCATION)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .request();
    }

    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {
        if (requestCode == PermissionCode.RG_LOCATION) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationClient.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {
        if (requestCode == PermissionCode.RG_LOCATION) {
            ToastUtil.toast("需要打开您的定位权限");
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_confirm:
                viewModel.sign();
                break;
        }
    }

    @Override
    protected void viewModelDestroy() {
        locationClient.stop();
        listener = null;
        viewModel.destroy();
    }
}
