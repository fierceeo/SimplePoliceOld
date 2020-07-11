Thanks for checking out my police plugin. Here is some information on how to use this.

**Usage**

Make sure to have essentials installed and create a jail named “jail1”

To add a police do /police add (username)

To jail a player, make sure you are a police and attack a player with a blaze rod

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

/police reload - reloads the config file

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
        <id>SimplePolice-API</id>
        <url>https://raw.githubusercontent.com/ramdon-person/SimplePolice/master/SimplePolice-API/target/mvn-repo/</url>
    </repository>

    <dependency>
        <groupId>com.voidcity.SimplePolice-API</groupId>
	<artifactId>SimplePolice-API</artifactId>
	<version>3.1</version>
    </dependency>

A list of all the methods in the api can be found here (unfortunately I wasn’t able to compile a java doc with this interface):
https://github.com/ramdon-person/SimplePolice/blob/master/SimplePolice-API/src/com/voidcitymc/api/SimplePolice/SimplePoliceAPI.java

Getting the api:
SimplePoliceAPI api = (SimplePoliceAPI) Bukkit.getPluginManager().getPlugin("Simple Police”);

Note: your plugin will have to depend (or soft depend) on SimplePolice to use the api
**Note:**

Requires Essentials to be setup and a jail named “jail1”

This plugin has uuid support

I will be adding support for multiple jails soon!

Support:

If you need support feel free to join my discord server: https://discord.gg/4NTmm8Q
