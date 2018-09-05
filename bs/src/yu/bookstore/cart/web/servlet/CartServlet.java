package yu.bookstore.cart.web.servlet;

import cn.itcast.servlet.BaseServlet;
import yu.bookstore.book.service.BookService;
import yu.bookstore.cart.domain.Cart;
import yu.bookstore.cart.domain.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: yusiming
 * @Date: 2018/9/5 20:55
 * @Description:
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/CartServlet"})
public class CartServlet extends BaseServlet {
    private BookService bookService = new BookService();

    /**
     * @Description: 添加购物条目
     * @auther: yusiming
     * @date: 20:58 2018/9/5
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.得到当前用户的购物车
         * 2.得到条目，包含图书的bid和 数量
         * 3.调用cart类的addItem方法添加条目
         * 3.添加条目
         */
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        CartItem cartItem = new CartItem();
        cartItem.setCount(Integer.parseInt(request.getParameter("count")));
        cartItem.setBook(bookService.load(request.getParameter("bid")));
        cart.addItem(cartItem);
        return "f:/jsps/cart/list.jsp";
    }

    /**
     * @Description: 请空购物车条目
     * @auther: yusiming
     * @date: 21:49 2018/9/5
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String clearItem(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.得到当前用户的购物车
         * 2.调用cart的clearItems方法，清空购物车
         */
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.clearItems();
        return "f:/jsps/cart/list.jsp";
    }

    /**
     * @Description: 删除指定的条目
     * @auther: yusiming
     * @date: 21:50 2018/9/5
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.得到当前用户的购物车
         * 2.得到bid参数
         * 3.调用cart的deleteItem方法
         */
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.deleteItem(request.getParameter("bid"));
        return "f:/jsps/cart/list.jsp";
    }
}

