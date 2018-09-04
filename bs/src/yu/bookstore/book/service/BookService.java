package yu.bookstore.book.service;

import yu.bookstore.book.dao.BookDao;
import yu.bookstore.book.domain.Book;

import java.util.List;

/**
 * @Auther: yusiming
 * @Date: 2018/9/4 23:44
 * @Description:
 */
public class BookService {
    private BookDao bookDao = new BookDao();
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    public List<Book> findByCategory(String cid) {
        return bookDao.findByCategory(cid);
    }
}
