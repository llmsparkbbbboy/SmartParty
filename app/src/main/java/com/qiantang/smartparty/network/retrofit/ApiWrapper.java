package com.qiantang.smartparty.network.retrofit;


import com.qiantang.smartparty.MyApplication;
import com.qiantang.smartparty.modle.RxActivity;
import com.qiantang.smartparty.modle.RxActivityDetial;
import com.qiantang.smartparty.modle.RxAssientHome;
import com.qiantang.smartparty.modle.RxCharacterDetial;
import com.qiantang.smartparty.modle.RxIndexCommon;
import com.qiantang.smartparty.modle.RxMsg;
import com.qiantang.smartparty.modle.RxMyStudy;
import com.qiantang.smartparty.modle.RxRankBranch;
import com.qiantang.smartparty.modle.RxRankPersonal;
import com.qiantang.smartparty.modle.RxSignList;
import com.qiantang.smartparty.modle.RxStudy;
import com.qiantang.smartparty.modle.RxStudyUnreadMsg;
import com.qiantang.smartparty.modle.RxThinkDetial;
import com.qiantang.smartparty.modle.RxUploadUrl;
import com.qiantang.smartparty.utils.luban.Luban;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by ChenHengQuan on 2016/11/23.
 * Email nullpointerchan@163.com
 */
public class ApiWrapper extends RetrofitUtil {

    private volatile static ApiWrapper apiWrapper;

    private ApiWrapper() {
    }

    public static ApiWrapper getInstance() {
        if (apiWrapper == null) {
            synchronized (ApiWrapper.class) {
                if (apiWrapper == null) {
                    apiWrapper = new ApiWrapper();
                }
            }
        }
        return apiWrapper;
    }

    public synchronized Observable<RxUploadUrl> setUpload(final String path) {
        final File file = new File(path);
        return Luban.get(MyApplication.getContext())
                .load(file)
                .putGear(Luban.THIRD_GEAR)
                .asObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnError(Throwable::printStackTrace)
                .onErrorResumeNext(throwable -> {
                    return Observable.empty();
                })
                .flatMap(bytes -> {
                    RequestBody requestFile = RequestBody.create(MediaType.parse
                            ("multipart/form-data"), bytes);
                    String fileName = file.getName().replaceFirst("gif$", "png");
                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("file", fileName, requestFile);
                    return ApiWrapper.getInstance().upload(body);
                })
                .map(uploadUrls -> {
                    RxUploadUrl uploadUrl = null;
                    if (uploadUrls.size() > 0) {
                        uploadUrl = uploadUrls.get(0);
                        uploadUrl.setFilePath(path);
                    }
                    return uploadUrl;
                });
    }

    /**
     * 上传文件
     */
    public Observable<List<RxUploadUrl>> upload(MultipartBody.Part file) {
        return getService().upload(file)
                .compose(this.apply());
    }


    /**
     * 个人排行
     *
     * @param time
     * @return
     */
    public Observable<RxRankPersonal> getRankPersonal(String time) {
        return getService().getRankPersonal(MyApplication.USER_ID, time).compose(this.applySchedulers());
    }


    /**
     * 支部排行
     *
     * @param time
     * @return
     */
    public Observable<RxRankBranch> getRankBranch(String time) {
        return getService().getRankBranch(MyApplication.USER_ID, time).compose(this.applySchedulers());
    }


    /**
     * 获取学习感悟列表
     *
     * @return
     */
    public Observable<RxStudy> getStudyList(int pageNo) {
        return getService().getStudyList(MyApplication.USER_ID, pageNo).compose(this.applySchedulers());
    }

    /**
     * 获取学习感悟列表
     *
     * @return
     */
    public Observable<RxMyStudy> getMyStudyList(int pageNo) {
        return getService().getMyStudyList(MyApplication.USER_ID, pageNo).compose(this.applySchedulers());
    }

