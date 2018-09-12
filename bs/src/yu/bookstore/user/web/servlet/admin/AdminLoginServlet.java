package yu.bookstore.user.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import yu.bookstore.user.domain.Admin;
import yu.bookstore.user.service.UserException;
import yu.bookstore.user.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: yusiming
 * @Date: 2018/9/12 23:32
 * @Description:
 */
@WebServlet(name = "AdminLoginServlet", urlPatterns = {"/AdminLoginServlet"})
public class AdminLoginServlet extends BaseServlet {
    private UserService userService = new UserService();

    /**
     * @Description: 管理员登陆 ，若登陆成功转发到
     * @auther: yusiming
     * @date: 23:33 2018/9/12
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        Admin admin = CommonUtils.toBean(request.getParameterMap(), Admin.class);
        System.out.println(admin);
        try {
            userService.login(admin);
            request.getSession().setAttribute("admin", admin);
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
            return "f:/adminjsps/msg.jsp";
        }
        return "f:/adminjsps/admin/index.jsp";
    }
}
