package one.lab.tasks.week.two

import one.lab.tasks.week.two.Products._

object Options {
  trait Error

  case class User(name: String, login: String, password: String)

  private val credentialsDb: Map[String, String] = Map(
    "user1" -> "password",
    "user2" -> "password",
    "user3" -> "password"
  )

  private val userProductsDb: Map[String, List[UserProducts]] = Map(
    "user1" -> List(MobileBankingProduct("", 0.0), ShopPlatformProduct("")),
    "user3" -> List(MobileBankingProduct("", 0.0), ShopPlatformProduct(""))
  )

  private def authorize(login: String, password: String): Either[Error, User] = ???
  private def getSubscribedProducts(user: User): List[UserProducts]           = ???
//  private def getMobileBankingDetails()
}
