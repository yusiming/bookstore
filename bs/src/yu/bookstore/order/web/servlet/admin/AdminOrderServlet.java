package yu.bookstore.order.web.servlet.admin;

import cn.itcast.servlet.BaseServlet;
import yu.bookstore.order.domain.Order;
import yu.bookstore.order.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: yusiming
 * @Date: 2018/9/12 22:18
 * @Description:
 */
@WebServlet(name = "AdminOrderServlet", urlPatterns = {"/AdminOrderServlet"})
public class AdminOrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();

    /**
     * @Description: 查询所有订单
     * @auther: yusiming
     * @date: 22:19 2018/9/12
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.调用service方法查询所有订单
         * 2.保存信息到request中
         * 3.转发到list.jsp显示信息
         */
        List<Order> orderList = orderService.findAll();
        request.setAttribute("request_orderList", orderList);
        return "f:/adminjsps/admin/order/list.jsp";
    }
}
