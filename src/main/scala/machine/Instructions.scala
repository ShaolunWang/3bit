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
        // do nothing
      case _: Int => r
    }

}
