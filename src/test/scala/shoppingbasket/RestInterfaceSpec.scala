package shoppingbasket

import scala.concurrent.duration.DurationInt

import org.scalatest.FlatSpec

import akka.actor.Actor
import akka.actor.ActorDSL.actor
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.testkit.TestActorRef
import akka.util.Timeout
import shoppingbasket.BasketActor.ListArticles
import spray.http._
import spray.http.ContentType
import spray.http.HttpCharsets._
import spray.http.HttpEntity
import spray.http.MediaTypes._
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport._
import spray.httpx.marshalling._
import spray.httpx.unmarshalling._
import spray.testkit.ScalatestRouteTest

class BasketActorMock extends Actor with Basket {
  // a mock
  var articles = Article(1, "scala in action", "a scala book", 2500, 1) :: Article(2, "scala programming", "a scala book", 3500, 1) :: Nil
  import BasketActor._

  def receive = {
    case AddArticle(article: Article) => sender ! ArticleAdded
    case DeleteArticle(id: Int) => sender ! ArticleDeleted
    case ListArticles => sender ! ArticleList(articles)
    case _ =>
  }
}

class RestInterfaceSpec extends FlatSpec with ScalatestRouteTest {
  implicit def timeout = Timeout(3.second)
  val basketActorMock = TestActorRef(Props[BasketActorMock])

  def route = new RestApi {
    def actorRefFactory = system
  }.routes(basketActorMock)

  "The RestApi" should "return a list of 2 items " in {
    Get("/basket") ~> route ~> check {
      assert(status === StatusCodes.OK)
      val res = responseAs[List[Article]]
      assert(res.size === 2)
    }
  }
  it should "return No Content when Delete" in {
    Delete("/basket/1") ~> route ~> check {
      assert(status === StatusCodes.NoContent)
    }
  }
  it should "return Created when Add" in {
    Post("/basket", HttpEntity(
      contentType = ContentType(`application/json`, `UTF-8`),
      string = """{"name": "scala in action", "description": "a scala book", "prize": 2500, "amount": 1, "id": 1 }""")) ~> route ~> check {
      assert(status === StatusCodes.Created)
    }
  }
}