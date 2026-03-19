import machine.ComboOperand
import java.io.Writer
import machine.Registers
import machine.Machine
import machine.Operand

extension (reg: Registers)
  def emit: String =
    val xString = "X: " + reg.x.toString() + "\n"
    val yString = "Y: " + reg.y.toString() + "\n"
    val outString = "out: " + reg.out.toString() + "\n"
    xString + yString + outString

extension (operand: ComboOperand)
  def emit: String = operand match
    case ComboOperand.Literal(value) =>
      "Operand: Literal " + value.toString() + "\n"
    case ComboOperand.X => "Operand: Reg X \n"
    case ComboOperand.Y => "Operand: Reg Y \n"
    case ComboOperand.Z => "Operand: Reg Z \n"

object PPrint:
  def pretty(m: Machine): Unit =
    val writer = new java.io.StringWriter()
    writer.write("====================\n")
    format(writer, m)
    writer.write("====================\n")
    println(writer.toString)

  def format(writer: Writer, m: Machine): Unit =
    val aluResString = "ALU: " + m.aluRes + "\n"
    val reg = emit(m.regs)
    val opcode = m.opcode match {
      case 0 => "Opcode: " + "XDV" + "\n"
      case 1 => "Opcode: " + "YXL" + "\n"
      case 2 => "Opcode: " + "YST" + "\n"
      case 3 => "Opcode: " + "JNZ" + "\n"
      case 4 => "Opcode: " + "YXZ" + "\n"
      case 5 => "Opcode: " + "OUT" + "\n"
      case 6 => "Opcode: " + "YDV" + "\n"
      case 7 => "Opcode: " + "ZDV" + "\n"
    }
    val operand = emit(Operand.combo(m.operand))
    val ip = "IP: " + m.ip + "\n"
    val phase = "Next Phase: " + m.phase.toString() + "\n"
    writer.write(aluResString + opcode + operand + reg + ip + phase)
