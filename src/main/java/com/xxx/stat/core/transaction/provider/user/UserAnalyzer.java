package com.xxx.stat.core.transaction.provider.user;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.api.stat.StatController;
import com.xxx.stat.core.stat.utils.StatDateUtils;
import com.xxx.stat.core.transaction.provider.ISummaryAnalyzer;
import com.xxx.stat.core.transaction.provider.SummaryType;
import com.xxx.stat.api.transaction.res.model.TransactionElement;
import com.xxx.stat.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户统计信息
 */
@Service
public class UserAnalyzer extends ISummaryAnalyzer {
    @Autowired
    private StatController statController;

    @Override
    public SummaryType getSummaryType() {
        return SummaryType.A_USER;
    }

    @Override
    public TransactionElement[] getTransactionElement(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return new TransactionElement[]{
                new TransactionElement("注册用户", "统计周期内，完成注册的用户数", statController.getSizeOfTotal(StatType.USER_REG, platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("活跃用户", "统计周期内，有登录网站或APP的用户数", statController.getSizeOfTotal(StatType.USER_LOGIN, platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("交易用户", "统计周期内，有支付行为的用户数", statController.getSizeOfTotal(StatType.USER_BUY_NUM, platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("用户总数", "注册的用户总数", statController.getSizeOfTotal(StatType.USER_REG, platform, retailerUuid, null, StatDateUtils.dateToInteger(StatDateUtils.integerToDayBetween(20190101).getDayStart()), StatDateUtils.dateToInteger(DateUtil.now())))
        };
    }
}
