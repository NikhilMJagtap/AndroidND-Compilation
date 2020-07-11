package db;

import model.Article;
import model.Source;

public class Converter {

    public static ArticleDB articleToArticleDB(Article article){
        return new ArticleDB(
                article.getSource().getName(),
                article.getAuthor(),
                article.getTitle(),
                article.getDescription(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getPublishedAt(),
                article.getContent()
        );
    }

    public static Article articleDBToArticle(ArticleDB articleDB){
        return new Article(
                new Source("", articleDB.getSource()),
                articleDB.getAuthor(),
                articleDB.getTitle(),
                articleDB.getDescription(),
                articleDB.getUrl(),
                articleDB.getUrlToImage(),
                articleDB.getPublishedAt(),
                articleDB.getContent()
        );
    }
}
