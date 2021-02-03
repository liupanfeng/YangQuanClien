package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.EmptyInfo;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.SheepBarCommentInfo;
import com.meishe.yangquan.bean.SheepBarCommentInfoResult;
import com.meishe.yangquan.bean.SheepBarInfoResult;
import com.meishe.yangquan.bean.SheepBarMessageInfo;
import com.meishe.yangquan.bean.SheepBarPictureInfo;
import com.meishe.yangquan.divider.CustomGridItemDecoration;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.KeyboardUtils;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.BackEditText;
import com.meishe.yangquan.view.RoundAngleImageView;
import com.meishe.yangquan.wiget.CustomTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 羊吧-详情页面
 *
 * @author 86188
 */
public class SheepBarDetailActivity extends BaseActivity {

    /**
     * 最新回复
     */
    public static final int TYPE_SHEEP_BAR_NEWEST = 1;
    /**
     * 只看楼主
     */
    public static final int TYPE_SHEEP_BAR_COMMEND = 2;

    private CustomTextView tv_sheep_bar_comment_number;
    private CustomTextView tv_sheep_bar_only_see_owner;
    private int mListType;
    private SheepBarMessageInfo mSheepBarMessageInfo;

    /*底下的头像*/
    private RoundAngleImageView iv_sheep_bar_photo;
    /*底下的用户昵称*/
    private TextView tv_sheep_bar_nickname;
    /*发布时间*/
    private TextView tv_sheep_bar_time;
    /*关注容器*/
    private LinearLayout ll_sheep_bar_focus;
    /*关注文案*/
    private TextView tv_sheep_bar_focus_content;
    /*关注内容*/
    private TextView tv_sheep_bar_content;

    private BackEditText et_say_your_idea;
    private TextView tv_sheep_bar_publish;
    private RecyclerView recycler_grid;
    private MultiFunctionAdapter mGrideAdapter;
    private View backLayout;

    private boolean mIsReply = false;
    /*评论id*/
    private int mCommentId;

    @Override
    protected int initRootView() {
        return R.layout.activity_sheep_bar_detail;
    }

    @Override
    public void initView() {
        tv_sheep_bar_comment_number = findViewById(R.id.tv_sheep_bar_comment_number);
        tv_sheep_bar_only_see_owner = findViewById(R.id.tv_sheep_bar_only_see_owner);
        backLayout = findViewById(R.id.backLayout);

        iv_sheep_bar_photo = findViewById(R.id.iv_sheep_bar_photo);
        tv_sheep_bar_nickname = findViewById(R.id.tv_sheep_bar_nickname);

        tv_sheep_bar_time = findViewById(R.id.tv_sheep_bar_time);
        ll_sheep_bar_focus = findViewById(R.id.ll_sheep_bar_focus);
        tv_sheep_bar_focus_content = findViewById(R.id.tv_sheep_bar_focus_content);
        tv_sheep_bar_content = findViewById(R.id.tv_sheep_bar_content);

        /*评论的图片列表*/
        recycler_grid = findViewById(R.id.recycler_grid);
        /*评论列表*/
        mRecyclerView = findViewById(R.id.recycler);

        et_say_your_idea = findViewById(R.id.et_say_your_idea);
        tv_sheep_bar_publish = findViewById(R.id.tv_sheep_bar_publish);


        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(mContext, 3);
        mGrideAdapter = new MultiFunctionAdapter(mContext, recycler_grid);
        CustomGridItemDecoration customGridItemDecoration = new CustomGridItemDecoration(15);
        recycler_grid.addItemDecoration(customGridItemDecoration);
        recycler_grid.setLayoutManager(gridLayoutManager);
        recycler_grid.setAdapter(mGrideAdapter);


        initRecyclerView();
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            //初始化顶部信息
            Bundle bundle = intent.getExtras();
            mSheepBarMessageInfo = (SheepBarMessageInfo) bundle.getSerializable("sheep_bar_info");
            if (mSheepBarMessageInfo != null) {
                //设置用户头像
                String iconUrl = mSheepBarMessageInfo.getIconUrl();

                RequestOptions options = new RequestOptions();
                options.centerCrop();
                options.placeholder(R.mipmap.ic_message_list_photo_default);

                Glide.with(mContext)
                        .asBitmap()
                        .load(iconUrl)
                        .apply(options)
                        .into(iv_sheep_bar_photo);
                iv_sheep_bar_photo.setTag(iconUrl);

                tv_sheep_bar_nickname.setText(mSheepBarMessageInfo.getNickname());

                tv_sheep_bar_time.setText(FormatDateUtil.longToString((mSheepBarMessageInfo).getInitDate(),
                        FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));
                tv_sheep_bar_content.setText(mSheepBarMessageInfo.getContent());
                tv_sheep_bar_comment_number.setText("最新回复（" + mSheepBarMessageInfo.getCommentAmount() + "）");
                tv_sheep_bar_focus_content.setText(mSheepBarMessageInfo.isHasFocused() ? "已关注" : "+关注");
                List<String> images = mSheepBarMessageInfo.getImages();
                List<SheepBarPictureInfo> list = new ArrayList<>();
                if (!CommonUtils.isEmpty(images)) {
                    for (int i = 0; i < images.size(); i++) {
                        SheepBarPictureInfo sheepBarMessageInfo = new SheepBarPictureInfo();
                        sheepBarMessageInfo.setFilePath(images.get(i));
                        sheepBarMessageInfo.setType(SheepBarPictureInfo.TYPE_URL_PIC);
                        list.add(sheepBarMessageInfo);
                    }
                    mGrideAdapter.addAll(list);
                }
            }
        }

