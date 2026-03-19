package machine

import munit.FunSuite

class MachineTestSuite extends FunSuite {

  test("IF stage") {
    val m = Machine(
      regs = Registers(0, 0, 0),
      program = Vector(1, 3), // yxl
      opcode = 0,
      operand = 0,
      aluRes = 0,
      ip = 0
    )

    val m2 = fetch(m)

    assertEquals(m2.opcode, 1)
    assertEquals(m2.operand, 3)
    assertEquals(m2.phase, Phase.EX)
    assertEquals(m2.ip, 2)
  }

  test("EX + WB (yxl)") {
    val m = Machine(
      regs = Registers(x = 0, y = 5, z = 0),
      program = Vector(1, 3), // yxl 3 -> 5 ^ 3 = 6
      opcode = 0,
      operand = 0,
      aluRes = 0,
      ip = 0
    )

    val m2 = step3(m)

    assertEquals(m2.regs.y, 6)
  }

  test("xdv updates X") {
    val m = Machine(
      regs = Registers(x = 16, y = 0, z = 0),
      program = Vector(0, 2), // xdv 2 -> 16 / 4 = 4
      opcode = 0,
      operand = 0,
      aluRes = 0,
      ip = 0
    )

    val m2 = step3(m)

    assertEquals(m2.regs.x, 4)
  }

  test("out writes to output") {
    val m = Machine(
      regs = Registers(x = 0, y = 0, z = 0, out = Vector()),
      program = Vector(5, 3), // out 3
      opcode = 0,
      operand = 0,
      aluRes = 0,
      ip = 0
    )

    val m2 = step3(m)

    assertEquals(m2.regs.out, Vector(3))
  }

  test("jnz jumps when x != 0") {
    val m = Machine(
      regs = Registers(x = 1, y = 0, z = 0),
      program = Vector(3, 4, 0, 0, 0, 0), // jnz 4
      opcode = 0,
      operand = 0,
      aluRes = 0,
      ip = 0
    )

    val m2 = writeBack(execute(fetch(m)))

    assertEquals(m2.ip, 4)
  }

  test("jnz x=0 case") {
    val m = Machine(
      regs = Registers(x = 0, y = 0, z = 0),
      program = Vector(3, 4), // jnz 4
      opcode = 0,
      operand = 0,
      aluRes = 0,
      ip = 0
    )

    val m2 = step3(m)

    // ip should stay at 2
    assertEquals(m2.ip, 2)
  }

  test("multi instrs") {
    val m = Machine(
      regs = Registers(x = 16, y = 0, z = 0, out = Vector()),
      program = Vector(
        0,
        2, // xdv
        5,
        4 // out
      ),
      opcode = 0,
      operand = 0,
      aluRes = 0,
      ip = 0
    )

    val m1 = step3(m)
    val m2 = step3(m1)

    assertEquals(m2.regs.x, 4)
    assertEquals(m2.regs.out, Vector(4))
  }

  test("example 1") {
    val prog = Vector(0, 1, 5, 4, 3, 0)
    var m = Machine(
      regs = Registers(x = 3729, y = 0, z = 0, out = Vector()),
      program = prog,
      opcode = 0,
      operand = 0,
      aluRes = 0,
      ip = 0
    )

    while (m.ip < prog.length) {
      m = step3(m)
    }

    assertEquals(m.regs.out, Vector(0, 4, 2, 1, 4, 2, 5, 6, 7, 3, 1, 0))
  }
  test("example 2") {
    val prog = Vector(0, 3, 5, 4, 3, 0)
    val reg = Registers(x = 8642024, y = 0, z = 0, out = Vector())
    var m = Machine(
      regs = Registers(x = 3729, y = 0, z = 0, out = Vector()),
      program = prog,
      opcode = 0,
      operand = 0,
      aluRes = 0,
      ip = 0
    )
    while (m.ip < prog.length) {
      m = step3(m)
    }
  }
}
