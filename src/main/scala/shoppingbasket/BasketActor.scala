package shoppingbasket

import spray.http.StatusCodes
import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorRef

/**
 * @author littleworld
 */

object BasketActor {
  case class AddArticle(a: Article)
  case class DeleteArticle(id: Int)
  case object ListArticles
  
  case object ArticleAdded
  case object ArticleDeleted
  case class ArticleList(list: List[Article])
}

class BasketActor extends Actor with Basket {
  
  import BasketActor._
  
  def receive = {
    case AddArticle(article: Article) => add(article); sender ! ArticleAdded
    case DeleteArticle(id: Int) => delete(id); sender ! ArticleDeleted
    case ListArticles => sender ! ArticleList(list)
    case _ =>
  }
}