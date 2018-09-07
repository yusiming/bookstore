package yu.bookstore.order.service;

import cn.itcast.jdbc.JdbcUtils;
import yu.bookstore.order.dao.OrderDao;
import yu.bookstore.order.domain.Order;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: yusiming
 * @Date: 2018/9/5 22:49
 * @Description: 业务层，
 */
public class OrderService {
    private OrderDao orderDao = new OrderDao();

    /**
     * @Description: 添加顶单，需要处理事务，添加订单和添加订单条目是一个事务
     * @auther: yusiming
     * @date: 21:42 2018/9/6
     * @param: []
     * @return: void
     */
    public void add(Order order) {
        try {
            // 开启事务
            JdbcUtils.beginTransaction();
            // 添加订单
            orderDao.addOrder(order);
            // 添加订单条目
            orderDao.addOrderItemList(order.getOrderItemList());
            // 提交事务
            JdbcUtils.commitTransaction();
        } catch (SQLException e) {
            try {
                // 回滚事务
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 根据用户的所有订单
     * @auther: yusiming
     * @date: 21:34 2018/9/7
     * @param: [uid]
     * @return: java.util.List<yu.bookstore.order.domain.Order>
     */
    public List<Order> myOrders(String uid) {
        return orderDao.findOrdersByUid(uid);
    }
}
