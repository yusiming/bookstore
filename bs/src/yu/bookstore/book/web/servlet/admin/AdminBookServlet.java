package yu.bookstore.book.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import yu.bookstore.book.domain.Book;
import yu.bookstore.book.service.BookService;
import yu.bookstore.category.domain.Category;
import yu.bookstore.category.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: yusiming
 * @Date: 2018/9/10 20:25
 * @Description: 后台图书管理模块
 */
@WebServlet(name = "AdminBookServlet", urlPatterns = {"/AdminBookServlet"})
public class AdminBookServlet extends BaseServlet {
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();

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

    /**
     * @Description: 加载图书详细信息
     * @auther: yusiming
     * @date: 20:45 2018/9/10
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String loadBook(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.得到bid
         * 2.调用service方法，得到Book对象
         * 3.得到所有图书分类
         * 3.转发到desc.jsp,显示详细信息
         */
        Book book = bookService.load(request.getParameter("bid"));
        List<Category> categoryList = categoryService.findAll();
        System.out.println(categoryList);
        request.setAttribute("request_book", book);
        request.setAttribute("request_categoryList", categoryList);
        return "f:/adminjsps/admin/book/desc.jsp";
    }

    /**
     * @Description: 添加图书之前，将图书分类信息，转发到add.jsp页面显示
     * @auther: yusiming
     * @date: 21:24 2018/9/10
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String beforeAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 调用service方法，
         */
        request.setAttribute("request_categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/book/add.jsp";
    }

    /**
     * @Description:
     * @auther: yusiming
     * @date: 21:44 2018/9/11
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String deleteBook(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String bid = request.getParameter("bid");
        bookService.delete(bid);
        return findAll(request, response);
    }

    /**
     * @Description: 修改图书
     * @auther: yusiming
     * @date: 22:11 2018/9/11
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        book.setCategory(category);
        bookService.edit(book);
        return findAll(request, response);
    }
}
