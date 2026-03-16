package machine
class InstructionTestSuite extends munit.FunSuite {

  test("xdv divides x by 2^combo_operand") {
    // default ip = 0
    val regs = Registers(x = 16, y = 0, z = 0)
    // opcode 0
    // div 2^2 as given in the example
    val newRegs = Instructions.execute(0, 2, regs)
    assertEquals(newRegs.x, 4)
    assertEquals(newRegs.ip, 2)
  }

  test("xdv truncates") {
    // 10 / 4 = 2.5 trunc to 2
    val regs1 = Registers(x = 10, y = 0, z = 0, ip = 0)
    val newRegs1 = Instructions.execute(0, 2, regs1)
    assertEquals(newRegs1.x, 2)
    assertEquals(newRegs1.ip, 2)
  }

  test("yxl with literal operand") {
    val regs = Registers(x = 0, y = 5, z = 0, ip = 0)
    // opcode 1
    val newRegs = Instructions.execute(1, 3, regs)
    // 5 ^ 3 = 6
    assertEquals(newRegs.y, 6)
    assertEquals(newRegs.ip, 2)
  }
  test("yst with combo operand") {
    val regs = Registers(x = 0, y = 0, z = 9, ip = 0)
    val newRegs = Instructions.execute(2, 6, regs)
    assertEquals(newRegs.y, 1)
    assertEquals(newRegs.ip, 2)
  }
  test("jnz literal jump when x!=0") {
    val regs = Registers(x = 5, y = 0, z = 0, ip = 0)
    val newRegs = Instructions.execute(3, 6, regs)
    assertEquals(newRegs.ip, 6)
  }
  test("jnz continue on x=0") {
    val regs = Registers(x = 0, y = 0, z = 0, ip = 0)
    val newRegs = Instructions.execute(3, 6, regs)
    assertEquals(newRegs.ip, 2)
  }
  test("yxz xors y and z, write to y") {
    val regs = Registers(x = 0, y = 10, z = 5, ip = 0)
    val newRegs = Instructions.execute(4, 123, regs)
    assertEquals(newRegs.y, 10 ^ 5)
    assertEquals(newRegs.z, 5)
    assertEquals(newRegs.x, 0)
    assertEquals(newRegs.ip, 2)
  }
  test("out outputs combo % 8") {
    val regs = Registers(x = 0, y = 0, z = 0, ip = 0)
    val newRegs = Instructions.execute(5, 3, regs)
    assertEquals(newRegs.out, Vector(3))
    assertEquals(newRegs.ip, 2)
  }
  test("out outputs reg % 8") {
    val regs = Registers(x = 0, y = 0, z = 9, ip = 0)
    val newRegs = Instructions.execute(5, 6, regs)
    // 9 % 8
    assertEquals(newRegs.out, Vector(1))
    assertEquals(newRegs.ip, 2)
  }
}
