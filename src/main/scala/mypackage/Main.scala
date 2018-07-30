package mypackage

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

case class XY(x: Double = 0, y: Double = 0)

object Abstract {
  final case class Vector2f(v: XY) extends AnyVal
  object Vector2f {
    def apply(x: Double, y: Double) = new Vector2f(XY(x, y))
  }
}

import Abstract.Vector2f

case class Container(from: Vector2f)

object Main extends App {

  val mapper = new ObjectMapper with ScalaObjectMapper

  mapper.registerModule(DefaultScalaModule)

  val input = Container(Vector2f(0,0))

  val out = mapper.writeValueAsString(input)

  val loaded = mapper.readValue[Container](out)

  val xy: Vector2f = loaded.from

  println(loaded) // prints "Container(Map(x -> 0.0, y -> 0.0))" instead of "Container(XY(0.0,0.0))"
  assert(xy.isInstanceOf[XY]) // fails
}