package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.SheepBarCommentInfo;
import com.meishe.yangquan.bean.SheepBarCommentInfoResult;
import com.meishe.yangquan.bean.SheepBarCommentSecondaryInfo;
import com.meishe.yangquan.bean.SheepBarCommentSecondaryInfoResult;
import com.meishe.yangquan.bean.SheepBarMessageInfo;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 羊吧 评论列表
 *
 * @author 86188
 */
public class SheepBarCommentListHolder extends BaseViewHolder {

    private final RequestOptions options;
    private View mItemView;


    /*头像*/
    private ImageView mIvSheepBarPhoto;

    /*昵称*/
    private TextView tv_sheep_bar_nickname;
    /*更新时间*/
    private TextView tv_sheep_bar_time;

    /*羊吧内容*/
    private TextView tv_sheep_bar_content;
    /*点赞*/
    private TextView tv_sheep_bar_prised;
    private RecyclerView child_recycler;
    private MultiFunctionAdapter mChildAdapter;

    public SheepBarCommentListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        mItemView = itemView;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        mIvSheepBarPhoto = view.findViewById(R.id.iv_sheep_bar_photo);
        tv_sheep_bar_nickname = view.findViewById(R.id.tv_sheep_bar_nickname);
        tv_sheep_bar_time = view.findViewById(R.id.tv_sheep_bar_time);
        tv_sheep_bar_prised = view.findViewById(R.id.tv_sheep_bar_prised);
        tv_sheep_bar_content = view.findViewById(R.id.tv_sheep_bar_content);
        child_recycler = view.findViewById(R.id.child_recycler);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(child_recycler.getContext(), RecyclerView.VERTICAL, false);
        mChildAdapter = new MultiFunctionAdapter(child_recycler.getContext(), child_recycler);
        child_recycler.setLayoutManager(layoutManager);
        child_recycler.setAdapter(mChildAdapter);

        //这是二级评论的点击回调
        mChildAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof SheepBarCommentSecondaryInfo ) {
                    switch (view.getId()){
                        case R.id.tv_sheep_bar_prised:
                            postInteractSheepBar(view.getContext(), position, (SheepBarCommentSecondaryInfo) baseInfo,
                                    2);
                            break;
                        case R.id.tv_sheep_bar_content:
                            MessageEvent messageEvent=new MessageEvent();
                            messageEvent.setEventType(MessageEvent.MESSAGE_TYPE_DO_SECOND_COMMENT);
                            messageEvent.setBaseInfo(baseInfo);
                            EventBus.getDefault().post(messageEvent);
                            break;
                    }

                }
            }
        });

    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof SheepBarCommentInfo) {

            String iconUrl = ((SheepBarCommentInfo) info).getIconUrl();
            if (iconUrl != null) {
                Glide.with(context)
                        .asBitmap()
                        .load(iconUrl)
                        .apply(options)
                        .into(mIvSheepBarPhoto);

            }

            tv_sheep_bar_nickname.setText("@" + ((SheepBarCommentInfo) info).getNickname());
            tv_sheep_bar_time.setText(FormatCurrentData.getTimeRange(((SheepBarCommentInfo) info).getInitDate()));
            tv_sheep_bar_content.setText(((SheepBarCommentInfo) info).getContent());
            tv_sheep_bar_prised.setCompoundDrawablesWithIntrinsicBounds(((SheepBarCommentInfo) info).isHasPraised() ? context.getResources().getDrawable(R.mipmap.ic_heart_selected):
                    context.getResources().getDrawable(R.mipmap.ic_heart_unselected),null,null,null);

            tv_sheep_bar_prised.setText(((SheepBarCommentInfo) info).getPraiseAmount()+"");

            int childAmount = ((SheepBarCommentInfo) info).getChildAmount();
            if (childAmount > 0) {
                //有子评论
                getCommentDataFromServer(context, ((SheepBarCommentInfo) info).getSheepBarMessageId(),
                        ((SheepBarCommentInfo) info).getId(), ((SheepBarCommentInfo) info).isOnlySeeOwner());
            } else {
                mChildAdapter.addAll(null);
            }

            tv_sheep_bar_prised.setTag(info);
            tv_sheep_bar_prised.setOnClickListener(listener);

            tv_sheep_bar_content.setTag(info);
            tv_sheep_bar_content.setOnClickListener(listener);
        }

    }


    /**
     * 获取二级评论信息
     *
     * @param context
     * @param messageid
     * @param commentId
     * @param onlySeeOwner
     */
    private void getCommentDataFromServer(Context context, int messageid, int commentId, boolean onlySeeOwner) {
        String token = UserManager.getInstance(context).getToken();
        if (Util.checkNull(token)) {
            return;
        }

        HashMap<String, Object> param = new HashMap<>();
        param.put("postId", messageid);
        param.put("onlySeeOwner", onlySeeOwner);
        param.put("parentId", commentId); //查二级子评论，这个是一级评论的id
        param.put("orderType", "desc");

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_BAR_COMMENT_LIST_LEVEL2, new BaseCallBack<SheepBarCommentSecondaryInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, SheepBarCommentSecondaryInfoResult secondaryInfoResult) {
                if (secondaryInfoResult == null) {
                    ToastUtil.showToast(response.message());
                    return;
                }
                if (secondaryInfoResult.getCode() != 1) {
                    ToastUtil.showToast(secondaryInfoResult.getMsg());
                    return;
                }
                List<SheepBarCommentSecondaryInfo> datas = secondaryInfoResult.getData();
                if (CommonUtils.isEmpty(datas)) {
                    return;
                }

                mChildAdapter.addAll(datas);

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
     * @param barCommentSecondaryInfo 对应的实体
     * @param interactType            1 给帖子点赞
     *                                2 评论点赞
     *                                3 收藏帖子
     *                                4 分享贴子
     */
    private void postInteractSheepBar(Context context, final int position,
                                      final SheepBarCommentSecondaryInfo barCommentSecondaryInfo,
                                      final int interactType) {
        String token = UserManager.getInstance(context).getToken();
        if (Util.checkNull(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("id", barCommentSecondaryInfo.getId());
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
//                        } else {
//                            sheepBarMessageInfo.setHasPraised(true);
//                            String priseNumber = tvPriseView.getText().toString();
//                        }
//                        mAdapter.notifyItemChanged(position);
                        break;
                    case 2:
                        if (barCommentSecondaryInfo.isHasPraised()) {
                            barCommentSecondaryInfo.setHasPraised(false);
                            int praiseAmount = barCommentSecondaryInfo.getPraiseAmount();
                            barCommentSecondaryInfo.setPraiseAmount(praiseAmount - 1);
                        } else {
                            barCommentSecondaryInfo.setHasPraised(true);
                            int praiseAmount = barCommentSecondaryInfo.getPraiseAmount();
                            barCommentSecondaryInfo.setPraiseAmount(praiseAmount + 1);
                        }
                        mChildAdapter.notifyItemChanged(position);
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
