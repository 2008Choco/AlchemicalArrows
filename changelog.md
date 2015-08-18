<center><img src="http://i.imgur.com/H1NYMTj.png", width=400, height=200></center>

# <center> Changelog </center> #
- - - - - - - - - -

This is where you will find the official changelog of all releases of *AlchemicalArrows &copy; *. This will be updated periodically in order to keep track of all changes related to this document. This is a large database of all changelogs that have ever been published to the official BukkitDev page.

The official BukkitDev page can be found by [clicking here](http://dev.bukkit.org/bukkit-plugins/alchemical-arrows)

## Official Releases ##
- - - - - - - - - -

### Release 1.0.2 ###
* Allowed the recipe handler to extend the Methods class
* Removed the methods package from the API
* Moved the Messages and Arrows class into one Methods class (Decreases file size)
* Removed all methods to create arrows, and condensed it into one less complicated method, "createSpecializedArrow(int count, String displayName)"
* Removed unnecessary imports from many of the API classes
* Added private methods in the Recipe handler class to create recipes in a much more efficient way (cleaner & faster code)
* Added a softdepend for WorldGuard in the plugin.yml
* Added an onDisable method to kill all arrows, and clear all ArrayLists in order to prevent memory leaks
* Added a changelog.md file to log all the changelogs
* General internal modifications and fixes for faster code processing

### Release 1.0.1 ###
* Added two strings into the constructor of the ArrowType class. Meta and DisplayTag
* Added a private internal method to handle the picking up of arrows (still needs more work, but should clean up code)
* Changed the name of Spectral Arrow to Ender Arrow (not to confuse it with the new 1.9 Spectral Arrow)

### Release 1.0.0 ###
* Officially released the first full version, out of Beta, version 1.0.0! Many official changes will be hapenning.
* Removed the OP join message for the BETA version
* Allowed the /givearrow command to be run from the console (Must use all parameters to give to another player)

## BETA Releases ##
- - - - - - - - - -

### BETA 0.9b ###
* Modified the ArrowType.setArrowType() method to set its display name, item meta, and add it to the array list
* Changed the way the /givearrows command handles arrow type listing, and fixed the magnetic arrow not displaying on the error msg
* Added an alias to "/alchemicalarrows version"... "/alchemicalarrows info" now works as well
* Refactored the trajectory of water arrows to shoot faster underwater
* Changed the SpecializedArrowShootEvent method "getPlayer()", to "getShooter()" which returns a ProjectileSource instead of a Player
* Changed the way arrow pickups are handled. (Though still lots of work to do on this)

### BETA 0.9a ###
* Fixed a spelling mistake on the /givearrow error message (I KNOW!!! "DARKNESS", NOT "DARKENSS", SORRY ;-;)
* Fixed "crafts" option in confusion arrow using the necrotic arrow "crafts" option
* Changed the way arrows are handled in WorldGuard regions
* |--> Fixed being able to shoot an entity while outside of a pvp denied region if they are in a pvp denied region
* |--> Changed the messages sent to the players when damaging an entity protected by pvp (A bit more discrete about having WorldGuard on your server)
* Modified the "/aa version" sub-command to display the API version of the plugin, and removed the "Other Popular Plugins" tab in place
* Added a new Magnetic Arrow! Crafting recipe: Arrow + Iron Ingot. "Attracts" entities towards you. Much like the magic arrow but in reverse (Permissions: arrows.shoot.magnetic)
* Improved the API and added a new Enum class for "ArrowType" to get and set a type of arrow
* Changed the way arrows are handled! They are now handled through ArrowTypes

### BETA 0.8b ###
* OFFICIAL fix for WorldGuard related PvP Flag issues!!! This has been tested and it 100% works!!!
* Further developed the internal API to simplify most of the code involving sending messages
* Created a basic External API for developers that would like to build hooks into this plugin
* |--> Please note that the events in the external API are used to combat the WorldGuard issue. Be cautious using these events
* Removed the unused death messages for specialized arrows
* Nullified the effects of a flame bow when using a fire arrow. Could cause issues with fire ticks and does not look good with particles
* Precised and further densified the Air and Magic Arrow's particles effects (They're not all over the place :P)
* Fixed the /givearrow command being able to give stacks of 0. Now can only give a minimum of 1 arrow
* Fixed "/givearrow life" giving a death arrow instead
* Fixed the permission for /givearrow not working properly
* Added an error message if an invalid arrow is specified in the /givearrow command
* Started creative development on a new arrow - Expect a new elemental arrow soon

