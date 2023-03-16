# Fabric Server API
A server api for Fabric aiming to simplify server-side mods significantly.
With big goals of make server-sided fabric a much more stable and enjoyable
experience.

## Bottles
#### (The Main Attraction)
Bottles isn't ready for prime-time yet.
However, the goal is to have a very nice and **STABLE** server ecosystem
from within Fabric.
I believe that this "bottles" system is going to be key in achieving that goal.

More info on the **Bottles** system in the coming months.

## Events
 - PlayerEvents.JUMP
   - A cancelable jump event for all ServerPlayerEntity
 - PlayerEvents.SNEAK
   - A cancelable sneak event for all ServerPlayerEntity
 - PlayerEvents.BLOCK_BREAK
   - A cancelable event for when a player breaks a block



### Future Goals - Further Out
 - A "ServerPlayer" class that would abstract a lot of the more
"complex" things about dealing with server-sided fabric mods that
interact directly with the Player class.
 - Tons more events
 - Easier configuration file management.
 - Easily read/write from offline players.
 - A "Utility" api that would offer many functions for dealing with server-sided
fabric mods. A less useful example off the top of my head would be
 `ServerUtils.TeleportAll(<position>)` which
would, of course, teleport all players to a given position.

## License
This project is available under the MIT license.
