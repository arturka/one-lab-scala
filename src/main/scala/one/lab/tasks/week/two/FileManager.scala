package one.lab.tasks.week.two

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import scala.jdk.CollectionConverters._
import scala.util.chaining._

/**
  * Можете реализовать свою логику.
  * Главное чтобы работали команды и выводились ошибки при ошибочных действиях.
  * ll - показать все что есть в тек. папке
  * dir - показать только директории в тек. папке
  * ls - показать только файлы в тек. папке
  * cd some_folder - перейте из тек. папки в другую (учитывайте что путь можно сделать самым простым
  *                  то есть если я сейчас в папке /main и внутри main есть папка scala и я вызову
  *                  cd scala то мы должны просто перейти в папку scala. Реализация cd из текущей папки
  *                  по другому ПУТИ не требуется. Не забудьте только реализовать `cd ..`)
  *
  * Бонусные команды и идеи привествуются.
  */
object FileManager extends App {

  trait Command {
    def isSubstitutive: Boolean = false
  }

  case class PrintErrorCommand(error: String) extends Command
  case class ListDirectoryCommand()           extends Command
  case class ListFilesCommand()               extends Command
  case class ListAllContentCommand()          extends Command

  case class ChangeDirectoryCommand(destination: String) extends Command {
    override val isSubstitutive: Boolean = true
  }

  case class ChangePathError(error: String)

  val filterFiles: Path => Boolean       = path => path.toFile.isFile
  val filterDirectories: Path => Boolean = path => path.toFile.isDirectory

  def getContent(path: String, func: Path => Boolean): List[String] =
    Files
      .list(Paths.get("dsfasdfas"))
      .iterator()
      .asScala
      .filter(func) // Path => Boolean
      .map(path => path.toFile.getName)
      .map(x => s"$path/$x")
      .toList

  def changePath(current: String, path: String): Either[ChangePathError, String] =
    Files.isDirectory(Paths.get(current + "/" + path)) match {
      case true  => Right(current + "/" + path)
      case false => Left(ChangePathError("directory doesn't exists"))
    }

  private def parseCommand(input: String): Command =
    if (input == "ll") ListAllContentCommand()
    else if (input == "ls") ListFilesCommand()
    else if (input == "dir") ListDirectoryCommand()
    else if (input.startsWith("cd"))
      input
        .split(" ")
        .tail
        .headOption
        .map(dir => ChangeDirectoryCommand(dir))
        .getOrElse(PrintErrorCommand("invalid format"))
    else PrintErrorCommand("command not found")

  def handleCommand(command: Command, currentPath: String): String =
    command match {
      case ListAllContentCommand() =>
        (getContent(currentPath, filterFiles) ++ getContent(currentPath, filterDirectories)).mkString("\n")
      case ListFilesCommand()       => getContent(currentPath, filterFiles).mkString("\n")
      case ListDirectoryCommand()   => getContent(currentPath, filterDirectories).mkString("\n")
      case PrintErrorCommand(error) => error
      case ChangeDirectoryCommand(destination) =>
        changePath(currentPath, destination) match {
          case Left(value)  => value.error
          case Right(value) => value
        }
    }

  def printString(str: String) = print(str)

  def main(basePath: String): Unit = {
    def innerLoop(currentPath: String): Unit = {
      scala.io.StdIn
        .readLine()
        .pipe(input => parseCommand(input))
        .pipe(command => (command, handleCommand(command, currentPath)))
        .tap { case (_, output) => println(output) }
        .tap {
          case (command, output) =>
            if (command.isSubstitutive) innerLoop(output)
            else innerLoop(currentPath)
        }

      println("You are in directory ->")
      println(s"$basePath")
      innerLoop(basePath)
    }

    innerLoop(basePath)
  }

  main("/Users/artur/src/github.com/arturka")
}
