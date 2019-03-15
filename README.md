# Discord bot for gaming clan Lance.
[![MIT Lisense](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/Antonio112009/LanceBot/blob/master/LICENSE)  ![GitHub top language](https://img.shields.io/github/languages/top/antonio112009/LanceBot.svg)
![Java version](https://img.shields.io/badge/java%20version-11.0.2-red.svg)
![Maven version](https://img.shields.io/badge/maven%20version-3.6.0-red.svg)
![Kotlin version](https://img.shields.io/badge/kotlin%20version-1.3.21-red.svg)

###Description
This bot is designed to simplify administration work on a server. There are various commands which are often used by all server administrators.
Bot runs on a server since October 2018. Since then it had several minor issues that were handled by Antonio112009 in a short time period.
According to admins opinion, bot is saving plenty of time as one line of command replaces multiple actions that admins had to do before bot was implemented on a server.  
[![Discord](https://img.shields.io/badge/join-discord-green.svg)](https://discord.gg/j7bAn94)  

<img src="https://github.com/Antonio112009/LanceBot/blob/master/readmeFiles/screen1.png" width="400" height="auto">

### Actions that bot could do
1. Assign people to specific role.
2. Send private message to members of the server.
3. Promote people by changing their role to higher in the hierarchy of all roles.
4. Bot keeps data of people in it's own audit. That helps to see how long people had specific role and notify administrators to promote/kick people or leave them with current role.
5. Bot gives the latest information about game services by reading API of external source every minute.
6. Bot manages gaming server that is owned by clan.

<img src="https://github.com/Antonio112009/LanceBot/blob/master/readmeFiles/screen2.png" width="auto" height="auto">


### Commands
Here's a list of all commands that bot can do.

| command | access | description |
| ------- | ------ | ----------- |
| !помощь | admins| Shows all commands that bot has |
| !рекрут @NICKNAME | admins | Add player to clan. The bot gives player corresponding role, makes a record in the audit. |
| !рекрут список | admins | Show the list of recruits, their start dates and how many days until the end of recruitment |
| !рекрут_add @NICKNAME N ++ REASON | admins | Extend the player to the role of Recruit in the clan. The bot adds days to its audit, makes a record in the audit. N stands for `N-week`, `N-month`, `DD-MM-GG` |
| !основа @NICKNAME ++ REASON | admins | Change the current role of the player to "Main Team". The bot hangs the corresponding tags, makes a record in the audit |
| !сержант @NICKNAME ++ REASON | admins | Change the current role of the player to "Sergeant". The bot hangs the corresponding tags, makes a record in the audit |
| !офицер @NICKNAME ++ REASON | admins | Change the current role of the player to "Officer". The bot hangs the corresponding tags, makes a record in the audit |
| !запас список | admins | show the list of players on vacation, their starting dates and how many days until the end |
| !запас @NICKNAME | admins | the bot is looking at whether the player is on vacation or not. If there is, bot says the end date |
| !запас @NICKNAME N ++ REASON | admins | Bot marks that player is on a vacation, makes a record in the audit. N stands for `N-week`, `N-month`, `DD-MM-GG` |
| !запас_add @NICKNAME N ++ REASON | admins | Bot extends due of player's vacation, makes a record in the audit. N stands for `N-week`, `N-month`, `DD-MM-GG` |
| !запас_нет @NICKNAME | admins | Bot returns people normal roles that were taken during their vacation, makes a record in the audit |
| !исключить @NICKNAME ++ REASON | admins | Bot kicks person from clan. Deletes all roles related with the clan,  makes a record in the audit and sends private message with information that player have to do after been kicked |
| !пароль NEW_PASSWORD | admins | Bot sets password for a training server that clan owns |
| !пароль дефолт | admins | Bot sets default password of the server |
| !версия | admins | Show current version of the bot |
| !talk @NICKNAME | admins | Creates temporary private text and voice channel with @NICKNAME. The function is needed to conduct interviews with likely new clan players. |
| !close | admins | Delete temporary private text and voice channel |

The bot is still under development.
