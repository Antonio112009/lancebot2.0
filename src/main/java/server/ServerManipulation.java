package server;

import config.BotConfig;
import general.Data;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServerManipulation {
    public void startPrivateConversation (MessageReceivedEvent event){
        Data data = new Data(event);

        event.getChannel().deleteMessageById(event.getMessageId()).queue();
        List<Permission> List = Arrays.asList(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE);

        String name = data.getMentionedMember().getUser().getName().toLowerCase().replaceAll(" ", "");

        event.getGuild().getCategoriesByName("Hall", true).get(0)
                .createTextChannel(name)
                .addPermissionOverride(event.getGuild().getPublicRole(), Collections.emptyList(), List)
                .addPermissionOverride(event.getMessage().getMentionedMembers().get(0), List, Collections.emptyList())
                .addPermissionOverride(event.getGuild().getRolesByName("Офицер", true).get(0), List, Collections.emptyList())
                .addPermissionOverride(event.getGuild().getRolesByName("Командование", true).get(0), List, Collections.emptyList())
                .addPermissionOverride(event.getGuild().getRolesByName("Замком", true).get(0), List, Collections.emptyList())
                .addPermissionOverride(event.getGuild().getRolesByName("Механик", true).get(0), List, Collections.emptyList())
//                .addPermissionOverride(event.getGuild().getMemberById("494226296969232394"), List, Collections.emptyList())
                .addPermissionOverride(event.getGuild().getMemberById(BotConfig.BOT_ID), List, Collections.emptyList())
                .queue();

        event.getGuild().getCategoriesByName("Hall", true).get(0)
                .createVoiceChannel(name)
                .addPermissionOverride(event.getGuild().getPublicRole(), Collections.emptyList(), List)
                .addPermissionOverride(event.getMessage().getMentionedMembers().get(0), List, Collections.emptyList())
                .addPermissionOverride(event.getGuild().getRolesByName("Офицер", true).get(0), List, Collections.emptyList())
                .addPermissionOverride(event.getGuild().getRolesByName("Командование", true).get(0), List, Collections.emptyList())
                .addPermissionOverride(event.getGuild().getRolesByName("Замком", true).get(0), List, Collections.emptyList())
                .addPermissionOverride(event.getGuild().getRolesByName("Механик", true).get(0), List, Collections.emptyList())
                .queue();
    }

    public void stopPrivateConversation(MessageReceivedEvent event){
        if(event.getChannel().getName().equals("получить-роль") || event.getChannel().getName().equals("hall") || event.getChannel().getName().equals("lance_recruit")) {
            event.getChannel().deleteMessageById(event.getMessageId()).queue();
            return;
        }

        try {
            if (event.getMessage().getCategory().getName().equals("Hall")) {
                event.getMessage().getTextChannel().delete().queue();
                event.getGuild().getVoiceChannelsByName(event.getMessage().getTextChannel().getName(), true).get(0).delete().queue();
            }
        } catch (Exception e){}
    }

    public void sendDocuments (MessageReceivedEvent event){
        String text = "";
    }

}