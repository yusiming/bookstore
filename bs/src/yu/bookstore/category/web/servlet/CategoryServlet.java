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

    /**
     * @Description: 查询所有图书分类
     * @auther: yusiming
     * @date: 13:01 2018/9/5
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.调用Service的findAll方法
         * 2.保存信息到request域中
         * 3.转发到left.jsp
         * 4.left.jsp循环显示
         */
        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/jsps/left.jsp";
    }
}
