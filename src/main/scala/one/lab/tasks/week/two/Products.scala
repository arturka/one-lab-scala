package one.lab.tasks.week.two

object Products {
  sealed trait UserProducts

  case class MobileBankingProduct(number: String, balance: Double) extends UserProducts
  case class ShopPlatformProduct(accountId: String)                extends UserProducts
  case class StreamingPlusProduct(watchList: List[String])         extends UserProducts

}
