package one.lab.demos.week.four

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.SupervisorStrategy
import akka.stream.Materializer
import akka.pattern.pipe
import one.lab.demos.week.four.GithubActor.GetRepositories
import one.lab.demos.week.four.GithubActor.GetUser
import one.lab.demos.week.four.GithubActor.GetUserAllInfo
import one.lab.demos.week.four.GithubActor.GithubRepository
import one.lab.demos.week.four.GithubActor.GithubUser
import one.lab.tasks.week.three.RestClientImpl
import org.json4s.DefaultFormats
import org.json4s.Formats
import org.json4s.jackson.JsonMethods.parse

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success

object GithubActor {
  case class GetUserAllInfo(login: String)
  case class GetUser(login: String)
  case class GetRepositories(repoUrl: String)

  case class GithubUser(
      login: String,
      name: String,
      location: Option[String],
      company: Option[String],
      repos_url: String
  )

  case class GithubRepository(
      name: String,
      description: Option[String],
      full_name: String,
      fork: Boolean,
      language: String
  )

}

class GithubActor()(implicit val system: ActorSystem, materializer: Materializer) extends Actor {

  override def supervisorStrategy: SupervisorStrategy = super.supervisorStrategy

  // global ExecutionContext = import scala.concurrent.ExecutionContext.global - 2 threads
  // actors - ExecutionContext - 1 threads

  implicit val formats: Formats     = DefaultFormats
  implicit val ex: ExecutionContext = context.dispatcher

  def getGithubUser(username: String): Future[GithubUser] =
    RestClientImpl.get(s"https://api.github.com/users/$username").map(x => parse(x).extract[GithubUser])

  def getUserRepositories(repoUrl: String): Future[List[GithubRepository]] =
    RestClientImpl.get(s"$repoUrl").map(x => parse(x).extract[List[GithubRepository]])

  override def receive: Receive = {
    case GetUser(login) =>
//      val f: Future[GithubUser] = getGithubUser(login)
//      f.onComplete {
//        case Success(value) => self ! value
//        case Failure(fail)  => println("error")
//      }
      getGithubUser(login).flatMap(user => getUserRepositories(user.repos_url)).pipeTo(self)
    case user: GithubUser => println(user)
    case GetRepositories(repoUrl) =>
      getUserRepositories(repoUrl).onComplete {
        case Success(result) => sender ! result
      }
    case obj: List[GithubRepository]     => obj.foreach(x => println(x))
    case akka.actor.Status.Failure(fail) => println(s"received failure ${fail.getMessage}")
    case obj: GithubRepository           => obj.description.getOrElse("Default")
    case obj                             => println(s"received unhandled object of type ${obj.getClass.getName}")

  }

}
