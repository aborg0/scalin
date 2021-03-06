package scalin

import spire.algebra.Eq
import spire.math._
import spire.util.Opt

/** Typeclass to perform pivot selection in a generic manner when using
  * either approximate precision or exact types.
  */
trait Pivot[A] extends Any {

  /** Function used to determine the priority of pivot element selection.
    * Higher values are chosen in priority. 
    * 
    * Required properties:
    * - `pivotPriority(a) >= 0`,
    * - `pivotPriority(a) == 0` if and only if `a == 0`.
    * 
    */
  def priority(a: A): Double

  /** Returns whether `a` is close to zero, up to chosen tolerance. */
  def closeToZero(a: A): Boolean

  def optionalExactEq: Opt[Eq[A]]

}

object Pivot {

  def apply[A](implicit ev: Pivot[A]): Pivot[A] = ev

  val tolerance = 1e-10

  def double(tolerance: Double): Pivot[Double] = new Pivot[Double] {

    def priority(x: Double) = x.abs

    def closeToZero(a: Double) = a.abs < tolerance

    def optionalExactEq = Opt.empty[Eq[Double]]

  }

  implicit object safeLong extends Pivot[SafeLong] {

    def priority(x: SafeLong) = x.toDouble.abs // TODO: implement simplest denominator/numerator bitlength selection

    def closeToZero(x: SafeLong) = x.isZero

    def optionalExactEq = Opt(Eq[SafeLong])

  }

  implicit object rational extends Pivot[Rational] {

    def priority(x: Rational) = x.toDouble.abs // TODO: implement simplest denominator/numerator bitlength selection

    def closeToZero(x: Rational) = x.isZero

    def optionalExactEq = Opt(Eq[Rational])

  }

}
