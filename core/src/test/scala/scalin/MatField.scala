package scalin

import spire.math.Rational

import algos.LUDecomposition

class MatField extends ScalinSuite {

  import scalin.immutable.dense._
  import spire.laws.arb.rational

  test("Inverses of matrices of determinant one") {
    forAll(Mats.genDetOne[Rational](4)) { m =>
      (m * m.inverse) shouldBe eye[Rational](4)
    }
  }

  test("Inverses of full rank matrices") {
    forAll(Mats.genFullRank[Rational](4)) { m =>
      (m * m.inverse) shouldBe eye[Rational](4)
    }
  }

  test("LU decomposition") {
    forAll(Mats.genFullRank[Rational](3)) { m =>
      import scalin.mutable.dense._
      val dec: LUDecomposition[Rational] = LUDecomposition.inPlaceLU(implicitly[scalin.mutable.MatEngine[Rational]].fromMat(m))
      val lu = dec.lower * dec.upper
      cforRange(0 until m.nRows) { k => m(dec.pivot(k), ::) shouldBe lu(k, ::) }
    }
  }

}
