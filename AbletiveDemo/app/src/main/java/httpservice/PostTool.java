package httpservice;

import android.graphics.Bitmap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data.PostPO;
import data.PostTitleVO;
import data.SearchPO;

/**
 * 文章工具
 * Created by Alan on 2016/3/7.
 */
public class PostTool {
    private final static String TAG = "Abletive";

    public static ArrayList<PostTitleVO> getPostTitle(PostPO postPO) throws IOException {
        return getPostTitle(postPO.toSearchPO());
    }

    public static ArrayList<PostTitleVO> getPostTitle(SearchPO searchPO) throws IOException {
        int numInPage = searchPO.getCount();
        ArrayList<PostTitleVO> postTitleList = new ArrayList<PostTitleVO>();
        ArrayList<HashMap<String, Object>> posts = searchPO.getPosts();

        for (int i = 0; i < numInPage; i++) {
            //获得文章对象
            HashMap<String, Object> onePost = posts.get(i);

            //文章标题
            String title = (String) onePost.get("title");

            //发布日期
            String date = (String) onePost.get("date");

            //获得作者对象
            Map<String, String> author = (Map<String, String>) onePost.get("author");

            //作者名
            String authorName = author.get("nickname");

            //文章缩略图
            Bitmap thumb = null;
            //TODO 应该有类的对应，迭代二建立
            Map<String, Object> tempThumb = (Map<String, Object>) onePost.get("thumbnail_images");
            Map<String, String> mediumThumb = null;
            String thumbUrl = "";
            if (tempThumb != null) {
                mediumThumb = (Map<String, String>) tempThumb.get("medium");
                thumbUrl = mediumThumb.get("url");
                thumb = new HttpImpl().getThumbnail(thumbUrl);
            }

            ArrayList<Map<String, String>> categoriesList = (ArrayList<Map<String, String>>) onePost.get("categories");
            String firstCategory = categoriesList.get(0).get("title");

            //评论数
            double commentsDouble = (double) onePost.get("comment_count");
            int comments = (int) commentsDouble;

            //浏览数
            Map<String, Object> customFields = (Map<String, Object>) onePost.get("custom_fields");
            ArrayList<Object> viewsDouble = (ArrayList<Object>) customFields.get("views");
            String views = (String) viewsDouble.get(0);

            //url
            String url = (String) onePost.get("url");

            PostTitleVO postTitle = new PostTitleVO(title, authorName, thumb, firstCategory, date, views, comments, url);
            postTitleList.add(postTitle);
        }
        return postTitleList;
    }

}
