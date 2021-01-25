package com.meishe.yangquan.bean;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/23
 * @Description : 行情历史走势数据
 */
public class QuotationHistoryInfo {

// "data": {
//        "history": {
//            "prices": [
//            30,
//                    36,
//                    0.48,
//                    0
//            ],
//            "dates": [
//            "2020-12-16",
//                    "2020-12-17",
//                    "2020-12-20",
//                    "2021-01-09"
//            ]
//        },
//        "statistics": {
//            "yesterday": 36,
//                    "average": 22.16,
//                    "min": 0.48,
//                    "max": 36,
//                    "today": 30
//        }
//    }

    private HistoryInfo history;


    private StatisticsInfo statistics;


    public HistoryInfo getHistory() {
        return history;
    }

    public void setHistory(HistoryInfo history) {
        this.history = history;
    }

    public StatisticsInfo getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsInfo statistics) {
        this.statistics = statistics;
    }
}
