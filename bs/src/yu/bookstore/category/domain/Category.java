package yu.bookstore.category.domain;

/**
 * @Auther: yusiming
 * @Date: 2018/9/4 23:05
 * @Description: bean对象
 */
public class Category {
    private String cid;
    private String cname;

    @Override
    public String toString() {
        return "Category{" +
                "cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
