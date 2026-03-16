package machine;

object Instructions {
  def execute(opcode: Int, operand: Int, r: Registers): Registers =
    opcode match {
      case 0 => // xdv
        val shift = Operand.combo(operand, r).toInt
        r.copy(
          x = r.x >> shift,
          y = r.y,
          z = r.z,
          ip = r.ip + 2
        )
      case 1 => // yxl
        val xor = Operand.literal(operand) ^ r.y
        r.copy(x = r.x, y = xor, z = r.z, ip = r.ip + 2)
      case 2 => // yst
        val mod8 = Operand.combo(operand, r).toInt % 8
        r.copy(x = r.x, y = mod8, z = r.z, ip = r.ip + 2)
      case 3 => // jnz
        if (r.x == 0) // do nothing, advance ip
          r.copy(x = r.x, y = r.y, z = r.z, ip = r.ip + 2)
        else
          val literal = Operand.literal(operand)
          r.copy(x = r.x, y = r.y, z = r.z, ip = literal)
      case _: Int => r
    }

}
