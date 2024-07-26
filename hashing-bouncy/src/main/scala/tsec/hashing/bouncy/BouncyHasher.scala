package tsec.hashing.bouncy

import cats.Applicative
import fs2.{Chunk, Pipe, Stream}
import tsec.hashing.{CryptoHash, CryptoHasher}

import java.security.MessageDigest

final class BouncyHasher[F[_], A] private[bouncy](val algorithm: String)(
  implicit F: Applicative[F]
) extends CryptoHasher[F, A] {

  private def genInstance = MessageDigest.getInstance(algorithm, "BC")

  def hash(bytes: Array[Byte]): F[CryptoHash[A]] =
    F.pure(CryptoHash[A](genInstance.digest(bytes)))

  /** In this case, we use the same code as fs2, but we resolve
    * the hash string prefix from the implicit
    */
  def hashPipe: Pipe[F, Byte, Byte] =
    in =>
      Stream.suspend[F, Byte] {
        in.chunks
          .fold(genInstance) { (d, c) =>
            val bytes = c.toArray
            d.update(bytes, 0, bytes.length)
            d
          }
          .flatMap { d =>
            Stream.chunk(Chunk.array(d.digest()))
          }
      }
}
