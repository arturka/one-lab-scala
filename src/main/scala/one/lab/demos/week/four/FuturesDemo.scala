package one.lab.demos.week.four

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.Future
import scala.concurrent.ExecutionContext._
import scala.util.Random

object FuturesDemo extends App {
//  side effect -> println, HTTP.get(), DB.query("")
//  referential transparent
  implicit val ex: ExecutionContextExecutor = global

  def putLn(x: Int) = println(x)
  val someInt       = 4
  putLn(someInt)
  putLn(someInt)
  putLn(2 * 2)

  // eagerly evaluated
  // memoized
  val f1 = {
    val r = new Random(0L)
    val x = Future(r.nextInt)

//  do notation
    for {
      a <- x
      b <- x
    } yield (a, b)
  }

  // Same as f1, but I inlined `x`
  val f2 = {
    val r = new Random(0L)
    for {
      a <- Future(r.nextInt)
      b <- Future(r.nextInt)
    } yield (a, b)
  }

  f1.onComplete(println) // Success((-1155484576,-1155484576))
  f2.onComplete(println) // Success((-1155484576,-723955400))
}
