package alleycats

import cats.Eq
import cats.syntax.eq._

import simulacrum.typeclass

@typeclass trait Zero[A] extends Serializable {
  def zero: A

  def isZero(a: A)(implicit ev: Eq[A]): Boolean =
    zero === a

  def nonZero(a: A)(implicit ev: Eq[A]): Boolean =
    zero =!= a
}

object Zero {
  def apply[A](a: => A): Zero[A] =
    new Zero[A] { lazy val zero: A = a }

  /* ======================================================================== */
  /* THE FOLLOWING CODE IS MANAGED BY SIMULACRUM; PLEASE DO NOT EDIT!!!!      */
  /* ======================================================================== */

  /**
   * Summon an instance of [[Zero]] for `A`.
   */
  @inline def apply[A](implicit instance: Zero[A]): Zero[A] = instance

  @deprecated("Use cats.syntax object imports", "2.2.0")
  object ops {
    implicit def toAllZeroOps[A](target: A)(implicit tc: Zero[A]): AllOps[A] {
      type TypeClassType = Zero[A]
    } =
      new AllOps[A] {
        type TypeClassType = Zero[A]
        val self: A = target
        val typeClassInstance: TypeClassType = tc
      }
  }
  trait Ops[A] extends Serializable {
    type TypeClassType <: Zero[A]
    def self: A
    val typeClassInstance: TypeClassType
    def isZero(implicit ev: Eq[A]): Boolean = typeClassInstance.isZero(self)(ev)
    def nonZero(implicit ev: Eq[A]): Boolean = typeClassInstance.nonZero(self)(ev)
  }
  trait AllOps[A] extends Ops[A]
  trait ToZeroOps extends Serializable {
    implicit def toZeroOps[A](target: A)(implicit tc: Zero[A]): Ops[A] {
      type TypeClassType = Zero[A]
    } =
      new Ops[A] {
        type TypeClassType = Zero[A]
        val self: A = target
        val typeClassInstance: TypeClassType = tc
      }
  }
  @deprecated("Use cats.syntax object imports", "2.2.0")
  object nonInheritedOps extends ToZeroOps

  /* ======================================================================== */
  /* END OF SIMULACRUM-MANAGED CODE                                           */
  /* ======================================================================== */

}
