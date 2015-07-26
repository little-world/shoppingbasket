package shoppingbasket

import com.typesafe.config.ConfigFactory

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.io.IO
import spray.can.Http
 


object Main extends App {
  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")
 
  implicit val system = ActorSystem("shoppingbacket")
 
  val basket = system.actorOf(Props[BasketActor], "shoppingbasket-actor")
  val service = system.actorOf(Props(classOf[RestInterface],basket), "shoppingbasket-service")

 
  implicit val executionContext = system.dispatcher
 
  IO(Http) ! Http.Bind(service, interface = host, port = port)
}
