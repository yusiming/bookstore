package yu.bookstore.book.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import yu.bookstore.book.domain.Book;
import yu.bookstore.category.domain.Category;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
     * @Description: 根据bid 查询单个图书的详细信息，并为其设置Category对象
     * @auther: yusiming
     * @date: 13:06 2018/9/5
     * @param: [bid]
     * @return: yu.bookstore.book.domain.Book
     */
    public Book load(String bid) {
        String sql = "select * from book where bid=?";
        try {
            Map<String, Object> map = queryRunner.query(sql, new MapHandler(), bid);
            Book book = CommonUtils.toBean(map, Book.class);
            Category category = CommonUtils.toBean(map, Category.class);
            book.setCategory(category);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
