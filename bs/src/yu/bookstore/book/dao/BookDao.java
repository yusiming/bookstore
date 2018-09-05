package yu.bookstore.book.dao;

import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import yu.bookstore.book.domain.Book;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: yusiming
 * @Date: 2018/9/4 23:43
 * @Description:
 */
public class BookDao {
    private TxQueryRunner queryRunner = new TxQueryRunner();

    /**
     * @Description: 查询所有图书
     * @auther: yusiming
     * @date: 13:05 2018/9/5
     * @param: []
     * @return: java.util.List<yu.bookstore.book.domain.Book>
     */
    public List<Book> findAll() {
        String sql = "select * from book";
        try {
            return queryRunner.query(sql, new BeanListHandler<>(Book.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 根据图书分类查询图书
     * @auther: yusiming
     * @date: 13:06 2018/9/5
     * @param: [cid]
     * @return: java.util.List<yu.bookstore.book.domain.Book>
     */
    public List<Book> findByCategory(String cid) {
        String sql = "select * from book where cid=?";
        try {
            return queryRunner.query(sql, new BeanListHandler<>(Book.class), cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 根据bid 查询单个图书的详细信息
     * @auther: yusiming
     * @date: 13:06 2018/9/5
     * @param: [bid]
     * @return: yu.bookstore.book.domain.Book
     */
    public Book load(String bid) {
        String sql = "select * from book where bid=?";
        try {
            return queryRunner.query(sql, new BeanHandler<>(Book.class), bid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
