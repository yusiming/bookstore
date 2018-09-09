package yu.bookstore.category.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
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
}
