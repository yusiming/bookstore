package yu.bookstore.category.web.servlet;

import cn.itcast.servlet.BaseServlet;
import yu.bookstore.category.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: yusiming
 * @Date: 2018/9/4 23:06
 * @Description:
 */
@WebServlet(name = "CategoryServlet", urlPatterns = {"/CategoryServlet"})
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setAttribute("categoryList",categoryService.findAll());
        return "f:/jsps/left.jsp";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

    }
}
