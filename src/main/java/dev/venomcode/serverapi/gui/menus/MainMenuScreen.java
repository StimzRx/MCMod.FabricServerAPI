package dev.venomcode.serverapi.gui.menus;

import dev.venomcode.serverapi.api.ServerUtils;
import dev.venomcode.serverapi.api.gui.SAPI_SLOT_CLICK;
import dev.venomcode.serverapi.api.gui.ServerScreenHandler;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

public class MainMenuScreen extends ServerScreenHandler {

    public MainMenuScreen(int syncId, PlayerInventory playerInventory) {
        super(syncId, playerInventory, 3);
    }

    @Override
    protected ItemStack handleInitialSlotStacks(int slotIndex, ServerPlayerEntity serverPlayer, ItemStack stack) {

        if(slotIndex == 7)
        {
            // Admin Menu
            if(serverPlayer.hasPermissionLevel(2) || Permissions.check(serverPlayer, "serverapi.admin"))
            {
                stack = new ItemStack(Items.REDSTONE_BLOCK).setCustomName(ServerUtils.getText("ADMIN", Formatting.RED));
            }
        }
        else if(slotIndex == 8)
        {
            // Close Screen
            stack = new ItemStack(Items.DEEPSLATE_REDSTONE_ORE).setCustomName(ServerUtils.getText("Close", Formatting.RED));
        }
        else if(slotIndex == 10)
        {
            // Teleports
            stack = new ItemStack(Items.ENDER_EYE).setCustomName(ServerUtils.getText("Teleports", Formatting.LIGHT_PURPLE));
        }

        return stack;
    }

    @Override
    public SAPI_SLOT_CLICK handleClickedSlot(int slotIndex, ClickType clickType, SlotActionType actionType, ServerPlayerEntity serverPlayer) {

        if(slotIndex == 7)
        {
            // Admin Menu
            if(serverPlayer.hasPermissionLevel(2) || Permissions.check(serverPlayer, "serverapi.admin")) {
                serverPlayer.openHandledScreen(new NamedScreenHandlerFactory() {
                    @Override
                    public Text getDisplayName() {
                        return ServerUtils.getText("ADMIN", Formatting.RED);
                    }

                    @Nullable
                    @Override
                    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                        return new AdminMenuScreen(syncId, playerInventory);
                    }
                });
            }
        }
        else if(slotIndex == 8)
        {
            // Close
            serverPlayer.closeHandledScreen();
        }
        else if(slotIndex == 10)
        {
            // Teleporting
            serverPlayer.openHandledScreen(new NamedScreenHandlerFactory() {
                @Override
                public Text getDisplayName() {
                    return ServerUtils.getText("Teleporting", Formatting.YELLOW);
                }

                @Nullable
                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return new TeleportsMenuScreen(syncId, playerInventory);
                }
            });
        }

        return SAPI_SLOT_CLICK.DENY_CLICK;
    }
}