        selectCommontNumber();
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initListener() {
        tv_sheep_bar_comment_number.setOnClickListener(this);
        tv_sheep_bar_only_see_owner.setOnClickListener(this);
        tv_sheep_bar_publish.setOnClickListener(this);
        ll_sheep_bar_focus.setOnClickListener(this);
        backLayout.setOnClickListener(this);

        //监测最底部输入内容
        et_say_your_idea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = charSequence.length();
                if (length == 0) {
                    tv_sheep_bar_publish.setTextColor(getResources().getColor(R.color.text_content_color));
                } else {
                    tv_sheep_bar_publish.setTextColor(getResources().getColor(R.color.app_main_color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //条目点击事件
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                int id = view.getId();
                switch (id) {
                    case R.id.tv_sheep_bar_prised:
                        //评论 点赞
                        postInteractSheepBar(position, (SheepBarCommentInfo) baseInfo, 2);
                        break;
                    case R.id.tv_sheep_bar_content:
                        //点击评论
                        if (baseInfo instanceof SheepBarCommentInfo) {
                            KeyboardUtils.showSoftInput(et_say_your_idea);
                            et_say_your_idea.setHint("回复@" + ((SheepBarCommentInfo) baseInfo).getNickname());
                            mCommentId = ((SheepBarCommentInfo) baseInfo).getId();
                            mIsReply = true;
                        }

//                        CommentBottomSheetDialogFragment fragment = new CommentBottomSheetDialogFragment();
//                        fragment.setMessageId(mSheepBarMessageInfo.getId(),mListType);
//                        fragment.show(getSupportFragmentManager(), "dialog");
                        break;
                    default:
                        break;
                }
            }
        });


        et_say_your_idea.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_SEND) {
                    KeyboardUtils.hideSoftInput(et_say_your_idea);
                    et_say_your_idea.setText("");
                    et_say_your_idea.setHint("说说你的看法...");
                    mIsReply = false;
                }
                return true;
            }
        });

        /**
         * 监听由于返回键导致的键盘收起
         */
        et_say_your_idea.setBackListener(new BackEditText.OnBackListener() {
            @Override
            public void back(TextView textView) {
                et_say_your_idea.setText("");
                et_say_your_idea.setHint("说说你的看法...");
                mIsReply = false;
            }
        });

    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*最新回复*/
            case R.id.tv_sheep_bar_comment_number:

                mAdapter.addAll(null);
                mListType = TYPE_SHEEP_BAR_NEWEST;
                tv_sheep_bar_comment_number.setSelected(true);
                tv_sheep_bar_only_see_owner.setSelected(false);
                getCommentDataFromServer(mSheepBarMessageInfo.getId(), mListType);

                break;
            /*只看楼主*/
            case R.id.tv_sheep_bar_only_see_owner:
                mAdapter.addAll(null);
                mListType = TYPE_SHEEP_BAR_COMMEND;
                tv_sheep_bar_only_see_owner.setSelected(true);
                tv_sheep_bar_comment_number.setSelected(false);
                getCommentDataFromServer(mSheepBarMessageInfo.getId(), mListType);
                break;
            case R.id.tv_sheep_bar_publish:
                //发表评论
                publishComment();
                break;
            case R.id.backLayout:
                finish();
                break;
            case R.id.ll_sheep_bar_focus:
                //关注
                focusUserServer(mSheepBarMessageInfo);
                break;
            default:
                break;
        }
    }


    /**
     * 关注用户
     */
    private void focusUserServer(final SheepBarMessageInfo sheepBarMessageInfo) {
        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("focusUserId", sheepBarMessageInfo.getInitUser());
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_FOCUS, new BaseCallBack<SheepBarInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, SheepBarInfoResult sheepBarInfoResult) {
                if (sheepBarInfoResult == null) {
                    ToastUtil.showToast(response.message());
                    return;
                }
                if (sheepBarInfoResult.getCode() != 1) {
                    ToastUtil.showToast(sheepBarInfoResult.getMsg());
                    return;
                }

                if (sheepBarMessageInfo.isHasFocused()) {
                    sheepBarMessageInfo.setHasFocused(false);
                } else {
                    sheepBarMessageInfo.setHasFocused(true);
                }
                tv_sheep_bar_focus_content.setText(sheepBarMessageInfo.isHasFocused() ? "已关注" : "+关注");
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);

    }


    /**
     * 发表评论
     */
    private void publishComment() {
        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }
        String content = et_say_your_idea.getText().toString();
        if (Util.checkNull(content)) {
            return;
        }

        if (mSheepBarMessageInfo == null) {
            return;
        }

        int objectType = 0;  //帖子还是评论 1 是帖子  2是评论
        int objectId = 0;    //对应的帖子或者评论的id
        int parentId = 0;    //如果是回复的评论，这个是一级评论的id   这个不是必填项目
        if (mIsReply) {
            //评论
            objectType = 2;
            objectId = mCommentId;
            parentId = mCommentId;
        } else {
            //帖子
            objectType = 1;
            objectId = mSheepBarMessageInfo.getId();
            parentId = mSheepBarMessageInfo.getId();
        }


        HashMap<String, Object> param = new HashMap<>();
        param.put("postId", mSheepBarMessageInfo.getId());
        param.put("objectType", objectType);
        param.put("objectId", objectId);
        param.put("parentId", parentId);
        param.put("content", content);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_BAR_ADD_COMMENT, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult serverResult) {
                if (serverResult == null) {
                    ToastUtil.showToast(response.message());
                    return;
                }
                if (serverResult.getCode() != 1) {
                    ToastUtil.showToast(serverResult.getMsg());
                    return;
                }

                ToastUtil.showToast("评论成功！");
                et_say_your_idea.setText("");
                et_say_your_idea.setHint("说说你的看法...");
                mIsReply = false;
                KeyboardUtils.hideSoftInput(et_say_your_idea);
                getCommentDataFromServer(mSheepBarMessageInfo.getId(), mListType);
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);

    }

    private void selectSeeOwner() {
        mAdapter.addAll(null);
        mListType = TYPE_SHEEP_BAR_COMMEND;
        tv_sheep_bar_only_see_owner.setSelected(true);
        tv_sheep_bar_comment_number.setSelected(false);
        getCommentDataFromServer(mSheepBarMessageInfo.getId(), mListType);
    }

    private void selectCommontNumber() {
        mAdapter.addAll(null);
        mListType = TYPE_SHEEP_BAR_NEWEST;
        tv_sheep_bar_comment_number.setSelected(true);
        tv_sheep_bar_only_see_owner.setSelected(false);
        getCommentDataFromServer(mSheepBarMessageInfo.getId(), mListType);
    }

    /**
     * 获取一级评论信息
     *
     * @param id       羊吧id
     * @param listType 是否只看楼主
     */
    private void getCommentDataFromServer(int id, int listType) {
        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }
        boolean onlySeeOwner = false;
        if (listType == TYPE_SHEEP_BAR_COMMEND) {
            onlySeeOwner = true;
        } else if (listType == TYPE_SHEEP_BAR_NEWEST) {
            onlySeeOwner = false;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("postId", id);
        param.put("onlySeeOwner", onlySeeOwner);
        param.put("orderType", "desc");

        final boolean finalOnlySeeOwner = onlySeeOwner;
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_BAR_COMMENT_LIST_LEVEL1, new BaseCallBack<SheepBarCommentInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, SheepBarCommentInfoResult sheepBarCommentInfoResult) {
                if (sheepBarCommentInfoResult == null) {
                    ToastUtil.showToast(response.message());
                    return;
                }
                if (sheepBarCommentInfoResult.getCode() != 1) {
                    ToastUtil.showToast(sheepBarCommentInfoResult.getMsg());
                    return;
                }
                List<BaseInfo> lists = new ArrayList<>();
                List<SheepBarCommentInfo> datas = sheepBarCommentInfoResult.getData();
                if (CommonUtils.isEmpty(datas)) {
                    return;
                }
                SheepBarCommentInfo.setSeeOwner(datas, finalOnlySeeOwner);
                SheepBarCommentInfo.setSheepBarMeesageId(datas, mSheepBarMessageInfo.getId());
                lists.addAll(datas);
                lists.add(new EmptyInfo());

                mAdapter.addAll(lists);

            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);

    }


    /**
     * 互动接口 点赞帖子，点赞评论，收藏帖子，分享帖子
     *
     * @param interactType 1 给帖子点赞
     *                     2 评论点赞
     *                     3 收藏帖子
     *                     4 分享贴子
     */
    private void postInteractSheepBar(final int position, final SheepBarCommentInfo sheepBarCommentInfo, final int interactType) {
        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("id", sheepBarCommentInfo.getId());
        param.put("interactType", interactType);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_BAR_INTERACT, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult serverResult) {
                if (serverResult == null) {
                    ToastUtil.showToast(response.message());
                    return;
                }
                if (serverResult.getCode() != 1) {
                    ToastUtil.showToast(serverResult.getMsg());
                    return;
                }
                switch (interactType) {
                    case 1:
//                        if (sheepBarMessageInfo.isHasPraised()) {
//                            sheepBarMessageInfo.setHasPraised(false);
//                            String priseNumber = tvPriseView.getText().toString();
//                            sheepBarMessageInfo.setPraiseAmount(Integer.valueOf(priseNumber) - 1);
//                        } else {
//                            sheepBarMessageInfo.setHasPraised(true);
//                            String priseNumber = tvPriseView.getText().toString();
//                            sheepBarMessageInfo.setPraiseAmount(Integer.valueOf(priseNumber) + 1);
//                        }
//                        mAdapter.notifyItemChanged(position);
                        break;
                    case 2:
                        if (sheepBarCommentInfo.isHasPraised()) {
                            sheepBarCommentInfo.setHasPraised(false);
                            int priseCount = sheepBarCommentInfo.getPraiseAmount();
                            sheepBarCommentInfo.setPraiseAmount(priseCount - 1);
                        } else {
                            sheepBarCommentInfo.setHasPraised(true);
                            int priseCount = sheepBarCommentInfo.getPraiseAmount();
                            sheepBarCommentInfo.setPraiseAmount(priseCount + 1);
                        }
                        mAdapter.notifyItemChanged(position);
                        break;
                    case 3:
                        ToastUtil.showToast("收藏帖子！");
                        break;
                    case 4:
                        ToastUtil.showToast("分享贴子！");
                        break;
                    default:
                        break;
                }

            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);

    }
}