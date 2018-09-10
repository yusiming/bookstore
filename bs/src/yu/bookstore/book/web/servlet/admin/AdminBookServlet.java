package yu.bookstore.book.web.servlet.admin;

import cn.itcast.servlet.BaseServlet;
import yu.bookstore.book.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: yusiming
 * @Date: 2018/9/10 20:25
 * @Description: 后台图书管理模块
 */
@WebServlet(name = "AdminBookServlet", urlPatterns = {"/AdminBookServlet"})
public class AdminBookServlet extends BaseServlet {
    private BookService bookService = new BookService();

    /**
     * @Description: 查看所有图书
     * @auther: yusiming
     * @date: 20:30 2018/9/10
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.调用service方法，
         * 2.得到所有图书信息，封装到一个list中
         * 3.保存list到request域中，转发到list.jsp显示信息
         */
        request.setAttribute("request_bookList", bookService.findAll());
        return "f:/adminjsps/admin/book/list.jsp";
    }
}
