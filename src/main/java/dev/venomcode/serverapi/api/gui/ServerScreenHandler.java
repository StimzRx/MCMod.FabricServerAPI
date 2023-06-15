package dev.venomcode.serverapi.api.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;

public abstract class ServerScreenHandler extends ScreenHandler {
    protected ServerScreenHandler(int syncId, PlayerInventory playerInventory, int rows) {
        super(fromRows(rows), syncId);

        int i = (rows - 4) * 18;

        this.inventory = new SimpleInventory(rows * 9);

        MutableText name = Text.of("").copy();
        name.setStyle(Style.EMPTY.withHoverEvent(HoverEvent.Action.SHOW_TEXT.buildHoverEvent(Text.of(""))));
        fillerItemStack = new ItemStack(Items.BLACK_STAINED_GLASS_PANE).setCustomName(name);

        ServerPlayerEntity serverPlayer = (ServerPlayerEntity)playerInventory.player;
        for(int b=0; b < inventory.size(); b++)
        {
            this.onInitInventoryFill(b, serverPlayer, this.inventory);
        }

        int n, m;

        for(n = 0; n < rows; ++n)
        {
            for(m = 0; m<9;++m)
            {
                this.addSlot(new Slot(this.inventory, m + n * 9, 8 + m * 18, 18 + n * 18));
            }
        }
        for(n=0; n<3; ++n)
        {
            for(m=0; m < 9; ++m)
            {
                this.addSlot(new Slot(playerInventory, m + n *9 + 9, 8 + m * 18, 103 + n * 18 + i));
            }
        }

        for (n = 0; n < 9; ++n) {
            this.addSlot(new Slot(playerInventory, n, 8 + n * 18, 161 + i));
        }

    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    private void onInitInventoryFill(int slotIndex, ServerPlayerEntity serverPlayer, Inventory inv)
    {
        ItemStack stack = handleInitialSlotStacks(slotIndex, serverPlayer, getFillerItem());
        inv.setStack(slotIndex, stack);
    }

    protected ItemStack handleInitialSlotStacks(int slotIndex, ServerPlayerEntity serverPlayer, ItemStack stack)
    {
        return stack;
    }

    public ItemStack getFillerItem() {
        return fillerItemStack.copy();
    }

    public int getInventorySize()
    {
        return inventory.size();
    }
    protected Inventory getInventory()
    {
        return inventory;
    }

    public SAPI_SLOT_CLICK handleClickedSlot(int slotIndex, ClickType clickType, SlotActionType actionType, ServerPlayerEntity serverPlayer) {
        return SAPI_SLOT_CLICK.DENY_CLICK;
    }

    private static ScreenHandlerType<GenericContainerScreenHandler> fromRows(int rows) {
        return switch (rows) {
            case 2 -> ScreenHandlerType.GENERIC_9X2;
            case 3 -> ScreenHandlerType.GENERIC_9X3;
            case 4 -> ScreenHandlerType.GENERIC_9X4;
            case 5 -> ScreenHandlerType.GENERIC_9X5;
            case 6 -> ScreenHandlerType.GENERIC_9X6;
            default -> ScreenHandlerType.GENERIC_9X1;
        };
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private final Inventory inventory;
    private ItemStack fillerItemStack;
}
