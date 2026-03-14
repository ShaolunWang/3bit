package machine;

object Operand {
  def literal(op: Int) = op
  def combo(op: Int, r: Registers): Int = op match {
    case 0 | 1 | 2 | 3 => op
    case 4             => r.x
    case 5             => r.y
    case 6             => r.z
    case _             => throw new IllegalArgumentException("Invalid operand")
  }
}
