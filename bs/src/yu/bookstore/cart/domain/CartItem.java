package yu.bookstore.cart.domain;

import yu.bookstore.book.domain.Book;

import java.math.BigDecimal;

/**
 * @Auther: yusiming
 * @Date: 2018/9/5 20:30
 * @Description: 购物车条目类
 */
public class CartItem {
    // 图书
    private Book book;
    // 数量
    private int count;

    // 小计
    public double getSubtotal() {
        // 防止二进制误差问题，使用BigDecimal类
        return new BigDecimal(book.getPrice() + "").multiply(new BigDecimal(count + "")).doubleValue();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
