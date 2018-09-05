package yu.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: yusiming
 * @Date: 2018/9/5 20:29
 * @Description: 购物车类
 */
public class Cart {
    // 将条目存放在map中
    private Map<String, CartItem> map = new LinkedHashMap<>();

    /**
     * @Description: 获取所有条目的小计之和
     * @auther: yusiming
     * @date: 20:46 2018/9/5
     * @param: []
     * @return: double
     */
    public double getTotal() {
        BigDecimal totla = new BigDecimal(0 + "");
        for (CartItem cartItem : map.values()) {
            totla = totla.add(new BigDecimal(cartItem.getSubtotal() + ""));
        }
        return totla.doubleValue();
    }

    /**
     * @Description: 添加条目到购物车中
     * @auther: yusiming
     * @date: 20:37 2018/9/5
     * @param: [cartItem]
     * @return: void
     */
    public void addItem(CartItem cartItem) {
        /*
         * 1.判断条目是否已经存在
         * 2.若存在修改已经存在的条目的count数量，
         * 3.若不存在直接添加到map中
         */
        if (map.containsKey(cartItem.getBook().getBid())) {
            CartItem oldCartItem = map.get(cartItem.getBook().getBid());
            oldCartItem.setCount(cartItem.getCount() + oldCartItem.getCount());
            map.put(cartItem.getBook().getBid(), oldCartItem);
        } else {
            map.put(cartItem.getBook().getBid(), cartItem);
        }
    }

    /**
     * @Description: 清空购物车条目
     * @auther: yusiming
     * @date: 20:38 2018/9/5
     * @param: []
     * @return: void
     */
    public void clearItems() {
        map.clear();
    }

    /**
     * @Description: 根据bid删除指定条目
     * @auther: yusiming
     * @date: 20:39 2018/9/5
     * @param: [bid]
     * @return: void
     */
    public void deleteItem(String bid) {
        map.remove(bid);
    }

    /**
     * @Description: 获取所有条目
     * @auther: yusiming
     * @date: 20:39 2018/9/5
     * @param: []
     * @return: java.util.Collection<yu.bookstore.cart.domain.CartItem>
     */
    public Collection<CartItem> getCartItems() {
        return map.values();
    }
}
