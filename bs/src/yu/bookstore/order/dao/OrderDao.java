package yu.bookstore.order.dao;

import cn.itcast.jdbc.TxQueryRunner;
import yu.bookstore.order.domain.Order;
import yu.bookstore.order.domain.OrderItem;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Auther: yusiming
 * @Date: 2018/9/5 22:49
 * @Description:
 */
public class OrderDao {
    private TxQueryRunner txQueryRunner = new TxQueryRunner();

    /**
     * @Description: 插入订单
     * @auther: yusiming
     * @date: 21:16 2018/9/6
     * @param: [order]
     * @return: void
     */
    public void addOrder(Order order) {
        String sql = "insert into orders values(?,?,?,?,?,?)";
        // 处理时间转换问题
        Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
        Object[] params = {order.getOid(), timestamp, order.getTotal(), order.getState(), order.getOwner()
                .getUid(), order.getAddress()};
        try {
            txQueryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 插入订单条目，可能有多个，需要使用批处理
     * @auther: yusiming
     * @date: 21:30 2018/9/6
     * @param: []
     * @return: void
     */
    public void addOrderItemList(List<OrderItem> orderItemList) {
        String sql = "insert into orderitem values(?,?,?,?,?)";
        // 一共有orderItemList.size() 个一维数组
        Object[][] params = new Object[orderItemList.size()][];
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            params[i] = new Object[]{orderItem.getIid(), orderItem.getCount(), orderItem.getSubtotal(), orderItem
                    .getOrder().getOid(), orderItem.getBook().getBid()};
        }
        try {
            // 执行批处理
            // batch方法的参数，(String sql,Object[][] params)，对于二维数组中的每一个一维数组，都使用该sql模板执行一遍
            txQueryRunner.batch("", params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
