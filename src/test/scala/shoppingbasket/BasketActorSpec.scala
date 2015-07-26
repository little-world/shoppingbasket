package shoppingbasket

/**
 * @author littleworld
 */

import org.scalatest.{ FlatSpecLike, BeforeAndAfterAll }
import akka.testkit.{ TestActorRef, ImplicitSender, TestKit }
import akka.actor.{ Props, ActorSystem }

class BasketActorSpec extends TestKit(ActorSystem()) with FlatSpecLike with ImplicitSender with BeforeAndAfterAll {

  import BasketActor._

  val basketActor = TestActorRef(Props[BasketActor])

  "A BasketActor" should "return a list of 2 Articles" in {
    basketActor ! ListArticles
    val lst = expectMsgType[ArticleList]
    assert(lst.list.size === 2)
  }
  
  it should "return a message ArticleDeleted when send Delete" in {
    basketActor ! DeleteArticle(1)
    expectMsg(ArticleDeleted)
  }

   it should "return a message ArticleAdded when send Add" in {
    basketActor ! AddArticle(Article(3, "scala in action", "a scala book", 2500, 1))
    expectMsg(ArticleAdded)
  }
}