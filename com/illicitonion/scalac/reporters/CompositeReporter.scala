package com.illicitonion.scalac.reporters

import scala.reflect.internal.util.Position
import scala.tools.nsc.reporters.Reporter

class CompositeReporter(val reporters: Array[Reporter]) extends Reporter {
  override protected def info0(
      pos: Position,
      msg: String,
      severity: Severity,
      force: Boolean): Unit = {
    severity.id match {
      case 0 =>
        reporters.foreach { _.info(pos, msg, force) }
      case 1 =>
        reporters.foreach { _.warning(pos, msg) }
      case 2 =>
        reporters.foreach { _.error(pos, msg) }
      case other =>
        throw new IllegalArgumentException(s"CompositeReporter didn't recognise severity $other")
    }
  }
}
