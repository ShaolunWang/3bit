package machine;

// using case class for immutability
// NOTE: we are copy the state but I guess we could
// also use mutable classes?
case class Registers(
    x: Int,
    y: Int,
    z: Int,
    out: Vector[Int] = Vector.empty
)
