package yu.bookstore.book.dao;

import cn.itcast.jdbc.TxQueryRunner;
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
    public List<Book> findAll() {
        String sql = "select * from book";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Book.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> findByCategory(String cid) {
        String sql = "select * from book where cid=?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Book.class),cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
