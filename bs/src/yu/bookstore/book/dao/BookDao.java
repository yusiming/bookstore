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
        String sql = "select * from book where del=false";
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
        String sql = "select * from book where cid=? and del=false";
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

    /**
     * @Description: 向数据库中添加图书
     * @auther: yusiming
     * @date: 23:09 2018/9/10
     * @param: [book]
     * @return: void
     */
    public void addBook(Book book) {
        String sql = "insert into book values(?,?,?,?,?,?,?)";
        Object[] params = {book.getBid(), book.getBname(), book.getPrice(), book.getAuthor(), book.getImage(), book
                .getCategory().getCid(), 0};
        try {
            queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 删除图书
     * @auther: yusiming
     * @date: 21:42 2018/9/11
     * @param: [bid]
     * @return: void
     */
    public void delete(String bid) {
        String sql = "update book set del=true where bid=?";
        try {
            queryRunner.update(sql, bid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 修改图书 信息
     * @auther: yusiming
     * @date: 22:13 2018/9/11
     * @param: [book]
     * @return: void
     */
    public void edit(Book book) {
        String sql = "update book set bname=?,price=?,author=?,cid=? where bid=?";
        Object[] params = {book.getBname(), book.getPrice(), book.getAuthor(), book
                .getCategory().getCid(), book.getBid()};
        try {
            queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
