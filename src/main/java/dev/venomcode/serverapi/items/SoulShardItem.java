package dev.venomcode.serverapi.items;

import dev.venomcode.serverapi.ServerAPIMod;
import dev.venomcode.serverapi.api.ServerAPI;
import dev.venomcode.serverapi.api.ServerUtils;
import eu.pb4.polymer.core.api.item.PolymerItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SoulShardItem  extends Item implements PolymerItem {

    public SoulShardItem(Settings settings) {
        super(settings);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return Items.SOUL_LANTERN;
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipContext context, @Nullable ServerPlayerEntity player) {

        if(isNbtDataValid(itemStack))
        {
            List<MutableText> lores = new ArrayList<>();

            lores.add(ServerUtils.getText("[Owner]"));
            lores.add(ServerUtils.getText(getOwnerPlayerName(itemStack)));
            lores.add(ServerUtils.getText(""));
            lores.add(ServerUtils.getText("[Bound To]"));
            lores.add(ServerUtils.getText(getBoundPlayerName(itemStack)));

            ServerUtils.setStackLore(itemStack, lores);
        }

        itemStack.setCustomName(ServerUtils.getText("Soul Shard", Formatting.LIGHT_PURPLE, Formatting.BOLD));

        return itemStack;
    }

    public void setOwnerPlayer(ItemStack itemStack, ServerPlayerEntity player)
    {
        NbtCompound dataTag = itemStack.getOrCreateSubNbt(ServerAPIMod.MODID);
        dataTag.putUuid(TAG_OWNER_PLAYER_UUID, player.getUuid());
        dataTag.putString(TAG_OWNER_PLAYER_NAME, player.getName().toString());

        itemStack.setSubNbt(ServerAPIMod.MODID, dataTag);
    }

    public ServerPlayerEntity getOwnerPlayer(ItemStack itemStack, MinecraftServer server) {
        if(!isNbtDataValid(itemStack))
            return null;

        NbtCompound dataTag = itemStack.getOrCreateSubNbt(ServerAPIMod.MODID);

        if(!dataTag.containsUuid(TAG_OWNER_PLAYER_UUID))
            return null;

        final UUID playerUUID = dataTag.getUuid(TAG_OWNER_PLAYER_UUID);

        return ServerAPI.getPlayerByUUID(playerUUID, server);
    }
    public String getOwnerPlayerName(ItemStack itemStack)
    {
        if(!isNbtDataValid(itemStack))
            return "!#_NULL_#!";

        NbtCompound dataTag = itemStack.getOrCreateSubNbt(ServerAPIMod.MODID);

        return dataTag.getString(TAG_OWNER_PLAYER_NAME);
    }

    public void setBoundPlayer(ItemStack itemStack, ServerPlayerEntity player) {
        NbtCompound dataTag = itemStack.getOrCreateSubNbt(ServerAPIMod.MODID);
        dataTag.putUuid(TAG_BOUND_PLAYER_UUID, player.getUuid());
        dataTag.putString(TAG_BOUND_PLAYER_NAME, player.getName().toString());

        itemStack.setSubNbt(ServerAPIMod.MODID, dataTag);
    }
    public ServerPlayerEntity getBoundPlayer(ItemStack itemStack, MinecraftServer server) {
        if(!isNbtDataValid(itemStack))
            return null;

        NbtCompound dataTag = itemStack.getOrCreateSubNbt(ServerAPIMod.MODID);

        if(!dataTag.containsUuid(TAG_BOUND_PLAYER_UUID))
            return null;

        final UUID playerUUID = dataTag.getUuid(TAG_BOUND_PLAYER_UUID);

        return ServerAPI.getPlayerByUUID(playerUUID, server);
    }
    public String getBoundPlayerName(ItemStack itemStack)
    {
        if(!isNbtDataValid(itemStack))
            return "!#_NULL_#!";

        NbtCompound dataTag = itemStack.getOrCreateSubNbt(ServerAPIMod.MODID);

        return dataTag.getString(TAG_BOUND_PLAYER_NAME);
    }


    public boolean isNbtDataValid(ItemStack itemStack) {
        return itemStack.hasNbt() && !itemStack.getOrCreateSubNbt(ServerAPIMod.MODID).isEmpty();
    }


    public static final String TAG_OWNER_PLAYER_UUID = "owner_player_uuid";
    public static final String TAG_OWNER_PLAYER_NAME = "owner_player_name";
    public static final String TAG_BOUND_PLAYER_UUID = "bound_player_uuid";
    public static final String TAG_BOUND_PLAYER_NAME = "bound_player_name";
}
