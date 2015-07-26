package shoppingbasket

/**
 * @author littleworld
 */

import org.scalatest.FlatSpec

class BasketSpec extends FlatSpec {

  "A basket" should "return a list of 2 articles" in new Basket {
    assert(list.size === 2)
  }

  it should "return a list of 3 articles when an article is added" in new Basket {
    add(Article(3, "scala in action", "a scala book", 2500, 1))
    assert(list.size === 3)
  }

  it should "return a list of 1 articles when an article is deleted" in new Basket {
    delete(2)
    assert(list.size === 1)
  }

}