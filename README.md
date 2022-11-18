# Fabric Server API
A server api for fabric aiming to simplify server-side mods significantly.

## Events
 - PlayerEvents.JUMP
   - A cancelable jump event for all ServerPlayerEntity
 - PlayerEvents.SNEAK
   - A cancelable sneak event for all ServerPlayerEntity
 - PlayerEvents.BLOCK_BREAK
   - A cancelable event for when a player breaks a block

### Future Goals
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
