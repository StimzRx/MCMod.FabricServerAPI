package dev.venomcode.serverapi.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.UUID;

public final class ServerUtils {
    public static Block GetBlockByTag(Identifier identifier) {
        return Registries.BLOCK.get(identifier);
    }
    public static ItemStack getHeadFromRaw(String rawId )
    {
        ItemStack buffStack = new ItemStack( Items.PLAYER_HEAD );
        NbtCompound skullTag = buffStack.getOrCreateSubNbt( "SkullOwner" );

        NbtCompound propertiesTag = new NbtCompound();
        NbtList texturesTag = new NbtList();
        NbtCompound textureValue = new NbtCompound();

        textureValue.putString("Value", rawId);

        texturesTag.add(textureValue);
        propertiesTag.put("textures", texturesTag);
        skullTag.put("Properties", propertiesTag);
        skullTag.putUuid( "Id", UUID.randomUUID() );

        return buffStack;
    }
    public static void setStackModelData(ItemStack stack, int id)
    {
        NbtCompound stackTag = stack.getOrCreateNbt(  );

        stackTag.putInt("CustomModelData", id);
    }

    public static void setStackLore(ItemStack stack, List<MutableText> lores)
    {
        NbtCompound dispCompound = stack.getOrCreateSubNbt( "display" );

        NbtList loreListTag = new NbtList();

        for(Text t : lores)
        {
            loreListTag.add(NbtString.of(Text.Serializer.toJson(t)));
        }

        dispCompound.put( "Lore", loreListTag );
    }

    public static MutableText getText(String text, Formatting... formattings)
    {
        return (MutableText)Text.of(text).getWithStyle( Style.EMPTY.withItalic(false).withFormatting(formattings) );
    }
}
