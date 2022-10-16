package cc.redme.mirai.plugin.betterhelp
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.findDuplicate
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "cc.redme.mirai.plugin.better-help",
        name = "更好的帮助指令",
        version = "1.0.0",
    ) {
        author("YehowahLiu")
    }
) {
    override fun onEnable() {
        PluginConfig.reload()
        HelpCommand.findDuplicate()?.unregister()
        HelpCommand.register()
        logger.info { "better-help-mirai-plugin 已加载, 将提供更好的帮助菜单" }
    }

    override fun onDisable() {
        PluginConfig.save()
    }
}
