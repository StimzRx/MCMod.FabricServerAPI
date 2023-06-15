package dev.venomcode.serverapi.gui.menus;

import dev.venomcode.serverapi.api.ServerUtils;
import dev.venomcode.serverapi.api.gui.SAPI_SLOT_CLICK;
import dev.venomcode.serverapi.api.gui.ServerScreenHandler;
import dev.venomcode.serverapi.data.SAPIData;
import dev.venomcode.serverapi.ifaces.player.IPlayerTeleporter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

public class TeleportsMenuScreen extends ServerScreenHandler {
    protected TeleportsMenuScreen(int syncId, PlayerInventory playerInventory) {
        super(syncId, playerInventory, 3);
    }

    @Override
    protected ItemStack handleInitialSlotStacks(int slotIndex, ServerPlayerEntity serverPlayer, ItemStack stack) {

        if(slotIndex == 8)
        {
            stack = new ItemStack(Items.DEEPSLATE_REDSTONE_ORE).setCustomName(ServerUtils.getText("Back", Formatting.RED));
        }
        else if(slotIndex == 10)
        {
            stack = new ItemStack(Items.BEACON).setCustomName(ServerUtils.getText("Teleport to Spawn", Formatting.LIGHT_PURPLE));
        }
        return stack;
    }

    @Override
    public SAPI_SLOT_CLICK handleClickedSlot(int slotIndex, ClickType clickType, SlotActionType actionType, ServerPlayerEntity serverPlayer) {
        if(slotIndex == 8)
        {
            // Close(Or in this case go back one menu)
            serverPlayer.openHandledScreen(new NamedScreenHandlerFactory() {
                @Override
                public Text getDisplayName() {
                    return ServerUtils.getText("Main Menu", Formatting.YELLOW);
                }

                @Nullable
                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return new MainMenuScreen(syncId, playerInventory);
                }
            });
        }
        else if(slotIndex == 10)
        {
            // Teleport to spawn

            serverPlayer.closeHandledScreen();

            SAPIData data = SAPIData.getState(serverPlayer.server);
            if(data.isSpawnSet())
            {
                IPlayerTeleporter playerTeleporter = (IPlayerTeleporter) serverPlayer;

                ServerWorld sWorld = serverPlayer.server.getWorld(RegistryKey.of(RegistryKeys.WORLD, data.getSpawnDimension()));

                playerTeleporter.setTeleportTarget(data.getSpawnPos(), 10, sWorld);
            }
            else
            {
                serverPlayer.sendMessage(ServerUtils.getText("Spawn not set!", Formatting.RED, Formatting.ITALIC), true);
            }
        }

        return SAPI_SLOT_CLICK.DENY_CLICK;
    }
}
