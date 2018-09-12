package yu.bookstore.order.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import yu.bookstore.book.domain.Book;
import yu.bookstore.order.domain.Order;
import yu.bookstore.order.domain.OrderItem;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            txQueryRunner.batch(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 根据用户uid查询所有订单
     * @auther: yusiming
     * @date: 21:35 2018/9/7
     * @param: []
     * @return: java.util.List<yu.bookstore.order.domain.Order>
     */
    public List<Order> findOrdersByUid(String uid) {
        /*
         * 1.根据uid得到所有订单
         * 2.为每一个订单添加订单条目
         * 3.返回所有订单
         */
        String sql = "select * from orders where uid=?";
        try {
            List<Order> orderList = txQueryRunner.query(sql, new BeanListHandler<>(Order.class), uid);
            for (Order order : orderList) {
                // 为每一个订单添加订单条目
                setOrderItemsToOrder(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 为OrderList中的每一个Order添加订单条目
     * @auther: yusiming
     * @date: 21:42 2018/9/7
     * @param: [orderList]
     * @return: void
     */
    private void setOrderItemsToOrder(Order order) {
        /*
         * 1.循环遍历List，得到每一个订单的oid
         * 2.根据oid查询OrderItem得到OrderItemList
         * 3.为每一个OrderItem设置Book
         * 3.为Order设置OrderItemList
         */
        String sql = "select * from orderitem i,book b where i.bid = b.bid and oid=?";
        try {
            List<Map<String, Object>> mapList = txQueryRunner.query(sql, new MapListHandler(), order.getOid());
            List<OrderItem> orderItemList = getOrderItemList(mapList);
            order.setOrderItemList(orderItemList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 将mapList转换为OrderItemList
     * @auther: yusiming
     * @date: 22:11 2018/9/7
     * @param: [mapList]
     * @return: java.util.List<yu.bookstore.order.domain.OrderItem>
     */
    private List<OrderItem> getOrderItemList(List<Map<String, Object>> mapList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
            Book book = CommonUtils.toBean(map, Book.class);
            orderItem.setBook(book);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    /**
     * @Description: 根据oid查询Order
     * @auther: yusiming
     * @date: 23:06 2018/9/7
     * @param: [oid]
     * @return: yu.bookstore.order.domain.Order
     */
    public Order loadOrder(String oid) {
        String sql = "select * from orders where oid=?";
        try {
            Order order = txQueryRunner.query(sql, new BeanHandler<>(Order.class), oid);
            // 设置orderItems
            setOrderItemsToOrder(order);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 将订单的状态+1
     * @auther: yusiming
     * @date: 23:38 2018/9/7
     * @param: [oid, state]
     * @return: void
     */
    public void changeOrderState(String oid) {
        String sql = "update orders set state=state+1 where oid=?";
        try {
            txQueryRunner.update(sql, oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 根据oid查询订单状态
     * @auther: yusiming
     * @date: 23:50 2018/9/7
     * @param: []
     * @return: int
     */
    public int getOrderStateByOid(String oid) {
        String sql = "select state from orders where oid=?";
        try {
            return (Integer) txQueryRunner.query(sql, new ScalarHandler(), oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 查询所有订单信息
     * @auther: yusiming
     * @date: 22:26 2018/9/12
     * @param: []
     * @return: java.util.List<yu.bookstore.order.domain.Order>
     */
    public List<Order> findAll() {
        String sql = "select * from orders";
        try {
            List<Order> orderList = txQueryRunner.query(sql, new BeanListHandler<>(Order.class));
            for (Order order : orderList) {
                setOrderItemsToOrder(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 根据订单状态查询订单
     * @auther: yusiming
     * @date: 23:00 2018/9/12
     * @param: [state]
     * @return: java.util.List<yu.bookstore.order.domain.Order>
     */
    public List<Order> findUnpaidOrders(int state) {
        String sql = "select * from orders where state=?";
        try {
            List<Order> orderList = txQueryRunner.query(sql, new BeanListHandler<>(Order.class), state);
            for (Order order : orderList) {
                setOrderItemsToOrder(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
