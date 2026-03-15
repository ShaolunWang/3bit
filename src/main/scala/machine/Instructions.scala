package machine;

object Instructions {
  def execute(opcode: Int, operand: Int, r: Registers): Registers =
    opcode match {
      case 0 => // xdv
        val shift = Operand.combo(operand, r).toInt
        r.copy(
          x = r.x >> shift,
          ip = r.ip + 2
        )
      case 1 => // yxl
        val xor = Operand.literal(operand) ^ r.y
        r.copy(x = r.x, y = xor, ip = r.ip + 2)
      case 2 => // yst
        val mod8 = Operand.combo(operand, r).toInt % 8
        r.copy(x = r.x, y = mod8, ip = r.ip + 2)
      case _: Int => r
    }

}
