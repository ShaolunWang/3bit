package machine;

object Instructions {
  def execute(opcode: Int, operand: Int, r: Registers): Registers =
    opcode match {
      case 0 =>
        val shift = Operand.combo(operand, r).toInt
        r.copy(
          x = r.x >> shift,
          ip = r.ip + 2
        )
      case 1 =>
        val xor = Operand.literal(operand) ^ r.y
        r.copy(x = r.x, y = xor, ip = r.ip + 2)
      case _: Int => r
    }

}
