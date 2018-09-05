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
}
