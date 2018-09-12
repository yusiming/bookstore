package yu.bookstore.user.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: yusiming
 * @Date: 2018/9/12 23:52
 * @Description:
 */
@WebFilter(filterName = "AdminLoginFilter", servletNames = {"AdminAddBookServlet", "AdminBookServlet",
        "AdminCategoryServlet", "AdminOrderServlet",}, urlPatterns = {"/adminjsps/admin/*"})
public class AdminLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException,
            IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if (request.getSession().getAttribute("admin") != null) {
            chain.doFilter(req, resp);
        } else {
            request.setAttribute("msg", "您还未登陆");
            request.getRequestDispatcher("/adminjsps/msg.jsp").forward(request, response);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
