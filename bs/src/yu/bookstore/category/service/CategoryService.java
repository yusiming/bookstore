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

    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
