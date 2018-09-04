package yu.bookstore.book.web.servlet;

import cn.itcast.servlet.BaseServlet;
import yu.bookstore.book.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: yusiming
 * @Date: 2018/9/4 23:44
 * @Description:
 */
@WebServlet(name = "BookServlet",urlPatterns = {"/BookServlet"})
public class BookServlet extends BaseServlet {
    private BookService bookService = new BookService();

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setAttribute("bookList",bookService.findAll());
        return "f:/jsps/book/list.jsp";
    }

    public String findByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setAttribute("bookList",bookService.findByCategory(request.getParameter("cid")));
        return "f:/jsps/book/list.jsp";
    }
}
