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
    public void avtive(String code) throws UserException {
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
}