    /**
     * 删除学习感悟
     *
     * @return
     */
    public Observable<String> deleteComment(String id) {
        return getService().deleteComment(id).compose(this.applySchedulers());
    }

    /**
     * 学习感悟未读消息
     */
    public Observable<List<RxStudyUnreadMsg>> getUnreadMsg() {
        return getService().getUnreadMsg(MyApplication.USER_ID).compose(this.applySchedulers());
    }

    public Observable<String> commentLike(int type, String id, String content) {
        return getService().commentLike(MyApplication.USER_ID, type, id, content).compose(this.applySchedulers());
    }

    /**
     * 取消赞
     *
     * @param id
     * @return
     */
    public Observable<String> cancelLike(String id) {
        return getService().cancelLike(MyApplication.USER_ID, id).compose(this.applySchedulers());
    }

    /**
     * 发表感想
     *
     * @return
     */
    public Observable<String> addCommentApp(String content, String image) {
        return getService().addCommentApp(MyApplication.USER_ID, content, image).compose(this.applySchedulers());
    }

    /**
     * 党建助手首页
     */
    public Observable<RxAssientHome> assientHome() {
        return getService().assientHome().compose(this.applySchedulers());
    }

    /**
     * 党建助手首页
     */
    public Observable<List<RxMsg>> tzNotice(int page) {
        return getService().tzNotice(page).compose(this.applySchedulers());
    }

    /**
     * @param type 4党建风采
     * @param page
     * @return
     */
    public Observable<List<RxIndexCommon>> fcNotice(int page, int type) {
        return getService().fcNotice(page, type).compose(this.applySchedulers());
    }

    /**
     * 党建活动
     *
     * @param page
     * @return
     */
    public Observable<List<RxActivity>> djActivity(int page) {
        return getService().djActivity(page).compose(this.applySchedulers());
    }

    /**
     * 活动详情
     *
     * @param page
     * @param id
     * @return
     */
    public Observable<RxActivityDetial> djActivityDetails(int page, String id) {
        return getService().djActivityDetails(page, id, MyApplication.USER_ID).compose(this.applySchedulers());
    }

    /**
     * 评论
     *
     * @param id
     * @return
     */
    public Observable<String> comment(String content, String id) {
        return getService().comment(id, content, MyApplication.USER_ID).compose(this.applySchedulers());
    }

    /**
     * 报名
     *
     * @param id
     * @return
     */
    public Observable<String> enroll(String id) {
        return getService().enroll(id, MyApplication.USER_ID).compose(this.applySchedulers());
    }

    /**
     * 签到记录
     *
     * @param id
     * @param pageNo
     * @return
     */
    public Observable<List<RxSignList>> tzSign(String id, int pageNo) {
        return getService().tzSign(id, pageNo).compose(this.applySchedulers());
    }

    /**
     * 风采详情
     *
     * @param page
     * @param id
     * @return
     */
    public Observable<RxActivityDetial> fcNoticeDetails(int page, String id) {
        return getService().fcNoticeDetails(page, id, MyApplication.USER_ID).compose(this.applySchedulers());
    }

    /**
     * 风采详情
     *
     * @param page
     * @param id
     * @return
     */
    public Observable<RxCharacterDetial> rwNoticeDetails(int page, String id, String printurl) {
        return getService().rwNoticeDetails(page, printurl, id, MyApplication.USER_ID).compose(this.applySchedulers());
    }

    /**
     * 思想汇报列表
     *
     * @param page
     * @param type
     * @return
     */
    public Observable<List<RxIndexCommon>> thinking(int page, int type) {
        return getService().thinking(page, type, MyApplication.USER_ID).compose(this.applySchedulers());
    }

    /**
     * 思想汇报详情
     *
     * @return
     */
    public Observable<RxThinkDetial> thinkingDetails(String id) {
        return getService().thinkingDetails(id).compose(this.applySchedulers());
    }
}
