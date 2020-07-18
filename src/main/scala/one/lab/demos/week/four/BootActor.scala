package one.lab.demos.week.four

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import one.lab.demos.week.four.MasterActor.DoComputation

import scala.concurrent.duration._
import scala.util.Failure
import scala.util.Success

object BootActor extends App {
  val system      = ActorSystem("demo-system")
  implicit val ex = system.dispatcher

//  val propsForEchoActor = Props(new EchoActor("lalka", 3))
//
//  val userA = system.actorOf(propsForEchoActor, "a")
//  val userB = system.actorOf(propsForEchoActor, "b")

//  userA ! "Hello" == userA.tell("hello", Actor.noSender)
//  userA ! "Hello"
//  userA.tell(3, Actor.noSender)
//  userA.tell(4, Actor.noSender)
//  userA.tell(3.14f, Actor.noSender)

  system.actorOf(Props(new MasterActor), "master-actor")

  system.actorSelection("/user/master-actor").resolveOne()(3.seconds).onComplete {
    case Success(ref)  => ref ! DoComputation("some message")
    case Failure(fail) => println(fail.getMessage)
  }

//  masterActor ! DoComputation("some message")
}

// EchoToConsole("lalka") -> MasterActor (str) -> WorkerActor -> console
// EchoToConsole("lalka") -> MasterActor (str) -> WorkerActor ->
//                                       length <-            <- computeLength(str)
//                                       println(length)
