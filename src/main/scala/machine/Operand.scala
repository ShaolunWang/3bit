package machine;

enum ComboOperand:
  case Literal(value: Int)
  case X
  case Y
  case Z

object ComboOperand:
  object Literal:
    def apply(value: Int): ComboOperand =
      if value >= 0 && value <= 3 then new ComboOperand.Literal(value)
      else throw new IllegalArgumentException("invalid literal value")

object Operand {
  def literal(op: Int) = op
  def combo(op: Int): ComboOperand = op match {
    case 0 | 1 | 2 | 3 => ComboOperand.Literal(op)
    case 4             => ComboOperand.X
    case 5             => ComboOperand.Y
    case 6             => ComboOperand.Z
    case _             => throw new IllegalArgumentException("Invalid operand")
  }
  def eval(op: ComboOperand, r: Registers): Int = {
    op match
      case ComboOperand.Literal(v) => v
      case ComboOperand.X          => r.x
      case ComboOperand.Y          => r.y
      case ComboOperand.Z          => r.z

  }
}
