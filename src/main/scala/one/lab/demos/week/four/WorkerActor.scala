package one.lab.demos.week.four

import akka.actor.Actor
import akka.actor.ActorRef
import one.lab.demos.week.four.MasterActor.ComputationResult

class WorkerActor extends Actor {

  var state: Int = 0

//  def func: String => Unit = str => str match {
//    case "lalka" => println("hey lalka")
//  }

  override def receive: Receive = {
//  ! tell child ! "sss"
//  ? ask  child ? "sss"
    case s: String =>
      val sender: ActorRef = context.sender()
      println(s"${sender.path} received string [${s}] to compute")
      println(s"${sender.path} sending result back to sender")
      Thread.sleep(5000)
      sender ! ComputationResult(s, s.length)
    case i: Int =>
      state += i
      println(s"echo int: ${i}")
    case x => println(s"received unhandled object ${x.getClass.getName}")
  }

}
