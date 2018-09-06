package yu.bookstore.order.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import yu.bookstore.cart.domain.Cart;
import yu.bookstore.cart.domain.CartItem;
import yu.bookstore.order.domain.Order;
import yu.bookstore.order.domain.OrderItem;
import yu.bookstore.order.service.OrderService;
import yu.bookstore.user.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

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
}
