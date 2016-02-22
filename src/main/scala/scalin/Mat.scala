package scalin

/** Concrete matrix trait. */
trait Mat[A] extends AbstractMat[A] { lhs =>

  def touch(node: AbstractNode) = if (node eq Mat.this) Touch.AsIs() else Touch.Clean()

  override def toString: String = Printer.mat(Mat.this)

}

object Mat extends MatFactory[Mat, Dummy] {

  def tabulate[A:Dummy](rows: Int, cols: Int)( f: (Int, Int) => A ): Mat[A] =
    immutable.DenseMat.tabulate[A](rows, cols)(f)

}