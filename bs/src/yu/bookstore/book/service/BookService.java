package yu.bookstore.book.service;

import yu.bookstore.book.dao.BookDao;
import yu.bookstore.book.domain.Book;

import java.util.List;

/**
 * @Auther: yusiming
 * @Date: 2018/9/4 23:44
 * @Description: 图书模块功能的实现
 */
public class BookService {
    private BookDao bookDao = new BookDao();

    /**
     * @Description: 得到所有图书信息
     * @auther: yusiming
     * @date: 13:07 2018/9/5
     * @param: []
     * @return: java.util.List<yu.bookstore.book.domain.Book>
     */
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    /**
     * @Description: 根据cid 图书分类得到图书信息
     * @auther: yusiming
     * @date: 13:07 2018/9/5
     * @param: [cid]
     * @return: java.util.List<yu.bookstore.book.domain.Book>
     */
    public List<Book> findByCategory(String cid) {
        return bookDao.findByCategory(cid);
    }

    /**
     * @Description: 得到单个图书的详细信息
     * @auther: yusiming
     * @date: 13:08 2018/9/5
     * @param: [bid]
     * @return: yu.bookstore.book.domain.Book
     */
    public Book load(String bid) {
        return bookDao.load(bid);
    }

    /**
     * @Description: 添加图书
     * @auther: yusiming
     * @date: 23:08 2018/9/10
     * @param: []
     * @return: void
     * @param book
     */
    public void addBook(Book book) {
        bookDao.addBook(book);
    }
}
