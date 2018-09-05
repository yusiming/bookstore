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
 * @Description: 图书模块的web层
 */
@WebServlet(name = "BookServlet", urlPatterns = {"/BookServlet"})
public class BookServlet extends BaseServlet {
    private BookService bookService = new BookService();

    /**
     * @Description: 查询所有图书
     * @auther: yusiming
     * @date: 13:09 2018/9/5
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.调用Service的findAll方法得到所有图书信息
         * 2.保存信息到request域中
         * 3.转发到list.jsp
         * 4.list.jsp循环显示图书信息
         */
        request.setAttribute("bookList", bookService.findAll());
        return "f:/jsps/book/list.jsp";
    }

    /**
     * @Description: 根据分类得到图书信息
     * @auther: yusiming
     * @date: 13:11 2018/9/5
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String findByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.得到图书分类参数cid，
         * 2.调用Service的findByCategory方法，传入参数cid
         * 3.保存信息到request域中，
         * 4.转发到list.jsp
         * 5.list.jsp循环显示图书
         */
        request.setAttribute("bookList", bookService.findByCategory(request.getParameter("cid")));
        return "f:/jsps/book/list.jsp";
    }

    /**
     * @Description: 加载单本图书的详细信息
     * @auther: yusiming
     * @date: 13:15 2018/9/5
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.得到bid参数，
         * 2.bookService的load方法，传入参数bid
         * 3.保存信息到request域中
         * 4.转发到desc.jsp页面实现详细图书信息
         */
        request.setAttribute("book", bookService.load(request.getParameter("bid")));
        return "f:/jsps/book/desc.jsp";
    }
}
