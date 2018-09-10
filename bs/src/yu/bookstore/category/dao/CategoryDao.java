package yu.bookstore.category.dao;

import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
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

    /**
     * @Description: 添加图书分类
     * @auther: yusiming
     * @date: 21:30 2018/9/9
     * @param: [category]
     * @return: void
     */
    public void add(Category category) {
        String sql = "insert into category values(?,?)";
        try {
            queryRunner.update(sql, category.getCid(), category.getCname());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 根据cname查询分类，返回Category对象
     * @auther: yusiming
     * @date: 21:40 2018/9/9
     * @param: [cname]
     * @return: yu.bookstore.category.domain.Category
     */
    public Category findCategoryByCname(String cname) {
        String sql = "select * from category where cname=?";
        try {
            return queryRunner.query(sql, new BeanHandler<>(Category.class), cname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 根据cname删除图书分类
     * @auther: yusiming
     * @date: 21:55 2018/9/9
     * @param: [cname]
     * @return: void
     */
    public void deleteCategory(String cid) {
        String sql = "delete from category where cid=?";
        try {
            queryRunner.update(sql, cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 根据cid查询份分类信息
     * @auther: yusiming
     * @date: 19:57 2018/9/10
     * @param: [cid]
     * @return: yu.bookstore.category.domain.Category
     */
    public Category loadCategory(String cid) {
        String sql = "select * from category where cid=?";
        try {
            return queryRunner.query(sql, new BeanHandler<>(Category.class), cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
