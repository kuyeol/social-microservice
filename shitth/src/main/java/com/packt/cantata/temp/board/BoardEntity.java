package com.packt.cantata.temp.board;


import com.packt.cantata.temp.common.EntityBase;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;


@Entity
public abstract  class BoardEntity  extends EntityBase {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_seq")
    @Column(name = "ARTICLE_ID")
    private Long  articleId;

    @Column(name = "ARTICLE_ID")
    private String title;

    @Column(name = "ARTICLE_ID")
    private String author;

    @Column(name = "ARTICLE_ID")
    private String content;

    @Column(name = "CREATED_TIMESTAMP")
    protected Long createdTimestamp;

    //
    //@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = false, mappedBy="user")
    //@Fetch(FetchMode.SELECT)
    //@BatchSize(size = 20)
    //protected Collection<User> attributes = new LinkedList<>();



    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof BoardEntity)) return false;

        BoardEntity that = (BoardEntity) o;

        if (!id.equals(that.getId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
