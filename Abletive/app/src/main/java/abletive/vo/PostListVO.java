package abletive.vo;

/**
 * 文章列表元素数据
 *
 * @author Alan
 * @version 2.1 2016/4/3
 */
public class PostListVO {
    String title;
    String description;
    String author;
    String thumbUrl;
    String category;
    String time;
    String views;
    String comments;
    String url;

    public PostListVO(String title, String description, String author, String thumbUrl, String category, String time, String views, String comments, String url) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.thumbUrl = thumbUrl;
        this.category = category;
        this.time = time;
        this.views = views;
        this.comments = comments;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
