package mypackage

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import scala.reflect._

case class XY(x: Double = 0, y: Double = 0) {
  def hidden = ???
}

trait SuperAbstract {
  type Vector2f

  implicit val ctagVector2f: ClassTag[Vector2f]

  trait ConstructVector2f {
    def apply(x: Double, y: Double): Vector2f
  }
  implicit val Vector2f: ConstructVector2f
}

abstract class Abstract[Vec2f: ClassTag](implicit ev: ClassTag[Vec2f]) extends SuperAbstract {
  type Vector2f = Vec2f
  val ctagVector2f: ClassTag[Vec2f] = ev
}

object Concrete extends Abstract[XY] {

  object Vector2f extends ConstructVector2f {
    def apply(x: Double, y: Double) = XY(x, y)
  }
}

object Abstract {
  val Link: SuperAbstract = Concrete
}

import Abstract.Link.Vector2f
import Abstract.Link._

case class Container(from: Vector2f)

object Main extends App {

  val mapper = new ObjectMapper with ScalaObjectMapper

  mapper.registerModule(DefaultScalaModule)

  val input = Container(Vector2f(0,0))

  val out = mapper.writeValueAsString(input)

  val loaded = mapper.readValue[Container](out)

  val xy: Vector2f = loaded.from

  //println(manifest[Vector2f])
  printClass[Vector2f]()

  def printClass[T: ClassTag]() = {
    println(classTag[T])
  }

  println(loaded) // prints "Container(Map(x -> 0.0, y -> 0.0))" instead of "Container(XY(0.0,0.0))"
  assert(xy.isInstanceOf[XY]) // fails
}