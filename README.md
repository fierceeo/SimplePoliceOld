Thanks for checking out my police plugin. Here is some information on how to use this.

**Usage**

Make sure to have essentials installed and create a jail named “jail1”

To add a police do /police add (username)

To jail a player, make sure you are a police and attack a player with a blaze rod

**Features**

Here is a list of things this plugin can do:
1. Lets you arrest players and opens a gui on arrest
2. Lets you pay police when you arrest a player
3. Lets you take money from a player who got arrested
4. A frisking system, lets you take away contraband items from a player, but you can set the percentage of finding an item
5. A message.yml file lets you translate this plugin into any language (enlgish is the default and spanish can be found at the link below:)
https://github.com/ramdon-person/SimplePolice/tree/master/Translations

Jailing System:

You can attack a player with a blaze rod (you can change the baton material in the config.yml file) which will open a gui asking how how long you want to jail that player for.


Frisking system:

A frisking system allows you to basically scan players for contraband items.
Things like the precent chance of an item being detected and the material for the frisk stick can be configured in the config file.
The frisking system supports custom items, and has support for quality armory guns.


911 System:

Players can "dial 911" using /911 (message) or just simply /911 which notifies all the police on the server


Police Tp:

Police can teleport to players within a configuged radius of them which notifies the player.


Api:

Simple Police has an api which is constantly improving and is open to user feedback and suggestions.


Plugins that use SimplePolice:

https://www.spigotmc.org/resources/simplebankrobbing.78558/


Note: if you would like your plugin added here, please contact me on discord. (My tag is ramdon#9244)


**Permissions**

police.add, Allows you to add a police

police.remove, Allows you to remove a police

police.help, Allows you to see the help pages

police.list, Allows you to see the list of police

police.unjail, Allows you to unjail someone

**Commands**

/police add (username) - adds a police

/police remove (username) - removes a police

/police help - shows all the commands

/police tp (username) - teleports a player to a random distance away from a player (max distance is configurable in the config)

/police admin - shows the police admin commands

/police unjail - unjails a player

/911 - calls the police

By default a police will have access to /police help, /police unjail and /police tp

**Screenshots:**

Jail time gui:
Screen Shot 2020-06-24 at 6.29.44 PM.png

Arresting in a safe area:
Screen Shot 2020-06-24 at 6.37.24 PM.png

**API**

Maven Repo:

	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>

 	<dependency>
	    <groupId>com.github.ramdon-person.SimplePolice</groupId>
	    <artifactId>SimplePolice-API</artifactId>
	    <version>3.2</version>
	</dependency>

Java docs:
https://plugins.voidcitymc.com/SimplePolice/doc/

Getting the api:

SimplePoliceAPI api = ((SimplePolice) Bukkit.getPluginManager().getPlugin("Simple Police")).getApi();

The you can do stuff like api.listPolice();

Note: your plugin will have to depend (or soft depend) on SimplePolice to use the api

Also, if you can't access the maven repo, please check the releases page for the api jar.

**Note:**

Requires Essentials to be setup and a jail named “jail1”

This plugin has uuid support

I will be adding support for multiple jails soon!

**Latest Dev Build:**
<img src="https://ci.codemc.io/job/ramdon-person/job/SimplePolice/badge/icon"
     alt="Badge"
     style="float: left; margin-right: 10px;" />

Plugin:

https://ci.codemc.io/job/ramdon-person/job/SimplePolice/lastSuccessfulBuild/artifact/SimplePolice/target/SimplePolice-3.3.jar

Api:

https://ci.codemc.io/job/ramdon-person/job/SimplePolice/lastSuccessfulBuild/artifact/SimplePolice-API/target/SimplePolice-API-3.3.jar


**Support:**

If you need support feel free to join my discord server: https://discord.gg/4NTmm8Q
