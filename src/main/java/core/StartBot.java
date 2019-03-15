package core;

import config.BotConfig;
import general.General;
import listeners.PublicManipulation;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import listeners.Administration;
import roleReaction.RoleReactionEdit;
import timings.WalkingTimer;

public class StartBot {

    public static void main(String[] args){
        try{
            JDA api = new JDABuilder(AccountType.BOT).setToken(BotConfig.TOKEN).setAutoReconnect(true).build();

            //Listeners
            api.addEventListener(new RoleReactionEdit());
            api.addEventListener(new Administration(api));
            api.addEventListener(new PublicManipulation());

            //Timers
            WalkingTimer timer = new WalkingTimer(api);
            timer.start();

        } catch (Exception e){
            System.out.println("Проблема в StartBot-class");
            General general = new General();
            general.addErrorToAudit("check api", e);
            e.printStackTrace();
        }
    }
}