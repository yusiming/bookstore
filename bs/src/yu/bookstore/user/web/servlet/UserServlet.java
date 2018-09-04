package yu.bookstore.user.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
import yu.bookstore.user.domain.User;
import yu.bookstore.user.service.UserException;
import yu.bookstore.user.service.UserService;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Auther: yusiming
 * @Date: 2018/9/3 23:10
 * @Description: web层
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends BaseServlet {
    private UserService userService = new UserService();

    /**
     * @Description: 注册功能
     * @auther: yusiming
     * @date: 23:11 2018/9/3
     * @param: [request, response]
     * @return: void
     */
    public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 获取请求参数，封装到User对象中
        User form = CommonUtils.toBean(request.getParameterMap(), User.class);
        // 添加uid和验证码code，code用来激活用户
        form.setUid(CommonUtils.uuid());
        form.setCode(CommonUtils.uuid() + CommonUtils.uuid());
        // 创建一个map用来保存错误信息
        Map<String, String> errors = new HashMap<>();
        // 校验用户名，用户名不能为空，长度在3-10之间
        String username = form.getUsername();
        if (username == null || username.trim().isEmpty()) {
            errors.put("username", "用户名不能为空");
        } else if (username.length() < 3 || username.length() > 10) {
            errors.put("username", "用户名长度必须在3-10之间");
        }
        // 校验密码
        String password = form.getPassword();
        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "密码不能为空");
        } else if (password.length() < 3 || password.length() > 10) {
            errors.put("password", "密码长度必须在3-10之间");
        }
        // 校验email
        String email = form.getEmail();
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "email不能为空");
        } else if (!email.matches("\\w+@\\w+\\.\\w+")) {
            errors.put("email", "email格式不正确");
        }
        if (errors.size() > 0) {
            // 保存错误信息
            request.setAttribute("errors", errors);
            // 为了回显数据
            request.setAttribute("form", form);
            return "f:/jsps/user/regist.jsp";
        }

        // 若校验成功，执行userService的regist方法
        try {
            userService.regist(form);
        } catch (UserException e) {
            // 若捕捉到异常，证明注册失败，保存错误信息和form到request中，
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form);
            return "f:/jsps/user/regist.jsp";
        }
        /*
         * 执行到这里证明注册成功
         * 发送邮件
         * 1.加载配置文件
         * 2.获取配置文件
         * 3.发送邮件到用户邮箱
         */
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
        // 获取服务器主机
        String host = properties.getProperty("host");
        // 获取用户名
        String uname = properties.getProperty("uname");
        // 获取密码
        String pwd = properties.getProperty("pwd");
        // 获取发件人
        String from = properties.getProperty("from");
        // 获取主题
        String subject = properties.getProperty("subject");
        // 获取内容
        String content = properties.getProperty("content");
        // 替换占位符
        content = MessageFormat.format(content, form.getCode());
        // 发邮件
        Session session = MailUtils.createSession(host, uname, pwd);
        Mail mail = new Mail(from, email, subject, content);
        try {
            MailUtils.send(session, mail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("msg", "注册成功，请到邮箱激活账户");
        return "f:/jsps/msg.jsp";
    }

    /**
     * @Description: 用户激活
     * @auther: yusiming
     * @date: 19:52 2018/9/4
     * @param: [request, response]
     * @return: java.lang.String
     */
    public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        /*
         * 步骤：
         * 1.获取参数code
         * 2.调用UserService的激活方法
         * 3.若出错保存异常信息到request中，转发到msg.jsp
         * 4.若激活成功，保存激活信息到request中，转发到msg.jsp
         */
        String code = request.getParameter("code");
        try {
            userService.avtive(code);
            request.setAttribute("msg","您成功激活了您的账户");
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
        }
        return "f:/jsps/msg.jsp";
    }
}
