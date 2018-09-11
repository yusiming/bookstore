package yu.bookstore.book.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import yu.bookstore.book.domain.Book;
import yu.bookstore.book.service.BookService;
import yu.bookstore.category.domain.Category;
import yu.bookstore.category.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: yusiming
 * @Date: 2018/9/10 20:25
 * @Description: 后台管理上传图书
 */
@WebServlet(name = "AdminAddBookServlet", urlPatterns = {"/AdminAddBookServlet"})
public class AdminAddBookServlet extends HttpServlet {
    private CategoryService categoryService = new CategoryService();
    private BookService bookService = new BookService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 得到工厂, 设置缓存大小为15kb，临时目录为D:/temp
        DiskFileItemFactory factory = new DiskFileItemFactory(30 * 1024, new File("D:/temp"));
        // 得到解析器
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        // 设置单个文件大小最大为30 kb
        fileUpload.setSizeMax(30 * 1024);
        // 得到FileItemList
        try {
            // 若文件大小超出30kb， fileUpload.parseRequest(request)会抛出此异常FileSizeLimitExceededException
            List<FileItem> list = fileUpload.parseRequest(request);
            Map<String, String> map = new HashMap<>();
            for (FileItem fileItem : list) {
                if (fileItem.isFormField()) {
                    map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
                }
            }
            // 得到book和category
            Book book = CommonUtils.toBean(map, Book.class);
            Category category = CommonUtils.toBean(map, Category.class);
            // 补全bid和category
            book.setBid(CommonUtils.uuid());
            book.setCategory(category);
            // 得到文件真实保存目录
            String realPath = this.getServletContext().getRealPath("/book_img");
            // 得到文件名称,添加前缀，避免文件名重复
            String filename = CommonUtils.uuid() + "_" + list.get(1).getName();
            // 校验文件拓展名
            if (!filename.toLowerCase().contains("jpg")) {
                request.setAttribute("msg", "您上传的图片大小超出了30kb");
                request.setAttribute("request_categoryList",categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
                return;
            }
            // 创建文件对应的File对象
            File destFile = new File(realPath, filename);
            list.get(1).write(destFile);

            // 设置book对象的image属性
            book.setImage(filename);
            // 使用bookService添加book到数据库中
            bookService.addBook(book);

            // 校验图片尺寸
            Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
            if (image.getWidth(null) > 200 || image.getHeight(null) > 200) {
                // 删除文件
                destFile.delete();
                request.setAttribute("msg", "您上传的图片尺寸超出了200 * 200");
                request.setAttribute("request_categoryList",categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
                return;
            }
            request.getRequestDispatcher("/AdminBookServlet?method=findAll").forward(request, response);
        } catch (Exception e) {
            // 若文件大小超过30kb 会抛出此异常
            if (e instanceof FileUploadBase.SizeLimitExceededException) {
                request.setAttribute("msg", "您上传的图片大小超出了30kb");
                request.setAttribute("request_categoryList",categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
            } else {
                throw new RuntimeException(e);
            }
        }
    }
}
