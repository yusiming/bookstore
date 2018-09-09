package yu.bookstore.category.service;

import yu.bookstore.category.dao.CategoryDao;
import yu.bookstore.category.domain.Category;

import java.util.List;

/**
 * @Auther: yusiming
 * @Date: 2018/9/4 23:05
 * @Description: 业务层
 */
public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();

    /**
     * @Description: 查询所有图书分类
     * @auther: yusiming
     * @date: 13:00 2018/9/5
     * @param: []
     * @return: java.util.List<yu.bookstore.category.domain.Category>
     */
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    /**
     * @Description: 添加图书分类
     * @auther: yusiming
     * @date: 21:29 2018/9/9
     * @param: [category]
     * @return: void
     */
    public void addCategory(Category category) {
        /*
         * 1.使用cname查询是否已经有这个分类，
         * 2.若已经有这个分类，什么都不做
         * 3.若没有过这个分类，添加分类
         */
        if (categoryDao.findCategoryByCname(category.getCname()) == null) {
            categoryDao.add(category);
        }
    }
}
