package one.lab.tasks.week.one

import scala.annotation.tailrec

object Recursion {
  def printNTimes(n: Int, value: String): Unit = {
    if(n > 1)
      printNTimes(n - 1, value)
    println(value)
  }

  def gcd(a: Long, b: Long): Long = {
    if(b == 0) a else gcd(b, a % b)
  }

  def nthFibonacciNumber(n: Int): Int = {
    if(n < 1) 0
    else if(n == 1 || n == 2) 1
    else nthFibonacciNumber(n - 1) + nthFibonacciNumber(n - 2)
  }

  def tailRecursiveFibonacciNumber(n: Int): Int = {
    @tailrec
    def fibonacciNumber(i: Int, pre: Int, prev: Int, cur: Int): Int = {
      if(i == n) cur
      else fibonacciNumber(i + 1, prev, cur, prev + cur)
    }
    if(n < 1) 0
    else fibonacciNumber(1, 0, 0, 1)
  }
}
