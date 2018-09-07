package yu.bookstore.order.service;

/**
 * @Auther: yusiming
 * @Date: 2018/9/7 23:34
 * @Description:
 */
public class OrderException extends Exception {
    public OrderException() {
        super();
    }

    public OrderException(String message) {
        super(message);
    }
}
