package mypackage

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

case class XY(x: Double = 0, y: Double = 0)

trait Abstract {
  type Vector2f

  trait ConstructVector2f {
    def apply(x: Double, y: Double): Vector2f
  }
  implicit val Vector2f: ConstructVector2f
}

object Concrete extends Abstract {
  type Vector2f = XY
  object Vector2f extends ConstructVector2f {
    def apply(x: Double, y: Double) = XY(x, y)
  }
}

object Abstract {
  val Link: Abstract = Concrete
}

import Abstract.Link.Vector2f

case class Container(@JsonDeserialize(as=classOf[XY]) from: Vector2f)

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