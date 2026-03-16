package machine
class InstructionTestSuite extends munit.FunSuite {

  test("xdv divides x by 2^combo, writes to X") {
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
  test("ydv divides X by 2^combo, writes to Y") {
    val regs = Registers(x = 20, y = 0, z = 0, ip = 0)
    val newRegs = Instructions.execute(6, 2, regs)
    // x,z should be unchanged
    assertEquals(newRegs.x, 20)
    assertEquals(newRegs.y, 5)
    assertEquals(newRegs.z, 0)
    assertEquals(newRegs.ip, 2)
  }
  test("ydv using reg y value") {
    val regs = Registers(x = 16, y = 2, z = 0, ip = 0)
    val newRegs = Instructions.execute(6, 5, regs)
    // denominator = 2^2 = 4
    assertEquals(newRegs.y, 4)
  }

  test("zdv divides x by 2^combo, writes to Z") {
    val regs = Registers(x = 32, y = 0, z = 0, ip = 0)

    val newRegs = Instructions.execute(7, 3, regs)

    // x,y should be unchanged
    assertEquals(newRegs.x, 32)
    assertEquals(newRegs.z, 4)
    assertEquals(newRegs.y, 0)
    assertEquals(newRegs.ip, 2)
  }

  test("zdv using reg x value") {
    val regs = Registers(x = 3, y = 0, z = 0, ip = 0)

    // 4 would use x value
    // thus 2^3 = 8
    val newRegs = Instructions.execute(7, 4, regs)
    // trunc 3 / 8
    assertEquals(newRegs.z, 0)
  }
}
