package machine
class InstructionTestSuite extends munit.FunSuite {

  test("xdv divides x by 2^combo, writes to X") {
    val regs = Registers(x = 16, y = 0, z = 0)
    // div 2^2 as given in the example
    val res = Instructions.XDV(2, 0, regs)
    assertEquals(res, 4)
  }

  test("xdv truncates") {
    // 10 / 4 = 2.5 trunc to 2
    val regs = Registers(x = 10, y = 0, z = 0)
    val res = Instructions.XDV(2, 0, regs)
    assertEquals(res, 2)
  }

  test("yxl with literal operand") {
    val regs = Registers(x = 0, y = 5, z = 0)
    val res = Instructions.YXL(3, 0, regs)
    // 5 ^ 3 = 6
    assertEquals(res, 6)
  }
  test("yst with combo operand") {
    val regs = Registers(x = 0, y = 0, z = 9)
    val res = Instructions.YST(6, 0, regs)
    assertEquals(res, 1)
  }
  test("jnz literal jump when x!=0") {
    val regs = Registers(x = 5, y = 0, z = 0)
    val res = Instructions.JNZ(6, 0, regs)
    assertEquals(res, 6)
  }
  test("jnz continue on x=0") {
    val regs = Registers(x = 0, y = 0, z = 0)
    val res = Instructions.JNZ(6, 0, regs)
    // NOTE: since we dont have IF stage this should stay the same
    assertEquals(res, 0)
  }
  test("yxz xors y and z, write to y") {
    val regs = Registers(x = 0, y = 10, z = 5)
    val res = Instructions.YXZ(123, 0, regs)
    assertEquals(res, 10 ^ 5)
  }
  test("out outputs combo % 8") {
    val regs = Registers(x = 0, y = 0, z = 0)
    val res = Instructions.OUT(3, 0, regs)
    assertEquals(res, 3)
  }
  test("out outputs reg % 8") {
    val regs = Registers(x = 0, y = 0, z = 9)
    val res = Instructions.OUT(6, 0, regs)
    // 9 % 8
    assertEquals(res, 1)
  }
  test("ydv divides X by 2^combo, writes to Y") {
    val regs = Registers(x = 20, y = 0, z = 0)
    val res = Instructions.YDV(2, 0, regs)
    // x,z should be unchanged
    assertEquals(res, 5)
  }
  test("ydv using reg y value") {
    val regs = Registers(x = 16, y = 2, z = 0)
    val res = Instructions.YDV(5, 0, regs)
    // denominator = 2^2 = 4
    assertEquals(res, 4)
  }

  test("zdv divides x by 2^combo, writes to Z") {
    val regs = Registers(x = 32, y = 0, z = 0)

    val res = Instructions.ZDV(3, 0, regs)

    assertEquals(res, 4)
  }

  test("zdv using reg x value") {
    val regs = Registers(x = 3, y = 0, z = 0)

    // 4 would use x value
    // thus 2^3 = 8
    val res = Instructions.ZDV(4, 0, regs)
    // trunc 3 / 8
    assertEquals(res, 0)
  }
}
