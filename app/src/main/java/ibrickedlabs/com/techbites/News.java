package ibrickedlabs.com.techbites;

import java.io.Serializable;

class News implements Serializable {
    private String srcName;
    private String authorName;
    private String title;
    private String url;
    private String imageUrl;
    private String content;
    private String publishedAt;

    public News(String srcName, String authorName, String title, String url, String imageUrl, String content, String publishedAt) {
        this.srcName = srcName;
        this.authorName = authorName;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.content = content;
        this.publishedAt = publishedAt;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
