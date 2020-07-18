package one.lab.demos.week.four

import akka.actor.ActorSystem
import akka.actor.Props
import akka.stream.Materializer
import one.lab.demos.week.four.GithubActor.GetUser

object BootGithubActor extends App {

  implicit val system       = ActorSystem()
  implicit val materailizer = Materializer.createMaterializer(system)

  val propsForGithubActor = Props(new GithubActor())

  val githuberActor = system.actorOf(propsForGithubActor)

  githuberActor ! GetUser("arturka")
}
