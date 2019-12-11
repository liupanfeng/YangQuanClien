package com.meishe.yangquan.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.BusinessOpportunityActivity;
import com.meishe.yangquan.activity.ContactUsActivity;
import com.meishe.yangquan.activity.LoginActivity;
import com.meishe.yangquan.activity.MessageCenterActivity;
import com.meishe.yangquan.activity.PerfectInformationActivity;
import com.meishe.yangquan.activity.ServiceTypeListActivity;
import com.meishe.yangquan.activity.VersionUpdateActivity;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.BusinessOpportunity;
import com.meishe.yangquan.bean.BusinessOpportunityResult;
import com.meishe.yangquan.bean.Message;
import com.meishe.yangquan.bean.MineTypeInfo;
import com.meishe.yangquan.bean.ServerCustomer;
import com.meishe.yangquan.bean.ServiceTypeInfo;
import com.meishe.yangquan.bean.SheepNews;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.fragment.CommentBottomSheetDialogFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.wiget.MaterialProgress;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class PerformClickAction {

    private static final PerformClickAction ourInstance = new PerformClickAction();

    public static PerformClickAction getInstance() {
        return ourInstance;
    }

    private PerformClickAction() {
    }



}
