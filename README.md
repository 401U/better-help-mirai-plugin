# better-help-mirai-plugin

> 为 Mirai 提供更好的帮助信息

配置文件： 位于`cc.redme.mirai.plugin.better-help`

```yaml
# /help 的回复格式
# {size} 将被替换为 可用指令数量
# {help} 将被替换为 可用的指令列表 + 描述
helpPattern: "共{size}个可用指令\n{help}"

# /help <指令名> 的回复格式
# {name}       将被替换为 指令名
# {alias}      将被替换为 指令的可用别名
# {permission} 将被替换为 指令权限ID
# {usage}      将被替换为 指令用法列表
helpDetailPattern: "指令名: {name}\n别名: {alias}\n权限ID: {permission}\n用法: {usage}"

# 在可用指令列表中隐藏的指令
hiddenCommand:
  - test1
  - test2

# 没有可用指令时的回复
emptyCommandReply: 好像没有什么能帮到你的...
```