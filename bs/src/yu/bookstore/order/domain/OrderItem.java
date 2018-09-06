package yu.bookstore.order.domain;

import yu.bookstore.book.domain.Book;

/**
 * @Auther: yusiming
 * @Date: 2018/9/5 22:42
 * @Description:
 */
public class OrderItem {
    private String iid;
    // 数量
    private int count;
    // 小计
    private double subtotal;
    // 所属订单
    private Order order;
    // 要购买的图书
    private Book book;

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
