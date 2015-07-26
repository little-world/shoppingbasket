package shoppingbasket

/**
 * @author littleworld
 */

import spray.json.DefaultJsonProtocol

case class Article(id: Int, name: String, description: String, prize: Int, amount: Int)

object Article extends DefaultJsonProtocol {
  implicit val format = jsonFormat5(Article.apply)
}

trait Basket {

  private var articles = Article(1, "scala in action", "a scala book", 2500, 1) :: Article(2, "scala programming", "a scala book", 3500, 1) ::  Nil

  def add(article: Article) {
    articles = article :: articles
  }

  def delete(id: Int) {
    articles = articles.filterNot((article) => article.id == id)
  }

  def list = articles
}