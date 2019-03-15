package general;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;



// https://leovoel.github.io/embed-visualizer/

public class EmbedCreator {
    private EmbedBuilder embed = new EmbedBuilder();


    public void createMessageAudit(MessageChannel channel, Member author, String title, String text, String image, int r, int g, int b){
        embed.setColor(new Color(r,g,b));
        if(author.getNickname() == null) {
            embed.setAuthor(author.getUser().getName(), null, author.getUser().getAvatarUrl());
        } else {
            embed.setAuthor(author.getNickname(), null, author.getUser().getAvatarUrl());
        }

        if (!title.isEmpty())
            embed.setTitle(title);
        embed.setDescription(text);
        if(!image.isEmpty())
            embed.setImage(image);
        embed.setTimestamp(Instant.now());

        channel.sendMessage(embed.build()).queue();
    }


    public void createMessageAudit(MessageChannel channel, Member author, String title, String text, int r, int g, int b){
        embed.setColor(new Color(r,g,b));
        if(author.getNickname() == null) {
            embed.setAuthor(author.getUser().getName(), null, author.getUser().getAvatarUrl());
        } else {
            embed.setAuthor(author.getNickname(), null, author.getUser().getAvatarUrl());
        }

        if (!title.isEmpty())
            embed.setTitle(title);
        embed.setDescription(text);
        embed.setTimestamp(Instant.now());

        channel.sendMessage(embed.build()).queue();
    }


    public void createMessageNotification(MessageChannel channel, Member author, String title, String text){
        embed.setColor(new Color(255,255,0));
        if(author.getNickname() == null) {
            embed.setAuthor(author.getUser().getName(), null, author.getUser().getAvatarUrl());
        } else {
            embed.setAuthor(author.getNickname(), null, author.getUser().getAvatarUrl());
        }

        if (!title.isEmpty())
            embed.setTitle(title);
        embed.setDescription(text);
        embed.setTimestamp(Instant.now());

        channel.sendMessage(embed.build()).queue();
    }

    public void createMessageKick(MessageChannel channel, Member author, String title, String text){
        int color = 165;
        embed.setColor(new Color(color,color,color));
        if(author.getNickname() == null) {
            embed.setAuthor(author.getUser().getName(), null, author.getUser().getAvatarUrl());
        } else {
            embed.setAuthor(author.getNickname(), null, author.getUser().getAvatarUrl());
        }

        if (!title.isEmpty())
            embed.setTitle(title);
        embed.setDescription(text);
        embed.setTimestamp(Instant.now());

        channel.sendMessage(embed.build()).queue();
    }


    public void createServerStatus(MessageChannel channel, String title, String text, int status){

        if(status == 0)
            embed.setColor(Color.red);
        if(status == 1)
            embed.setColor(Color.yellow);
        if(status == 2)
            embed.setColor(Color.green);
        if (status == 3)
            embed.setColor(Color.orange);

        embed.setTitle(title);
        embed.setDescription(text);
        embed.setTimestamp(Instant.now());
        channel.sendMessage(embed.build()).queue();
    }





    //    https://gist.github.com/zekroTJA/c8ed671204dafbbdf89c36fc3a1827e1



}


