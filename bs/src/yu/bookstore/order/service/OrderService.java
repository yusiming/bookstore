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

    /**
     * @Description: 加载单个订单
     * @auther: yusiming
     * @date: 23:15 2018/9/7
     * @param: [oid]
     * @return: yu.bookstore.order.domain.Order
     */
    public Order loadOrder(String oid) {
        return orderDao.loadOrder(oid);
    }

    /**
     * @Description: 订单确认收货
     * @auther: yusiming
     * @date: 23:32 2018/9/7
     * @param: [oid]
     * @return: void
     */
    public void confirmReceipt(String oid) throws OrderException {
        /*
         * 1.得到order
         * 2.若state为3，修改状态
         * 3.若不为3，抛异常
         */
        if (orderDao.getOrderStateByOid(oid) == 3) {
            orderDao.changeOrderState(oid);
        } else {
            throw new OrderException("确认收货失败");
        }
    }

    /**
     * @Description: 若订单状态为1，修改为2
     * @auther: yusiming
     * @date: 23:40 2018/9/8
     * @param: [r6_order]
     * @return: void
     */
    public void pay(String r6_order) {
        if (orderDao.getOrderStateByOid(r6_order) == 1) {
            orderDao.changeOrderState(r6_order);
        }
    }

    /**
     * @Description: 后台管理查询所有订单
     * @auther: yusiming
     * @date: 22:25 2018/9/12
     * @param: []
     * @return: java.util.List<yu.bookstore.order.domain.Order>
     */
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    /**
     * @Description: 查询所有未付款订单
     * @auther: yusiming
     * @date: 22:58 2018/9/12
     * @param: []
     * @return: java.util.List<yu.bookstore.order.domain.Order>
     */
    public List<Order> findUnpaidOrders(int state) {
        return orderDao.findUnpaidOrders(state);
    }

    /**
     * @Description: 发货
     * @auther: yusiming
     * @date: 23:17 2018/9/12
     * @param: [oid]
     * @return: void
     */
    public void Shipping(String oid) {
        orderDao.changeOrderState(oid);
    }
}
