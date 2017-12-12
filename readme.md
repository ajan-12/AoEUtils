1) Tablist with per-rank prefixes, per-world prefixes and per-rank name colors (Consider using [TAB](https://www.spigotmc.org/resources/tab-1-8-x-1-12-x.50065/) + [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/))

2) Rtp

3) Custom structure generator (Separate plugin recommended)

4) Join messages hidden for players who have been offline for less than for 4 mins

5) Join messages show to joining player regardless of offline time

6) Fake wb messages from afk users upon join, only visible to joining player

7) Optional registration system which lets you play as a guest until you register

8) System that sets your rank to member and prevents your account from being accessed by cracked users if you join with a premium client for the first time which can be reset to the normal password system by registering

9) Announcements that draw from specific pools for specific types of player distinguished by permissions

10) A system that replicates the function of my generic npcs system which i'll explain in further detail once that needs to happen

11) A feature that reloads/resends the nearby chunks to a player to remove ghost blocks

12) Making the wands work

13) ./playtime command (Separate plugin recommended)

14) ./pmute which lets players vote to mute a player minimum of 3 players need to vote minimum of ((1+x/3+x)*100) % of players need to vote yes where x is the amount of times the player has been muted

15) A system that deletes data and coreprotect rollbacks players that have played for less than 10 hours but haven't joined for more than a month

16) System which makes saplings spawn from existing trees and makes dropped saplings self-plant into sapling blocks (I hate this idea -yash)

17) Battles (Separate plugin recommended)

18) Duels (Separate plugin recommended)

19) Bounty system which increases a player's bounty if they kill a player that has no bounty by at least 50 or 5% of the killed player's money and the bounty added to the killer is removed from the victim's balance (Separate plugin recommended)

20) Command to spawn in a specified type of sf backpack with the specified id and uuid of the specified user

21) If the player kills a specified type of npc a per-npc configurable amount of money is removed from the killer and placed on them as bounty

22) A /server command for warping between the available worlds currently uhc and towny and parkour and creative specific permission nodes for each

23) A system that calculates the amount of active players in uhc and changes the worldborder size so that the world has a configurable amount of square area for each player (sqrt(playercount * x) + spawnsize) where x is the configurable variable and the result is the diameter of the worldborder 

24) The entitylimiter system (Separate plugin recommended)

25) A system that detects reliably if a player is afk and then based on the ratio of the average playercount and the configurable value
add a certain amount of afk time to the player, based on the amount of time they afked [(playercount/configurable value) * AFK time = Amount to add to afk time variable]

26) Automatic restart system which stores the average playtimes of each hour of each day of the week and tries to determine an optimal time to restart and restarts are at most x hours and at least y hours apart

27) a treasure chest system to refill specific chests with configurable loot tables that spawns more treasure chests in the wilderness if a minimum refill count per day isn't met that removes treasure chests from wilderness if a maximum refill count per day is exceeded and that provides each treasure chest its own refill cooldown that only refills chests in loaded chunks

28) UHC world ban system

29) a system that upon death lets a player teleport to their death coordinates with a cooldown that is a configurable % of the player's current playtime the ability timeouts in 5 minutes, if the player doesn't teleport in the first two or so minutes other players will for the remaining 3 or 2 minutes be able to teleport to the death location

30) A shortcut command to make end gateway portals leading into specific coordinates

31) A command that broadcasts a message for all but specified players, by a specified player (./command <sender> <excluded_player> <message ...>)

32) A command that broadcasts a message for specified players, by a specified player (./command <sender> <included_player> <message ...>)
  
33) System to give new custom advancements
  
