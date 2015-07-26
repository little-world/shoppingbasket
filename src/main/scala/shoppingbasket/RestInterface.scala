package shoppingbasket

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import spray.routing._

class RestInterface(basket: ActorRef) extends HttpServiceActor with RestApi {

  def receive = runRoute(routes(basket))
}

trait RestApi extends HttpService {
  import BasketActor._
  implicit val timeout = Timeout(10 seconds)

  def routes(basket: ActorRef): Route = {

    path("basket") {
      get {
        //curl  http://localhost:8091/basket
        
        onSuccess(basket ? ListArticles) {
          case ArticleList(list)=> complete(OK, list)
          case _ => complete(BadRequest)
        }

      } ~
        //curl -H "Content-Type: application/json"  -X POST  -d '{"name": "scala in action", "description": "a scala book", "prize": 2500, "amount": 1, "id": 1 }' http://localhost:8091/add
        post {
          entity(as[Article]) { article =>
            onSuccess(basket ? AddArticle(article)) {
              case ArticleAdded => complete(Created)  
              case _ => complete(BadRequest)
            }
          }
        }
    } ~
      // curl -X DELETE  http://localhost:8091/basket/1
      path("basket" / LongNumber) { id =>

        delete {
          onSuccess(basket ? DeleteArticle(id.toInt)) {
            case ArticleDeleted => complete(NoContent)
            case _ => complete(BadRequest)
          }

        }
      }
  }

}
