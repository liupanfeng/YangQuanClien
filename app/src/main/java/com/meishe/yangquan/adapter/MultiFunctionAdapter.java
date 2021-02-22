package com.meishe.yangquan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BUMeesageDataInfo;
import com.meishe.yangquan.bean.BUShopDataInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.BusinessOpportunity;
import com.meishe.yangquan.bean.Comment;
import com.meishe.yangquan.bean.FeedGoodsInfo;
import com.meishe.yangquan.bean.FeedShoppingInfo;
import com.meishe.yangquan.bean.FodderInfo;
import com.meishe.yangquan.bean.HomeMarketPictureInfo;
import com.meishe.yangquan.bean.IndustryInfo;
import com.meishe.yangquan.bean.IndustryNewsClip;
import com.meishe.yangquan.bean.Label;
import com.meishe.yangquan.bean.MarketInfo;
import com.meishe.yangquan.bean.Message;
import com.meishe.yangquan.bean.MineBreedingArchivesInfo;
import com.meishe.yangquan.bean.MineCallbackInfo;
import com.meishe.yangquan.bean.MineMyFansInfo;
import com.meishe.yangquan.bean.MineMyFocusInfo;
import com.meishe.yangquan.bean.MineTypeInfo;
import com.meishe.yangquan.bean.MineUserMessageInfo;
import com.meishe.yangquan.bean.PointRecordInfo;
import com.meishe.yangquan.bean.QuotationInfo;
import com.meishe.yangquan.bean.ServerCustomer;
import com.meishe.yangquan.bean.ServiceInfo;
import com.meishe.yangquan.bean.ServiceMessage;
import com.meishe.yangquan.bean.ServiceTypeInfo;
import com.meishe.yangquan.bean.SheepBarCommentInfo;
import com.meishe.yangquan.bean.SheepBarCommentSecondaryInfo;
import com.meishe.yangquan.bean.SheepBarMessageInfo;
import com.meishe.yangquan.bean.SheepBarPictureInfo;
import com.meishe.yangquan.bean.SheepHairInfo;
import com.meishe.yangquan.bean.SheepLossInfo;
import com.meishe.yangquan.bean.SheepNews;
import com.meishe.yangquan.bean.SheepVaccineInfo;
import com.meishe.yangquan.bean.SystemMessageInfo;
import com.meishe.yangquan.bean.SystemNotification;
import com.meishe.yangquan.viewhoder.BUHomeShopViewHolder;
import com.meishe.yangquan.viewhoder.BUMessageViewHolder;
import com.meishe.yangquan.viewhoder.BaseViewHolder;
import com.meishe.yangquan.viewhoder.BreedingArchivesFoodAnalysisHolder;
import com.meishe.yangquan.viewhoder.BusinessOpportunityListHolder;
import com.meishe.yangquan.viewhoder.CommentListHolder;
import com.meishe.yangquan.viewhoder.FeedFoodsHolder;
import com.meishe.yangquan.viewhoder.FeedShoppingHolder;
import com.meishe.yangquan.viewhoder.MineCallbackHolder;
import com.meishe.yangquan.viewhoder.SheepCutHairHolder;
import com.meishe.yangquan.viewhoder.HomeMarketPictureListHolder;
import com.meishe.yangquan.viewhoder.IndustryContentHolder;
import com.meishe.yangquan.viewhoder.IndustryListHolder;
import com.meishe.yangquan.viewhoder.HomeMarketListHolder;
import com.meishe.yangquan.viewhoder.MessageCenterListHolder;
import com.meishe.yangquan.viewhoder.MessageListHolder;
import com.meishe.yangquan.viewhoder.MineBreedingArchivesHolder;
import com.meishe.yangquan.viewhoder.MineMyFansHolder;
import com.meishe.yangquan.viewhoder.MineMyFocusHolder;
import com.meishe.yangquan.viewhoder.MineMyMessageHolder;
import com.meishe.yangquan.viewhoder.MineMyPointsHolder;
import com.meishe.yangquan.viewhoder.MineSystemMessageHolder;
import com.meishe.yangquan.viewhoder.MineTypeHolder;
import com.meishe.yangquan.viewhoder.QuotationListHolder;
import com.meishe.yangquan.viewhoder.ServiceLabelHolder;
import com.meishe.yangquan.viewhoder.HomeServiceListHolder;
import com.meishe.yangquan.viewhoder.ServiceMessageListHolder;
import com.meishe.yangquan.viewhoder.ServiceSheepNewsHolder;
import com.meishe.yangquan.viewhoder.ServiceMessageHolder;
import com.meishe.yangquan.viewhoder.ServiceTypeHolder;
import com.meishe.yangquan.viewhoder.ServiceTypeListHolder;
import com.meishe.yangquan.viewhoder.SheepBarCommentListHolder;
import com.meishe.yangquan.viewhoder.SheepBarCommentSecondaryListHolder;
import com.meishe.yangquan.viewhoder.SheepBarMessageListHolder;
import com.meishe.yangquan.viewhoder.SheepBarPictureListHolder;
import com.meishe.yangquan.viewhoder.SheepLossHolder;
import com.meishe.yangquan.viewhoder.SheepVaccinesHolder;

