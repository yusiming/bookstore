package yu.bookstore.user.web.filter;

import yu.bookstore.user.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: yusiming
 * @Date: 2018/9/11 22:31
 * @Description:
 */
@WebFilter(filterName = "LoginFilter",
        servletNames = {"CartServlet", "OrderServlet"},
        urlPatterns = {"/jsps/cart/*", "/jsps/order/*"})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException,
            IOException {
        /*
         * 1.从session中获取用户信息
         * 2.若存在用户信息，放行
         * 3.若不存在用户信息，转发到主页
         */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            chain.doFilter(req, resp);
        } else {
            request.setAttribute("msg", "您还没有登陆");
            request.getRequestDispatcher("/jsps/user/login.jsp").forward(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
