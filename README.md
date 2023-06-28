# Fabric Server API
A server api for Fabric aiming to simplify server-side mods significantly.
With big goals of make server-sided fabric a much more stable and enjoyable
experience.

## Hotkey Menu
**(Currently bound to Shift+F)**

The hotkey menu currenly just allows users to teleport to spawn. In the future it will allow MUCH more including configuration screens for other server-side mods, alternative type of TPA, trading, dueling, and many others.

To use the current "teleport to spawn" feature, admins must first set spawn in the menu via the "ADMIN" button on the top-right of it. This will require either the player be an OP, or that they have the ``serverapi.admin`` permission.

More features for the **Hotkey Menu** system in the coming months(hopefully).

## Events
- PlayerEvents.JUMP
  - A cancelable jump event for all ServerPlayerEntity
- PlayerEvents.SNEAK
  - A cancelable sneak event for all ServerPlayerEntity
- PlayerEvents.BLOCK_BREAK
  - A cancelable event for when a player breaks a block

## ServerUtils
A utility class featuring a array of static methods for you to use!
- ``Block getBlockByTag(Identifier);``
- ``ItemStack getHeadFromRaw(String rawId);``
- ``void setStackModelData(ItemStack, int); // For resource packs``
- ``void setStackLore(ItemStack, List<MutableText>);``
- ``MutableText getText(String, Formatting...);``

## ServerAPI
The "main" API to access... or at least thats the goal for the future. Limited use for now!
- ``ServerPlayerEntity getPlayerByUUID(UUID, MinecraftServer);``
- ``ServerAPI.Logger``
  - Gives a string infused with color attributes for you to log colored text to the console. Abusable so may be disabled by default in future versions via a config option?
  - ``String Success(String); // green color``
  - ``String Warning(String); // yellow color``
  - ``String Error(String); // Red color``
  - ``String Debug (String); // cyan color``


### Future Goals - Further Out
- A "ServerPlayer" class that would abstract a lot of the more
  "complex" things about dealing with server-sided fabric mods that
  interact directly with the Player class.
- A lot more events.
- Easier configuration file management for YOUR server-side mods!
- Easily read/write from offline players.
- GUI configuration management for your server-side mods.

**Notice:**
*Bottles and all of its relations have been scrapped as a concept. Sorry for the hype!*