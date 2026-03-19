import machine.Machine
import machine.Registers
import machine.step3
import machine.fetch
import machine.execute

@main def main(): Unit =
  val prog = Vector(0, 1, 5, 4, 3, 0)
  var m = Machine(
    regs = Registers(x = 3729, y = 0, z = 0, out = Vector()),
    program = prog,
    opcode = 0,
    operand = 0,
    aluRes = 0,
    ip = 0
  )

  // NOTE: not using the runner because
  // I'd like to pretty print for debugging here
  while (m.ip < prog.length) {
    m = step3(m)
    PPrint.pretty(m)
  }
