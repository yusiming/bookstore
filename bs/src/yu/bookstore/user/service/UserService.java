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
}
