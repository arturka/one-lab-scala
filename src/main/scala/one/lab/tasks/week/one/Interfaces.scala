package one.lab.tasks.week.one

import one.lab.tasks.week.one.Interfaces.Platform.Platform
/**
  * 1. You have to define three [[one.lab.tasks.week.one.Interfaces.Console]] class implementation, let's say Xbox,
  *    PlayStation, Sega
  * 2. You also need to define implementation GameDisk traits for each of console, and some classes of games, see
  *    [[one.lab.tasks.week.one.Interfaces.XboxGameDisk]] and [[one.lab.tasks.week.one.Interfaces.ForzaHorizon]]
  * 3. When creating implementation of Console be sure to properly implement play method,
  *    so that when I try to play Xbox with PS game disk, it will print me that disk format is invalid.
  *    But when I supply appropriate disk it will print s"playing ${disk.game()}"
  */


object Interfaces {
  object Platform extends Enumeration {
    type Platform = Value
    val PlayStation = Value("PS")
    val Xbox = Value("XB")
    val Sega = Value("S")
  }

  trait GameDisk {
    val consoleType: Platform
    val game: String
  }
  trait Console {
    def play(disk: GameDisk): Unit
  }

  trait PlayStationGameDisk extends GameDisk {
    override val consoleType: Platform = Platform.PlayStation
  }
  trait XboxGameDisk extends GameDisk {
    override val consoleType: Platform = Platform.Xbox
  }
  trait SegaGameDisk extends GameDisk {
    override val consoleType: Platform = Platform.Sega
  }

  class PlayStationConsole extends Console {
    override def play(disk: GameDisk): Unit = {
      if(disk.consoleType == Platform.PlayStation) println(s"playing ${disk.game}")
      else println("ERROR 342: Invalid disk format...")
    }
  }
  class XboxConsole extends Console {
    override def play(disk: GameDisk): Unit = {
      if(disk.consoleType == Platform.Xbox) println(s"playing ${disk.game}")
      else println("ERROR 532: Invalid disk format...")
    }
  }
  class SegaConsole extends Console {
    override def play(disk: GameDisk): Unit = {
      if(disk.consoleType == Platform.Sega) println(s"playing ${disk.game}")
      else println("ERROR 234: Invalid disk format...")
    }
  }

  class CounterStrike extends PlayStationGameDisk {
    override val game: String = "CS:GO"
  }
  class NeedForSpeed extends XboxGameDisk {
    override val game: String = "NFS"
  }
  class Kosynka extends SegaGameDisk {
    override val game: String = "Kosynka"
  }

  def main(args: Array[String]): Unit = {
    val csgo = new CounterStrike()
    val nfs = new NeedForSpeed()
    val kos = new Kosynka()
    val playStationConsole = new PlayStationConsole()
    val xboxConsole = new XboxConsole()
    val segaConsole = new SegaConsole()
    println("PlayStation: ")
    playStationConsole.play(csgo)
    playStationConsole.play(nfs)
    playStationConsole.play(kos);
    println()
    println("Xbox: ")
    xboxConsole.play(csgo)
    xboxConsole.play(nfs)
    xboxConsole.play(kos)
    println()
    println("Sega: ")
    segaConsole.play(csgo)
    segaConsole.play(nfs)
    segaConsole.play(kos)
  }
}




