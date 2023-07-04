package dev.venomcode.serverapi.commands.server;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.venomcode.serverapi.api.ServerUtils;
import me.lucko.fabric.api.permissions.v0.PermissionCheckEvent;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ServerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        LiteralArgumentBuilder<ServerCommandSource> builder = literal("server")
                .requires( src -> (Permissions.check(src, "serverapi.commands.server")) || src.hasPermissionLevel(2))
                .then(literal("setmodeldata")
                .then(argument("id", IntegerArgumentType.integer(5,99999))
                .executes(ctx -> executeSetModelData(ctx, IntegerArgumentType.getInteger(ctx, "id")))));

        dispatcher.register(builder);
    }
    public static int executeSetModelData(CommandContext<ServerCommandSource> ctx, int id) throws CommandSyntaxException
    {
        if(!ctx.getSource().isExecutedByPlayer())
            return Command.SINGLE_SUCCESS;

        ServerPlayerEntity srvPlr = ctx.getSource().getPlayer();

        ItemStack handStack = srvPlr.getInventory().getMainHandStack();
        if(handStack != ItemStack.EMPTY)
        {
            ServerUtils.setStackModelData(handStack, id);
            ctx.getSource().sendFeedback(ServerUtils.getText("Set CustomModelData to " + id, Formatting.GREEN), false);
        }

        return Command.SINGLE_SUCCESS;
    }
}
