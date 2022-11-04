package cc.redme.mirai.plugin.betterhelp

import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.allRegisteredCommands
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.commandPrefix
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.matchCommand
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.ConsoleCommandOwner
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.permission.PermissionService.Companion.hasPermission

object HelpCommand : SimpleCommand(
    ConsoleCommandOwner, "help", "?", "帮助", "？", "h",
    description = "查看所有可用指令，/help 指令名 获取指令详情"
) {
    override val usage: String =
        "/help    # 查看所有可用指令\n/help <指令名>    # 获取该指令详情\n/help --all    # 获取所有指令详情"
    private fun CommandSender.getCommands(): List<Command> {
        return allRegisteredCommands.asSequence()
            .filter { permitteeId.hasPermission(it.permission) }
            .filter { !PluginConfig.hiddenCommand.contains(it.primaryName) }.toList()
    }

    @Handler
    suspend fun CommandSender.handle() {
        val commands = getCommands()
        val helpMain = commands
            .joinToString("\n") { command ->
                "$commandPrefix${command.primaryName} ${command.description}"
            }
        if (commands.isEmpty()) {
            sendMessage(PluginConfig.emptyCommandReply)
        } else {
            sendMessage(
                PluginConfig.helpPattern
                    .replace("{help}", helpMain)
                    .replace("{size}", commands.size.toString())
            )
        }
    }

    @Handler
    suspend fun CommandSender.handleString(commandName: String) {
        if (!commandName.startsWith(commandPrefix) && commandName != "--all")
            return handleString(commandPrefix + commandName)

        sendMessage(when (commandName) {
            // https://github.com/mamoe/mirai/blob/dev/mirai-console/backend/mirai-console/src/command/BuiltInCommands.kt#L103
            "--all" -> {
                val commands = getCommands()
                if (commands.isEmpty()) {
                    PluginConfig.emptyCommandReply
                } else {
                    commands.joinToString("\n\n") { command ->
                        val lines = command.usage.lines()
                        if (lines.isEmpty()) "/${command.primaryName} ${command.description}"
                        else
                            "◆ " + lines.first() + "\n" + lines.drop(1).joinToString("\n") { "  $it" }
                    }.lines().filterNot(String::isBlank).joinToString("\n")
                }
            }

            else -> {
                val command = matchCommand(commandName)
                if (command == null || !hasPermission(command.permission))
                    "此指令不存在或权限不足"
                else {
                    val alias =
                        if (command.secondaryNames.isNotEmpty()) command.secondaryNames.joinToString(", ") else "无"
                    command.let {
                        PluginConfig.helpDetailPattern
                            .replace("{name}", it.primaryName)
                            .replace("{alias}", alias)
                            .replace("{permission}", it.permission.id.toString())
                            .replace("{usage}", it.usage)
                    }
                }

            }
        })
    }
}