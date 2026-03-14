package machine;

// using case class for immutability
case class Registers(x: Int, y: Int, z: Int, out: Vector[Int] = Vector.empty())
