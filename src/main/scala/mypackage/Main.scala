package mypackage

import io.circe._
import io.circe.generic.semiauto._
import io.circe.parser._
import io.circe.syntax._

case class XY(x: Double = 0, y: Double = 0)

trait Abstract {
  type Vector2f

  trait ConstructVector2f {
    def apply(x: Double, y: Double): Vector2f
  }
  implicit val Vector2f: ConstructVector2f

  trait Vector2fOps {
    def x: Double
    def y: Double
  }
  implicit def Vector2fOps(v: Vector2f): Vector2fOps

  implicit val vector2fEncoder: Encoder[Vector2f] = Encoder.forProduct2("x", "y")(v => (v.x, v.y))
  implicit val vector2fDecoder: Decoder[Vector2f] = Decoder.forProduct2("x", "y")(Vector2f.apply)
}

object Concrete extends Abstract {
  type Vector2f = XY

  object Vector2f extends ConstructVector2f {
    def apply(x: Double, y: Double) = XY(x, y)
  }

  class ConcreteVector2fOps(vector2f: Vector2f) extends Vector2fOps {
    def x = vector2f.x
    def y = vector2f.y
  }
  def Vector2fOps(v: Vector2f): Vector2fOps = new ConcreteVector2fOps(v)
}

object Abstract {
  val Link: Abstract = Concrete

}

import Abstract.Link._

case class Container(from: Vector2f)

object Container {

  implicit val vector2fEncoder: Encoder[Vector2f] = Encoder.forProduct2("x", "y")(v => (v.x, v.y))
  implicit val vector2fDecoder: Decoder[Vector2f] = Decoder.forProduct2("x", "y")(Vector2f.apply)

  implicit val encoder: Encoder[Container] = deriveEncoder
  implicit val decoder: Decoder[Container] = deriveDecoder
}
object Main extends App {


  val input = Container(Vector2f(0,0))

  val out = input.asJson.spaces2

  val loaded = decode[Container](out).right.get

  val xy: Vector2f = loaded.from

  println(loaded) // prints "Container(Map(x -> 0.0, y -> 0.0))" instead of "Container(XY(0.0,0.0))"
  assert(xy.isInstanceOf[XY]) // fails
}