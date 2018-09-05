package yu.bookstore.user.service;

import yu.bookstore.user.dao.UserDao;
import yu.bookstore.user.domain.User;

/**
 * @Auther: yusiming
 * @Date: 2018/9/3 21:08
 * @Description: User业务层
 */
public class UserService {
    // 依赖UserDao
    private UserDao userDao = new UserDao();

    /**
     * @Description: 用户的注册,
     * @auther: yusiming
     * @date: 20:25 2018/9/4
     * @param: [form]
     * @return: void
     */
    public void regist(User form) throws UserException {
        // 校验用户名是否已被注册
        if (userDao.findByUsername(form.getUsername()) != null) {
            throw new UserException("用户名已经被注册了！");
        }
        // 校验email是否已被注册
        if (userDao.findByEmail(form.getEmail()) != null) {
            throw new UserException("用户邮箱已经被注册了");
        }
        // 注册用户
        userDao.add(form);
    }

    /**
     * @Description: 用户注册之后的激活
     * @auther: yusiming
     * @date: 20:27 2018/9/4
     * @param: []
     * @return: void
     */
    public void active(String code) throws UserException {
        User user = userDao.findByCode(code);
        if (user == null) {
            throw new UserException("激活码错误！");
        }
        // 查看用户状态避免重复激活
        if (user.isState()) {
            throw new UserException("您的账户已经激活了！");
        }
        userDao.updateState(user.getUid(), true);
    }

    /**
     * @Description: 用户登陆方法
     * @auther: yusiming
     * @date: 21:43 2018/9/4
     * @param: [form]
     * @return: void
     */
    public User login(User form) throws UserException {
        /*
         * 1.得到用户名和密码
         * 2.根据用户名查询用户
         * 3.校验密码
         * 4.校验用户状态
         * 5.返回user对象
         */
        String username = form.getUsername();
        String password = form.getPassword();
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UserException("用户名不存在");
        } else if (!user.getPassword().equals(password)) {
            throw new UserException("密码错误");
        } else if (!user.isState()) {
            throw new UserException("用户未激活");
        }
        return user;
    }
}
