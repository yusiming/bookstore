package yu.bookstore.order.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import yu.bookstore.cart.domain.Cart;
import yu.bookstore.cart.domain.CartItem;
import yu.bookstore.order.domain.Order;
import yu.bookstore.order.domain.OrderItem;
import yu.bookstore.order.service.OrderException;
import yu.bookstore.order.service.OrderService;
import yu.bookstore.user.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Auther: yusiming
 * @Date: 2018/9/5 22:50
 * @Description:
 */
@WebServlet(name = "OrderServlet", urlPatterns = {"/OrderServlet"})
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();

    /**
     * @Description: 添加订单
     * @auther: yusiming
     * @date: 22:15 2018/9/6
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        HttpSession session = request.getSession();
        // 得到购物车和用户
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        // 创建Order对象
        Order order = new Order();
        // 设置order的各个属性的值
        order.setOid(CommonUtils.uuid());
        order.setOrdertime(new Date());
        order.setTotal(cart.getTotal());
        order.setState(1);
        order.setOwner(user);
        // 创建订单条目List
        List<OrderItem> orderItemList = new ArrayList<>();
        // 得到购物车条目集合，遍历集合，为每一个orderItem赋值
        Collection<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems) {
            // 创建一个OrderItem对象,
            OrderItem orderItem = new OrderItem();
            // 设置其各个属性的值
            orderItem.setIid(CommonUtils.uuid());
            orderItem.setCount(cartItem.getCount());
            orderItem.setBook(cartItem.getBook());
            orderItem.setOrder(order);
            orderItem.setSubtotal(cartItem.getSubtotal());
            // 将orderItem对象添加到List中
            orderItemList.add(orderItem);
        }
        // 将orderItemList添加到order对象中
        order.setOrderItemList(orderItemList);
        // 清空购物车，
        cart.clearItems();
        // 添加订单记录到数据库中
        orderService.add(order);
        // 保存订单信息到request域中，
        request.setAttribute("order", order);
        // 转发到order/desc.jsp页面显示订单信息
        return "f:/jsps/order/desc.jsp";
    }

    /**
     * @Description: 完成我的订单功能
     * @auther: yusiming
     * @date: 21:30 2018/9/7
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String myOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.从session中得到user对象，
         * 2.根据uid调用service的myOrders方法，得到所有订单
         * 3.保存所有订单到request域中
         * 4.转发到order/list,jsp显示所有订单
         */
        User user = (User) request.getSession().getAttribute("user");
        List<Order> orderList = orderService.myOrders(user.getUid());
        request.setAttribute("orderList", orderList);
        return "f:/jsps/order/list.jsp";
    }

    /**
     * @Description: 点击 付款 加载单个订单
     * @auther: yusiming
     * @date: 22:56 2018/9/7
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String loadOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.得到参数oid
         * 2.根据oid加载Order
         * 3.把保存Order到request中
         * 4.转发到desc.jsp页面显示信息
         */
        Order order = orderService.loadOrder(request.getParameter("oid"));
        request.setAttribute("order", order);
        return "f:/jsps/order/desc.jsp";
    }

    /**
     * @Description: 点击确认收货，完成收货
     * @auther: yusiming
     * @date: 23:21 2018/9/7
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String confirmReceipt(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 1.得到订单oid
         * 2.校验订单的状态是否为3
         * 3.保存信息到request
         * 4.转发到msg.jsp页面显示信息
         */
        String oid = request.getParameter("oid");
        try {
            orderService.confirmReceipt(oid);
            request.setAttribute("msg", "收货成功");
        } catch (OrderException e) {
            request.setAttribute("msg", e.getMessage());
        }
        return "f:jsps/order/msg.jsp";
    }
}