public class MultiFunctionAdapter extends BaseRecyclerAdapter {

    public MultiFunctionAdapter(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        BaseViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        switch (viewType) {
            case VIEW_SERVICE_MESSAGE:
                if (pageType == 1) {
                    view = mLayoutInflater.inflate(R.layout.item_service_message, parent, false);
                    viewHolder = new ServiceMessageHolder(view, this);
                } else if (pageType == 2) {
                    view = mLayoutInflater.inflate(R.layout.item_service_message_lsit, parent, false);
                    viewHolder = new ServiceMessageListHolder(view, this);
                }

                break;
            case VIEW_SERVICE_TYPE:
                view = mLayoutInflater.inflate(R.layout.item_service_type, parent, false);
                viewHolder = new ServiceTypeHolder(view, this);
                break;
            case VIEW_SERVICE_TYPE_LIST:
                view = mLayoutInflater.inflate(R.layout.item_service_type_list, parent, false);
                viewHolder = new ServiceTypeListHolder(view, this);
                break;
            case VIEW_MINE_TYPE_LIST:
                view = mLayoutInflater.inflate(R.layout.item_mine_type, parent, false);
                viewHolder = new MineTypeHolder(view, this);
                break;
            case VIEW_MESSAGE_TYPE_LIST:
                view = mLayoutInflater.inflate(R.layout.item_message_type_list, parent, false);
                viewHolder = new MessageListHolder(view, this);
                break;
            case VIEW_SERVICE_NEWS_TYPE_LIST:
                view = mLayoutInflater.inflate(R.layout.item_service_news_type, parent, false);
                viewHolder = new ServiceSheepNewsHolder(view, this);
                break;
            case VIEW_SERVICE_LABEL:
                view = mLayoutInflater.inflate(R.layout.item_label_server, parent, false);
                viewHolder = new ServiceLabelHolder(view, this);
                break;
            case VIEW_MESSAGE_CENTER_LIST:
                view = mLayoutInflater.inflate(R.layout.item_message_center, parent, false);
                viewHolder = new MessageCenterListHolder(view, this);
                break;
            case VIEW_BUSINESS_CENTER_LIST:
                view = mLayoutInflater.inflate(R.layout.item_business_opportunity, parent, false);
                viewHolder = new BusinessOpportunityListHolder(view, this);
                break;
            case VIEW_COMMENT_LIST:
                view = mLayoutInflater.inflate(R.layout.item_comment, parent, false);
                viewHolder = new CommentListHolder(view, this);
                break;
            /*行情列表*/
            case VIEW_QUOTATION_LIST:
                view = mLayoutInflater.inflate(R.layout.item_quotation, parent, false);
                viewHolder = new QuotationListHolder(view, this);
                break;

            /*市场列表*/
            case VIEW_MARKET_LIST:
                view = mLayoutInflater.inflate(R.layout.item_market, parent, false);
                viewHolder = new HomeMarketListHolder(view, this);
                break;
            /*服务列表*/
            case VIEW_SERVICE_LIST:
                view = mLayoutInflater.inflate(R.layout.item_service, parent, false);
                viewHolder = new HomeServiceListHolder(view, this);
                break;
            /*行业资讯列表*/
            case VIEW_INDUSTRY_LIST:
                view = mLayoutInflater.inflate(R.layout.item_industry, parent, false);
                viewHolder = new IndustryListHolder(view, this);
                break;
            /*羊吧图片*/
            case VIEW_SHEEP_BAR_ADD_PIC:
                view = mLayoutInflater.inflate(R.layout.item_sheep_bar, parent, false);
                viewHolder = new SheepBarPictureListHolder(view, this);
                break;
            case VIEW_HOME_MARKET_PICTURE_LIST:
                view = mLayoutInflater.inflate(R.layout.item_home_market, parent, false);
                viewHolder = new HomeMarketPictureListHolder(view, this);
                break;
            /*羊吧信息发布*/
            case VIEW_SHEEP_BAR_MESSAGE:
                view = mLayoutInflater.inflate(R.layout.item_sheep_bar_message, parent, false);
                viewHolder = new SheepBarMessageListHolder(view, this);
                break;
            /*羊吧一级评论列表*/
            case VIEW_COMMENT_LEVEL1_LIST:
                view = mLayoutInflater.inflate(R.layout.item_sheep_bar_comment, parent, false);
                viewHolder = new SheepBarCommentListHolder(view, this);
                break;
            /*羊吧二级评论列表*/
            case VIEW_COMMENT_LEVEL1_CHILD_LIST:
                view = mLayoutInflater.inflate(R.layout.item_sheep_bar_comment, parent, false);
                viewHolder = new SheepBarCommentSecondaryListHolder(view, this);
                break;
            /*资讯详情页内容*/
            case VIEW_INDUSTRY_CONTENT_LIST:
                view = mLayoutInflater.inflate(R.layout.item_industry_content, parent, false);
                viewHolder = new IndustryContentHolder(view, this);
                break;
            /*剪羊毛内容*/
            case VIEW_CUT_SHEEP_HAIR_LIST:
                view = mLayoutInflater.inflate(R.layout.item_cut_sheep_hair, parent, false);
                viewHolder = new SheepCutHairHolder(view, this);
                break;
            /*养殖助手-损耗列表*/
            case VIEW_CUT_SHEEP_LOSS_LIST:
                view = mLayoutInflater.inflate(R.layout.item_sheep_loss, parent, false);
                viewHolder = new SheepLossHolder(view, this);
                break;
            /*养殖助手-疫苗列表*/
            case VIEW_CUT_SHEEP_VACCINE_LIST:
                view = mLayoutInflater.inflate(R.layout.item_sheep_vaccine, parent, false);
                viewHolder = new SheepVaccinesHolder(view, this);
                break;
            /*我的积分记录*/
            case VIEW_MINE_MY_POINTS_LIST:
                view = mLayoutInflater.inflate(R.layout.item_mine_my_points, parent, false);
                viewHolder = new MineMyPointsHolder(view, this);
                break;
            /*我的系统消息*/
            case VIEW_MINE_SUYSTEM_MESSAGE_LIST:
                view = mLayoutInflater.inflate(R.layout.item_mine_system_message, parent, false);
                viewHolder = new MineSystemMessageHolder(view, this);
                break;

            /*我的消息*/
            case VIEW_MINE_USER_MESSAGE_LIST:
                view = mLayoutInflater.inflate(R.layout.item_mine_my_message, parent, false);
                viewHolder = new MineMyMessageHolder(view, this);
                break;
            /*我的-我的粉丝*/
            case VIEW_MINE_MY_FANS_LIST:
                view = mLayoutInflater.inflate(R.layout.item_mine_my_fans, parent, false);
                viewHolder = new MineMyFansHolder(view, this);
                break;
            /*我的-我的关注*/
            case VIEW_MINE_MY_FOCUS_LIST:
                view = mLayoutInflater.inflate(R.layout.item_mine_my_focus, parent, false);
                viewHolder = new MineMyFocusHolder(view, this);
                break;
            /*我的-我的反馈*/
            case VIEW_MINE_CALLBACK_LIST:
                view = mLayoutInflater.inflate(R.layout.item_mine_my_callback, parent, false);
                viewHolder = new MineCallbackHolder(view, this);
                break;
            /*我的-养殖档案*/
            case VIEW_MINE_BREEDING_ARCHIVE_LIST:
                view = mLayoutInflater.inflate(R.layout.item_mine_breeding_archives, parent, false);
                viewHolder = new MineBreedingArchivesHolder(view, this);
                break;

            /*养殖助手-配料分析*/
            case VIEW_SHEEP_BREEDING_FOOD_ANALYSIS_LIST:
                view = mLayoutInflater.inflate(R.layout.item_breeding_archives_food_analysis, parent, false);
                viewHolder = new BreedingArchivesFoodAnalysisHolder(view, this);
                break;

            /*饲料商店列表*/
            case VIEW_FEED_SHOPPING_LIST:
                view = mLayoutInflater.inflate(R.layout.item_feed_shopping, parent, false);
                viewHolder = new FeedShoppingHolder(view, this);
                break;

            /*饲料商品列表*/
            case VIEW_FEED_FOODS_LIST:
                view = mLayoutInflater.inflate(R.layout.item_feed_foods, parent, false);
                viewHolder = new FeedFoodsHolder(view, this);
                break;



            /*商版-店铺数据*/
            case VIEW_BU_HOME_SHOPDATA_LIST:
                view = mLayoutInflater.inflate(R.layout.item_bu_home_shop_view, parent, false);
                viewHolder = new BUHomeShopViewHolder(view, this);
                break;
            /*商版-消息数据*/
            case VIEW_BU_MESSAGE_LIST:
                view = mLayoutInflater.inflate(R.layout.item_bu_message_view, parent, false);
                viewHolder = new BUMessageViewHolder(view, this);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        BaseInfo baseInfo;
        if (isNeedAutoScroll()) {
            baseInfo = getItem(0);
        } else {
            baseInfo = getItem(position);
        }
        if (baseInfo instanceof ServiceMessage) {
            return VIEW_SERVICE_MESSAGE;
        } else if (baseInfo instanceof ServiceTypeInfo) {
            return VIEW_SERVICE_TYPE;
        } else if (baseInfo instanceof ServerCustomer) {
            return VIEW_SERVICE_TYPE_LIST;
        } else if (baseInfo instanceof MineTypeInfo) {
            return VIEW_MINE_TYPE_LIST;
        } else if (baseInfo instanceof Message) {
            return VIEW_MESSAGE_TYPE_LIST;
        } else if (baseInfo instanceof SheepNews) {
            return VIEW_SERVICE_NEWS_TYPE_LIST;
        } else if (baseInfo instanceof Label) {
            return VIEW_SERVICE_LABEL;
        } else if (baseInfo instanceof SystemNotification) {
            return VIEW_MESSAGE_CENTER_LIST;
        } else if (baseInfo instanceof BusinessOpportunity) {
            return VIEW_BUSINESS_CENTER_LIST;
        } else if (baseInfo instanceof Comment) {
            return VIEW_COMMENT_LIST;
        } else if (baseInfo instanceof QuotationInfo) {
            return VIEW_QUOTATION_LIST;
        } else if (baseInfo instanceof MarketInfo) {
            return VIEW_MARKET_LIST;
        } else if (baseInfo instanceof IndustryInfo) {
            return VIEW_INDUSTRY_LIST;
        } else if (baseInfo instanceof SheepBarPictureInfo) {
            return VIEW_SHEEP_BAR_ADD_PIC;
        } else if (baseInfo instanceof SheepBarMessageInfo) {
            return VIEW_SHEEP_BAR_MESSAGE;
        } else if (baseInfo instanceof ServiceInfo) {
            return VIEW_SERVICE_LIST;
        } else if (baseInfo instanceof SheepBarCommentInfo) {
            return VIEW_COMMENT_LEVEL1_LIST;
        } else if (baseInfo instanceof SheepBarCommentSecondaryInfo) {
            return VIEW_COMMENT_LEVEL1_CHILD_LIST;
        } else if (baseInfo instanceof IndustryNewsClip) {
            return VIEW_INDUSTRY_CONTENT_LIST;
        } else if (baseInfo instanceof SheepHairInfo) {
            return VIEW_CUT_SHEEP_HAIR_LIST;
        } else if (baseInfo instanceof PointRecordInfo) {
            return VIEW_MINE_MY_POINTS_LIST;
        } else if (baseInfo instanceof SystemMessageInfo) {
            return VIEW_MINE_SUYSTEM_MESSAGE_LIST;
        } else if (baseInfo instanceof HomeMarketPictureInfo) {
            return VIEW_HOME_MARKET_PICTURE_LIST;
        }else if (baseInfo instanceof BUShopDataInfo) {
            return VIEW_BU_HOME_SHOPDATA_LIST;
        }else if (baseInfo instanceof MineBreedingArchivesInfo) {
            return VIEW_MINE_BREEDING_ARCHIVE_LIST;
        }else if (baseInfo instanceof FodderInfo) {
            return VIEW_SHEEP_BREEDING_FOOD_ANALYSIS_LIST;
        }else if (baseInfo instanceof MineMyFansInfo) {
            return VIEW_MINE_MY_FANS_LIST;
        }else if (baseInfo instanceof MineMyFocusInfo) {
            return VIEW_MINE_MY_FOCUS_LIST;
        }else if (baseInfo instanceof SheepLossInfo) {
            return VIEW_CUT_SHEEP_LOSS_LIST;
        }else if (baseInfo instanceof SheepVaccineInfo) {
            return VIEW_CUT_SHEEP_VACCINE_LIST;
        }else if (baseInfo instanceof MineCallbackInfo) {
            return VIEW_MINE_CALLBACK_LIST;
        }else if (baseInfo instanceof MineUserMessageInfo) {
            return VIEW_MINE_USER_MESSAGE_LIST;
        }else if (baseInfo instanceof BUMeesageDataInfo) {
            return VIEW_BU_MESSAGE_LIST;
        } else if (baseInfo instanceof FeedShoppingInfo) {
            return VIEW_FEED_SHOPPING_LIST;
        }else if (baseInfo instanceof FeedGoodsInfo) {
            return VIEW_FEED_FOODS_LIST;
        }
        return super.getItemViewType(position);
    }


}
