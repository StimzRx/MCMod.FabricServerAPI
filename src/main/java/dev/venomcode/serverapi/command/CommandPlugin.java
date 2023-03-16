package dev.venomcode.serverapi.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.venomcode.serverapi.api.ServerAPI;
import dev.venomcode.serverapi.api.plugin.FabricPlugin;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.server.command.CommandManager.*;

public class CommandPlugin {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(literal("plugin")
                .requires( src -> src.hasPermissionLevel(4) || Permissions.require("serverapi.commands.plugin", false).test(src) )
                .executes( ctx -> {
                    ctx.getSource().sendFeedback(Text.of("Usage: /plugin enable <name> | /plugin disable <name>"), false);
                    return Command.SINGLE_SUCCESS;
                })
                .then(literal("list")
                        .executes(CommandPlugin::executeListPlugins))
                .then(literal("enable")
                        .then(argument("plugin name",greedyString())
                                .executes( ctx -> executeEnablePluginByName(ctx, StringArgumentType.getString(ctx, "plugin name")))))
                .then(literal("disable")
                        .then(argument("plugin name", greedyString())
                                .executes(ctx -> executeDisablePluginByName(ctx, StringArgumentType.getString(ctx,"plugin name"))))));
    }

    private static int executeListPlugins(CommandContext<ServerCommandSource> ctx) {
        List<String> pluginNames = ServerAPI.getPluginNames();

        MutableText replyText = (MutableText)Text.of("\n--=[Plugins]=--");
        if(pluginNames.size() == 0)
        {
            replyText.append("\nNo plugins registered.");
        }
        else
        {
            for(String name : pluginNames)
            {
                replyText.append("\n["+name+"]");
            }
        }

        ctx.getSource().sendFeedback(replyText, false);

        return Command.SINGLE_SUCCESS;
    }

    private static int executeEnablePluginByName(CommandContext<ServerCommandSource> ctx, String pluginName) {
        FabricPlugin plugin = ServerAPI.getPlugin(pluginName);
        if(plugin == null)
        {
            ctx.getSource().sendFeedback(Text.of("Unable to find plugin '" + pluginName + "'."), false);
        }

        plugin.onEnable();

        return Command.SINGLE_SUCCESS;
    }
    private static int executeDisablePluginByName(CommandContext<ServerCommandSource> ctx, String pluginName) {
        FabricPlugin plugin = ServerAPI.getPlugin(pluginName);
        if(plugin == null)
        {
            ctx.getSource().sendFeedback(Text.of("Unable to find plugin '" + pluginName + "'."), false);
        }

        plugin.onDisable();

        return Command.SINGLE_SUCCESS;
    }

}
