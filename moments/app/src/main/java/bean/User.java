package bean;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by lanyu on 2018/12/13.
 */
public class User implements Serializable{
    private int img_head;
    private String username;
    private String content;
    private int img1_comment;

    public User( String username) {

        this.username = username;
        this.content = content;
    }
    public User( int img,String username, String content,int img1) {
        this.img_head = img;
        this.username = username;
        this.content = content;
        this.img1_comment = img1;
    }

    public int getImg() {
        return img_head;
    }

    public void setImg(int img,int img1) {
        this.img_head = img; this.img1_comment =img1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
