package machine;
// ALU eval
// NOTE: the IP here is merely for jump evals
object Instructions {
  val XDV: (Int, Int, Registers) => Int =
    (operand, _, r) => {
      val shift = Operand.get(operand, r)
      val res = r.x >> shift
      res
    }

  val YXL: (Int, Int, Registers) => Int =
    (operand, _, r) => {
      val res = Operand.literal(operand) ^ r.y
      res
    }

  val YST: (Int, Int, Registers) => Int =
    (operand, _, r) => {
      val res = Operand.get(operand, r) % 8
      res
    }

  val JNZ: (Int, Int, Registers) => Int =
    (operand, ip, r) => {
      val res =
        if r.x == 0 then
          ip // NOTE: we DONT have to update it here, it's done in IF
        else Operand.literal(operand)
      res
    }

  val YXZ: (Int, Int, Registers) => Int =
    (operand, _, r) => {
      val res = r.y ^ r.z
      res
    }

  val OUT: (Int, Int, Registers) => Int =
    (operand, _, r) => {
      val res = Operand.get(operand, r) % 8
      res
    }

  val YDV: (Int, Int, Registers) => Int =
    (operand, _, r) => {
      val shift = Operand.get(operand, r)
      val res = r.x >> shift
      res
    }

  val ZDV: (Int, Int, Registers) => Int =
    (operand, _, r) => {
      val shift = Operand.get(operand, r)
      val res = r.x >> shift
      res
    }
}
