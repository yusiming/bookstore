package yu.bookstore.category.dao;

import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import yu.bookstore.category.domain.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: yusiming
 * @Date: 2018/9/4 23:06
 * @Description: dao层
 */
public class CategoryDao {
    private TxQueryRunner queryRunner = new TxQueryRunner();

    /**
     * @Description: 查询所有图书分类
     * @auther: yusiming
     * @date: 12:59 2018/9/5
     * @param: []
     * @return: java.util.List<yu.bookstore.category.domain.Category>
     */
    public List<Category> findAll() {
        String sql = "select * from category";
        try {
            return queryRunner.query(sql, new BeanListHandler<>(Category.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
