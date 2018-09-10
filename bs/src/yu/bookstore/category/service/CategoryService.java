package yu.bookstore.category.service;

import yu.bookstore.book.dao.BookDao;
import yu.bookstore.book.domain.Book;
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
    private BookDao bookDao = new BookDao();

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

    /**
     * @Description: 删除图书分类，若该分类下存在图书则不能删除
     * @auther: yusiming
     * @date: 21:54 2018/9/9
     * @param: [cname]
     * @return: void
     */
    public void deleteCategory(String cid) throws CategoryException {
        List<Book> bookList = bookDao.findByCategory(cid);
        if (bookList.size() != 0) {
            throw new CategoryException("该分类存在图书，不能删除");
        } else {
            categoryDao.deleteCategory(cid);
        }
    }

    /**
     * @Description: 根据cid得到Category对象
     * @auther: yusiming
     * @date: 19:56 2018/9/10
     * @param: [cid]
     * @return: yu.bookstore.category.domain.Category
     */
    public Category loadCategory(String cid) {
        return categoryDao.loadCategory(cid);
    }

    /**
     * @Description: 修改分类名称
     * @auther: yusiming
     * @date: 20:12 2018/9/10
     * @param: [category]
     * @return: void
     */
    public void edit(Category category) throws CategoryException {
        if (categoryDao.findCategoryByCname(category.getCname()) == null) {
            categoryDao.editCategory(category.getCid(), category.getCname());
        } else {
            throw new CategoryException("该分类名称已经存在");
        }
    }
}
