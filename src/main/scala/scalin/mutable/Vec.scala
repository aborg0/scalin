package scalin
package mutable

import spire.algebra._
import spire.syntax.ring._
import spire.syntax.cfor._

trait Vec[A] extends scalin.Vec[A] { lhs =>

  def set(k: Int, a: A): Unit

  def set(sub: Subscript, rhs: A): Unit = {
    val ind = sub.forLength(length)
    cforRange(0 until ind.length) { k =>
      set(ind(k), rhs)
    }
  }

  def set(sub: Subscript, givenRhs: Vec[A]): Unit = {
    val ind = sub.forLength(length)
    val rhs = givenRhs.copyIfOverlap(lhs)
    val n = ind.length
    require(n == rhs.length)
    cforRange(0 until n) { k =>
      set(ind(k), rhs(k))
    }
  }

}

object Vec extends VecFactory[Vec, Dummy] {

  def tabulate[A:Dummy](length: Int)( f: Int => A ): Vec[A] =
    DenseVec.tabulate[A](length)(f)

}
