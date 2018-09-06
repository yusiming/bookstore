package yu.bookstore.order.domain;

import yu.bookstore.user.domain.User;

import java.util.Date;
import java.util.List;

/**
 * @Auther: yusiming
 * @Date: 2018/9/5 22:34
 * @Description:
 */
public class Order {
    // 订单编号
    private String oid;
    // 下单时间
    private Date ordertime;
    // 合计金额
    private double total;
    // 订单状态 ，未付款、已付款未发货、已发货未确认收货、交易成功
    private int state;
    // 下单人
    private User owner;
    // 地址
    private String address;
    // 当前订单下所有条目
    private List<OrderItem> orderItemList;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
