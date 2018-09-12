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

    /**
     * @Description: 查询所有未付款订单
     * @auther: yusiming
     * @date: 22:56 2018/9/12
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String findUnpaidOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        List<Order> orderList = orderService.findUnpaidOrders(1);
        request.setAttribute("request_orderList", orderList);
        return "f:/adminjsps/admin/order/list.jsp";
    }

    /**
     * @Description: 查询所有已付款订单
     * @auther: yusiming
     * @date: 23:02 2018/9/12
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String findPaidOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        List<Order> orderList = orderService.findUnpaidOrders(2);
        request.setAttribute("request_orderList", orderList);
        return "f:/adminjsps/admin/order/list.jsp";
    }

    /**
     * @Description: 查询未发货订单
     * @auther: yusiming
     * @date: 23:05 2018/9/12
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String findUnReceivedOrders(HttpServletRequest request, HttpServletResponse response) throws
            ServletException,
            IOException {
        List<Order> orderList = orderService.findUnpaidOrders(3);
        request.setAttribute("request_orderList", orderList);
        return "f:/adminjsps/admin/order/list.jsp";
    }

    /**
     * @Description: 查询已收货订单
     * @auther: yusiming
     * @date: 23:06 2018/9/12
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String findReceivedOrders(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        List<Order> orderList = orderService.findUnpaidOrders(4);
        request.setAttribute("request_orderList", orderList);
        return "f:/adminjsps/admin/order/list.jsp";
    }

    /**
     * @Description: 发货
     * @auther: yusiming
     * @date: 23:12 2018/9/12
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String Shipping(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        /*
         * 1.得到oid参数，调用service方法
         * 2.保存成功信息到request中
         * 3.转发到msg.jsp页面显示信息
         */
        orderService.Shipping(request.getParameter("oid"));
        request.setAttribute("msg","发货成功");
        return "f:/adminjsps/admin/msg.jsp";
    }
}
