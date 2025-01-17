package algebra
package lattice

import scala.{specialized => sp}

/**
 * De Morgan algebras are bounded lattices that are also equipped with
 * a De Morgan involution.
 *
 * De Morgan involution obeys the following laws:
 *
 *  - ¬¬a = a
 *  - ¬(x∧y) = ¬x∨¬y
 *
 * However, in De Morgan algebras this involution does not necessarily
 * provide the law of the excluded middle. This means that there is no
 * guarantee that (a ∨ ¬a) = 1. De Morgan algebra do not not necessarily
 * provide the law of non contradiction either. This means that there is
 * no guarantee that (a ∧ ¬a) = 0.
 *
 * De Morgan algebras are useful to model fuzzy logic. For a model of
 * classical logic, see the boolean algebra type class implemented as
 * [[Bool]].
 */
trait DeMorgan[@sp(Int, Long) A] extends Any with Logic[A] { self =>
  def meet(a: A, b: A): A = and(a, b)

  def join(a: A, b: A): A = or(a, b)

  def imp(a: A, b: A): A = or(not(a), b)
}

trait DeMorganFunctions[H[A] <: DeMorgan[A]]
    extends BoundedMeetSemilatticeFunctions[H]
    with BoundedJoinSemilatticeFunctions[H]
    with LogicFunctions[H]

object DeMorgan extends DeMorganFunctions[DeMorgan] {

  /**
   * Access an implicit `DeMorgan[A]`.
   */
  @inline final def apply[@sp(Int, Long) A](implicit ev: DeMorgan[A]): DeMorgan[A] = ev

  /**
   * Turn a [[Bool]] into a `DeMorgan`
   * Used for binary compatibility.
   */
  final def fromBool[@sp(Int, Long) A](bool: Bool[A]): DeMorgan[A] =
    new DeMorgan[A] {
      def and(a: A, b: A): A = bool.and(a, b)

      def or(a: A, b: A): A = bool.or(a, b)

      def not(a: A): A = bool.complement(a)

      def one: A = bool.one

      def zero: A = bool.zero
    }
}
