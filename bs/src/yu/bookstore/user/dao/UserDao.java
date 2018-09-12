package yu.bookstore.user.dao;

import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import yu.bookstore.user.domain.Admin;
import yu.bookstore.user.domain.User;

import java.sql.SQLException;

/**
 * @Auther: yusiming
 * @Date: 2018/9/3 21:07
 * @Description: User的数据库持久层
 */
public class UserDao {
    // 使用TxQueryRunner对象进行数据查询
    private TxQueryRunner queryRunner = new TxQueryRunner();

    /**
     * @Description: 根据用户名称查询用户
     * @auther: yusiming
     * @date: 22:45 2018/9/3
     * @param: [user]
     * @return: yu.bookstore.user.domain.User
     */
    public User findByUsername(String user) {
        String sql = "select * from tb_user where username=?";
        try {
            return queryRunner.query(sql, new BeanHandler<>(User.class), user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 根据邮箱地址查询用户
     * @auther: yusiming
     * @date: 22:45 2018/9/3
     * @param: [user]
     * @return: yu.bookstore.user.domain.User
     */
    public User findByEmail(String email) {
        String sql = "select * from tb_user where email=?";
        try {
            return queryRunner.query(sql, new BeanHandler<>(User.class), email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 注册用户，将用户信息添加到数据库中
     * @auther: yusiming
     * @date: 22:53 2018/9/3
     * @param: [user]
     * @return: void
     */
    public void add(User user) {
        String sql = "insert into tb_user values(?,?,?,?,?,?)";
        Object[] params = {user.getUid(), user.getUsername(), user.getPassword(), user.getEmail(), user.getCode(),
                user.isState()};
        try {
            queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 根据激活码查询用户
     * @auther: yusiming
     * @date: 20:18 2018/9/4
     * @param: [code]
     * @return: yu.bookstore.user.domain.User
     */
    public User findByCode(String code) {
        String sql = "select * from tb_user where code=?";
        try {
            return queryRunner.query(sql, new BeanHandler<>(User.class), code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 根据uid更改用户的状态
     * @auther: yusiming
     * @date: 20:21 2018/9/4
     * @param: [uid, state]
     * @return: void
     */
    public void updateState(String uid, boolean state) {
        String sql = "update tb_user set state=? where uid=?";
        try {
            queryRunner.update(sql, state, uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 管理员登陆方法
     * @auther: yusiming
     * @date: 23:41 2018/9/12
     * @param: [username, password]
     * @return: void
     */
    public Admin findByUsername(String username, String password) {
        String sql = "select * from adminuser where username=? and password=?";
        try {
            return queryRunner.query(sql, new BeanHandler<>(Admin.class), username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
