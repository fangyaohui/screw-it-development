package vo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @FileName BlogHeadlineVO
 * @Description
 * @Author yaoHui
 * @date 2024-08-04
 **/
@Data
public class BlogHeadlineVO {

    private String id;

    // 博客标题
    private String title;

    // 博客简要
    private String summary;

    // 博客作者
    private String author;

    // 博客显示图片地址
    private String image;

    // 博客浏览人数
    private Integer views;

    // 博客点赞人数
    private Integer likes;

    // 博客评论总数
    private Integer commentsCount;

}
