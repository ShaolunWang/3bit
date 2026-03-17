package machine;
// ALU eval
object Instructions {
  val XDV: (Int, Int, Int, Registers) => Int =
    (opcode, operand, ip, r) => {
      val shift = Operand.eval(Operand.combo(operand), r)
      val res = r.x >> shift
      res
    }

  val YXL: (Int, Int, Int, Registers) => Int =
    (opcode, operand, ip, r) => {
      val res = Operand.literal(operand) ^ r.y
      res
    }

  val YST: (Int, Int, Int, Registers) => Int =
    (opcode, operand, ip, r) => {
      val res = Operand.eval(Operand.combo(operand), r) % 8
      res
    }

  val JNZ: (Int, Int, Int, Registers) => Int =
    (opcode, operand, ip, r) => {
      val res =
        if r.x == 0 then ip + 2
        else Operand.literal(operand)
      res
    }

  val YXZ: (Int, Int, Int, Registers) => Int =
    (opcode, operand, ip, r) => {
      val res = r.y ^ r.z
      res
    }

  val OUT: (Int, Int, Int, Registers) => Int =
    (opcode, operand, ip, r) => {
      val res = Operand.eval(Operand.combo(operand), r) % 8
      res
    }

  val YDV: (Int, Int, Int, Registers) => Int =
    (opcode, operand, ip, r) => {
      val shift = Operand.eval(Operand.combo(operand), r)
      val res = r.x >> shift
      res
    }

  val ZDV: (Int, Int, Int, Registers) => Int =
    (opcode, operand, ip, r) => {
      val shift = Operand.eval(Operand.combo(operand), r)
      val res = r.x >> shift
      res
    }
}
