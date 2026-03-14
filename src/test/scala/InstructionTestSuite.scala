package machine
class MySuite extends munit.FunSuite {

  test("xdv divides X by 2^combo_operand") {
    // default ip = 0
    val regs = Registers(x = 16, y = 0, z = 0)
    // opcode 0
    // div 2^2 as given in the example
    val newRegs = Instructions.execute(0, 2, regs)
    assertEquals(newRegs.x, 4)
    assertEquals(newRegs.ip, 2)
  }
}
