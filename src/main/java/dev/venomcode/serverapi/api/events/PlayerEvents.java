package dev.venomcode.serverapi.api.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

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
}