### BETA 0.8a ###
* Added a new command with 3 sub commands: /alchemicalarrows (alias: /aa)
* |--> /aa version - View version information about the plugin. No permission node
* |--> /aa reload - Reload the configuration file for AlchemicalArrows. Permission: arrows.command.reload
* |--> /aa killallarrows - Kill every single specialized arrow in the world (admin purposes). Permission: arrows.command.killallarrows
* Added a new command for administrators to give specialized arrows: /givearrow <arrow> <count> <player>. Permission: arrows.command.givearrow
* All above sub-commands with permissions default to OP only
* Removed the "AlchemicalArrows" section of the config (accidentally left it in last version)
* Added a config option for DeathArrow to disable the possibility of instant death (default: true)
* Added a VERY basic internal API for creating elemental arrows (not meant for external development purposes, but can be used if developers really want to. No documentation will be provided)
* Added a supplimentary recipe for darkness arrows. You can now use both coal or charcoal
* Fixed some stability issues with the newly developed water arrow

### BETA 0.7 ###
* Removed the debug messages on the necrotic arrow I accidentally left in (xD oops)
* Removed creeper explosions when hit with a fire arrow. That would require version dependent code (trying to avoid that)
* Changed the arrow damage event priority to lowest rather than low (SHOULD fix WorldGuard issues)
* Added a fix to prevent arrow effects from occurring in Non-PvP worlds
* Fixed life arrows having a chance of dealing damage?
* Fixed the extra pickup sound when picking up a regular item
* Changed Infinity bows decreasing the arrow count in creative mode
* Added support for version 1.8.8. Should work for all 1.8.x versions now
* Un-nullified water arrows. They will now fire 3x as fast underwater. Though be careful, as they will quickly decrease in speed

### BETA 0.6b ###
* Added a new and final Elemental Arrow (for now ;D)
* |--> Necrotic Arrow: If the arrow hits a player, all entities within a 50x50 area will be targeted towards them
* Fixed the permission nodes for the two new Elemental Arrows ("arrows.shoot.confusion" and "arrows.shoot.necrotic")
* Set ProjectileDamage events to LOW to allow for ALL PvP / grief protection plugins to prevent my arrows from damaging other players
* Changed arrows so they will ONLY work on LivingEntities
* Temporarily nullified the Water Arrow for future plans (it has absolutely 0 use)
* Fixed Air, Earth, and Magic arrows not applying modified vectors properly

### BETA 0.6a ###
* Fixed fire arrows not displaying flame
* Fixed picking up a fire arrow returning a red arrow
* Added particles to the darkness arrow. When a player is hit, smoke particles appear as well
* Added a new Elemental Arrow. Confusion Arrow.
* |--> Damaging an entity with this arrow will reverse the entity's direction. If it's a player, they will receive confusion

### BETA 0.5c ###
* Changed the link for reporting bugs upon an op join. Forgot to send you to the tickets page instead :P
* Fixed errors when a player dies to anything but an arrow

### BETA 0.5b ###
* Fixed the death messages not displaying when a player dies
* Fixed accidentally making version 0.5 only function in version 1.8.3
* Fixed the infinity arrows and cleaned up the code a lot
* Cleaned up the infinity arrow handling and shortened the class by 100 lines of code

### BETA 0.5a ###
* COMPLETELY redesigned and recoded the infrastructure of Alchemical Arrows. It will no longer rely on ArrayLists. This will save your server some memory, and arrows will react more efficiently
* Simplified the ability to add new arrows in the future. This process should be faster for me to update due to the new method of arrow handling
* Significantly decreased the file size which also allows for a faster process to start the plugin
* Changed teleportation of spectral arrows. Will keep player direction when teleporting the player
* Fixed picking up arrows returning to regular arrows. They will now keep their metadata (Temporary mimic, will re-code later)
* Added a new general config option to determine whether arrow nametags should display or not

### BETA 0.4 ###
* Fixed earth arrows changing the players direction when teleporting them into the ground
* Fixed many of the arrows interacting with non-living entities (such as armour-stands / item frames)
* Fixed the "0 knockback" bug on all of the arrows that did not involve vectors
* Fixed entities not targetting the player that shot them with a magical arrow
* Fixed arrows being treated as null objects rather than arrows
* Changed direct damage from the plugin, to the damage from the event (SHOULD fix WorldGuard/GriefPrevention/Towny(etc.) issues)
* Changed the density of the particles on the light arrow
* Removed the spider spawn when a player is hit by a darkness arrow
* Removed the ability to use these arrows with an infinity bow (TEMPORARY, SLOPPY FIX! WILL CLEANSE CODE LATER)
* Added a new configuration option to determine the amount of crafted arrows returned
* Added custom death messages in case a player was killed by a specialized arrow

### BETA 0.3 ###
* Redesigned the look of the config file for future enhancements on specific arrows
* Added a "Craftable" config option to define whether the arrow is able to be crafted or not
* Added permission nodes for shooting arrows. A reference can be found on the main page under the permission section
* Recoded crafting recipe handling. If a crafting recipe is disabled in the config, it will not load into RAM

### BETA 0.2 ###
* Added a new configuration file with a new set of options
* You can disable the crafting of specific arrows (They are still useable, just not obtainable through crafting)
* Added plugin Metrics. This can be disabled in the configuration file (Enabled by default)
* General stability fixes with the arrows

### BETA 0.1 ###
* Initial release. Please view main page