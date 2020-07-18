package one.lab.demos.week.four

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import akka.util.Timeout
import one.lab.demos.week.four.MasterActor.ComputationResult
import one.lab.demos.week.four.MasterActor.DoComputation

import scala.concurrent.duration._
import scala.concurrent._

object MasterActor {
  case class DoComputation(str: String)
  case class ComputationResult(str: String, length: Int)
}

/**
  */
class MasterActor() extends Actor {

//  S - Single responsibility principe
  val childActor: ActorRef = context.actorOf(Props(new WorkerActor))

  implicit val defaultTimeout: Timeout = 3.seconds
  implicit val ex: ExecutionContext    = ExecutionContext.global

  override def receive: Receive = {
    case command: DoComputation =>
      println(s"sending ${command} to ${childActor.path}")
//      childActor.tell(command.str) // fire and forget
//      ask(childActor, command.str) // send and wait
//      (akka.actor.actorRef2Scala(childActor).!(command.str)) (one.lab.demos.week.four.MasterActor.self)
//      childActor.tell(command.str, self)

//      val result: Future[Any] = ask(childActor, command.str) == childActor ? command.str
//      val result: Future[ComputationResult] = (childActor ? command.str).mapTo[ComputationResult]
      childActor ! command.str

//      result.onComplete {
//        case Success(value) => println(s"received Computation Result $value")
//        case Failure(ex)    => println(ex.getMessage)
//      }

//      childActor ! command.str
    case ComputationResult(str, length) => println(s"MasterActor received result back ${str}, $length")
//    case EchoToConsole(str) => childActor.tell(str, context.self)
  }

}
