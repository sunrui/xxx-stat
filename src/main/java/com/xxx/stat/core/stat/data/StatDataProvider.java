package com.xxx.stat.core.stat.data;

import com.xxx.stat.core.stat.data.node.StatGroup;
import com.xxx.stat.core.stat.data.node.StatGroupNode;
import com.xxx.stat.core.stat.provider.RetailerPair;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.StatUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 基础数据服务提供者
 */
@Service
public class StatDataProvider {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取商户对象
     *
     * @return 商户对象
     */
    public Collection<RetailerPair> getAllRetailerPair() {
        String sql = "select uuid,email as name from mms_retailer ";
        ArrayList<RetailerPair> retailerPairs = new ArrayList<>();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list) {
            RetailerPair retailerPair = new RetailerPair(map.get("uuid").toString(), map.get("name").toString());
            retailerPairs.add(retailerPair);
        }
        return retailerPairs;
    }

    /**
     * 获取所有统计类型
     *
     * @return 统计类型
     */
    public StatGroup[] getAllStatGroup() {
        return new StatGroup[]{
                new StatGroup("用户", new StatGroupNode[]{
                        new StatGroupNode(StatType.USER_REG, StatUnit.SIZE, StatType.USER_REG.getComment()),
                        new StatGroupNode(StatType.USER_LOGIN, StatUnit.SIZE, StatType.USER_LOGIN.getComment()),
                        new StatGroupNode(StatType.USER_ADD_CART, StatUnit.SIZE, StatType.USER_ADD_CART.getComment()),
                        new StatGroupNode(StatType.USER_CHECKOUT_NUM, StatUnit.SIZE, StatType.USER_CHECKOUT_NUM.getComment())
                }),
                new StatGroup("商户", new StatGroupNode[]{
                        new StatGroupNode(StatType.MERCHANT_REG, StatUnit.SIZE, StatType.MERCHANT_REG.getComment()),
                        new StatGroupNode(StatType.MERCHANT_ACTIVE, StatUnit.SIZE, StatType.MERCHANT_ACTIVE.getComment()),
                        new StatGroupNode(StatType.MERCHANT_SALE, StatUnit.PRICE, StatType.MERCHANT_SALE.getComment())
                }),
                new StatGroup("商品", new StatGroupNode[]{
                        new StatGroupNode(StatType.PRODUCT_PUTON, StatUnit.SIZE, StatType.PRODUCT_PUTON.getComment()),
                        new StatGroupNode(StatType.PRODUCT_PUTOFF, StatUnit.SIZE, StatType.PRODUCT_PUTOFF.getComment())
                }),
                new StatGroup("订单", new StatGroupNode[]{
                        new StatGroupNode(StatType.ORDER_UNPAY_NUM, StatUnit.SIZE, StatType.ORDER_UNPAY_NUM.getComment()),
                        new StatGroupNode(StatType.ORDER_PAY_NUM, StatUnit.SIZE, StatType.ORDER_PAY_NUM.getComment()),
                        new StatGroupNode(StatType.ORDER_PAY_AMT, StatUnit.PRICE, StatType.ORDER_PAY_AMT.getComment()),
                        new StatGroupNode(StatType.ORDER_WAIT_SHIP_NUM, StatUnit.SIZE, StatType.ORDER_WAIT_SHIP_NUM.getComment()),
                        new StatGroupNode(StatType.ORDER_WAIT_SHIP_AMT, StatUnit.PRICE, StatType.ORDER_WAIT_SHIP_AMT.getComment()),
                        new StatGroupNode(StatType.ORDER_IN_SHIP_NUM, StatUnit.SIZE, StatType.ORDER_IN_SHIP_NUM.getComment()),
                        new StatGroupNode(StatType.ORDER_IN_SHIP_AMT, StatUnit.PRICE, StatType.ORDER_IN_SHIP_AMT.getComment()),
                        new StatGroupNode(StatType.ORDER_DELIVERED_NUM, StatUnit.SIZE, StatType.ORDER_DELIVERED_NUM.getComment()),
                        new StatGroupNode(StatType.ORDER_DELIVERED_AMT, StatUnit.PRICE, StatType.ORDER_DELIVERED_AMT.getComment()),
                        new StatGroupNode(StatType.ORDER_RECEIVED_NUM, StatUnit.SIZE, StatType.ORDER_RECEIVED_NUM.getComment()),
                        new StatGroupNode(StatType.ORDER_RECEIVED_AMT, StatUnit.PRICE, StatType.ORDER_RECEIVED_AMT.getComment()),
                        new StatGroupNode(StatType.ORDER_SKU_PAY, StatUnit.SIZE, StatType.ORDER_SKU_PAY.getComment()),
                        new StatGroupNode(StatType.ORDER_SKU_REFUNDED_AMT, StatUnit.PRICE, StatType.ORDER_SKU_REFUNDED_AMT.getComment()),
                        new StatGroupNode(StatType.ORDER_SKU_REFUNDED_NUM, StatUnit.SIZE, StatType.ORDER_SKU_REFUNDED_NUM.getComment()),
                        new StatGroupNode(StatType.ORDER_PRODUCT_REFUNDED_NUM, StatUnit.SIZE, StatType.ORDER_PRODUCT_REFUNDED_NUM.getComment()),
                        new StatGroupNode(StatType.ORDER_REFUNDED, StatUnit.SIZE, StatType.ORDER_REFUNDED.getComment()),

                }),
                new StatGroup("物流", new StatGroupNode[]{
                        new StatGroupNode(StatType.LOGISTICS_NEW, StatUnit.SIZE, StatType.LOGISTICS_NEW.getComment()),
                        new StatGroupNode(StatType.LOGISTICS_FINISH, StatUnit.SIZE, StatType.LOGISTICS_FINISH.getComment())
                }),
        };
    }
}
