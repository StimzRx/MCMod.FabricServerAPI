package dev.venomcode.serverapi.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public final class PlayerEvents {
    /**
     * An event that is called when a player jumps.
     */
    public static final Event<Jump> JUMP = EventFactory.createArrayBacked(Jump.class, callbacks -> (player) -> {
        for (Jump callback : callbacks) {
            if (!callback.allowJump(player)) {
                return false;
            }
        }

        return true;
    });
    /**
     * An event that is called when a player sneaks.
     */
    public static final Event<Sneak> SNEAK = EventFactory.createArrayBacked(Sneak.class, callbacks -> (player) -> {
        for (Sneak callback : callbacks) {
            if (!callback.allowSneak(player)) {
                return false;
            }
        }

        return true;
    });

    /**
     * An event that is a called when a player is about to break a block
     */
    public static final Event<BreakBlock> BREAK_BLOCK = EventFactory.createArrayBacked(BreakBlock.class, callbacks -> (player, world, block) -> {
        for(BreakBlock c : callbacks)
        {
            if(!c.allowBreakBlock(player,world,block))
            {
                return false;
            }
        }
        return true;
    });

    @FunctionalInterface
    public interface Jump {
        /**
         * Called when a player jumps.
         *
         * @param player the player
         * @return true if the jump should be allowed, false otherwise.
         */
        boolean allowJump(ServerPlayerEntity player);
    }
    @FunctionalInterface
    public interface Sneak {
        /**
         * Called when a player sneaks.
         *
         * @param player the player
         * @return true if the sneak should be allowed, false otherwise.
         */
        boolean allowSneak(ServerPlayerEntity player);
    }
    @FunctionalInterface
    public interface BreakBlock {
        /**
         * Called when a player tries to break a block.
         *
         * @param player the ServerPlayerEntity involved
         * @param world the ServerWorld
         * @param block the Block
         * @return true if the block break should be allowed, false otherwise.
         */
        boolean allowBreakBlock(ServerPlayerEntity player, ServerWorld world, Block block);
    }
}
