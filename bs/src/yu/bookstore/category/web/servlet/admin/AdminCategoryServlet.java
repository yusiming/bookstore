package yu.bookstore.category.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import yu.bookstore.category.domain.Category;
import yu.bookstore.category.service.CategoryException;
import yu.bookstore.category.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: yusiming
 * @Date: 2018/9/9 21:03
 * @Description: 后台管理
 */
@WebServlet(name = "AdminCategoryServlet", urlPatterns = {"/AdminCategoryServlet"})
public class AdminCategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    /**
     * @Description: 查看所有图书分类
     * @auther: yusiming
     * @date: 21:04 2018/9/9
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        List<Category> categoryList = categoryService.findAll();
        request.setAttribute("request_categoryList", categoryList);
        return "f:/adminjsps/admin/category/list.jsp";
    }

    /**
     * @Description: 添加分类
     * @auther: yusiming
     * @date: 21:15 2018/9/9
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.得到参数，封装为Category对象
         * 2.补全cid
         * 3.调用service的addCategory方法
         * 4。执行findAll方法，显示添加结果
         */
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        category.setCid(CommonUtils.uuid());
        categoryService.addCategory(category);
        return findAll(request, response);
    }

    /**
     * @Description: 删除图书分类
     * @auther: yusiming
     * @date: 21:49 2018/9/9
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.得到需要删除的cid
         * 2.调用service的deleteCategory方法
         * 3.调用findAll
         */
        try {
            categoryService.deleteCategory(request.getParameter("cid"));
        } catch (CategoryException e) {
            request.setAttribute("msg", e.getMessage());
            return "f:/adminjsps/msg.jsp";
        }
        return findAll(request, response);
    }

    /**
     * @Description: 改修之前得到分类的信息，转发到mod.jsp显示分类 信息
     * @auther: yusiming
     * @date: 19:53 2018/9/10
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String beforeEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.得到cid
         * 2.得到Category对象
         * 3.转发到mod.jsp页面实现信息
         */
        request.setAttribute("request_category", categoryService.loadCategory(request.getParameter("cid")));
        return "f:/adminjsps/admin/category/mod.jsp";
    }

    /**
     * @Description: 修改分类信息，转发到list.jsp页面显示结果
     * @auther: yusiming
     * @date: 20:10 2018/9/10
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        try {
            categoryService.edit(category);
        } catch (CategoryException e) {
            request.setAttribute("msg", e.getMessage());
            return "f:/adminjsps/msg.jsp";
        }
        return findAll(request, response);
    }
}
