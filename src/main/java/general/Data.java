package general;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

public class Data {
    private JDA api;
    private MessageReceivedEvent event;
    private GuildController controller;
    private String content;
    private MessageChannel channel;
    private Member member;
    private Member mentionedMember;
    private String[] command;
    private String[] comment;
    private TextChannel lanceAudit;
    private TextChannel lanceNews;
    private TextChannel lanceOfficer;
    private Role lanceRole;
    private Role recruitRole;
    private boolean mentioned = true;

    public Data( MessageReceivedEvent event){
        this.event = event;
        this.controller = event.getGuild().getController();
        this.content = event.getMessage().getContentRaw().replaceAll("\\s{2,}", " ").trim();
        this.channel = event.getChannel();
        this.member = event.getMember();
        this.command = content.split(" ");
        this.comment = content.split("\\+\\+");
        this.lanceRole = event.getGuild().getRolesByName("Lance", true).get(0);
        this.recruitRole = event.getGuild().getRolesByName("Рекрут", true).get(0);

        this.lanceAudit = event.getGuild().getTextChannelsByName("lance_audit", true).get(0);
        this.lanceNews = event.getGuild().getTextChannelsByName("lance_news", true).get(0);
        this.lanceOfficer = event.getGuild().getTextChannelsByName("lance_officer", true).get(0);

        try {
            this.mentionedMember = event.getMessage().getMentionedMembers().get(0);
        } catch (Exception e){
            mentioned = false;
        }
    }

    public JDA getApi() {
        return api;
    }

    public GuildController getController() {
        return controller;
    }
    public MessageReceivedEvent getEvent() {
        return event;
    }

    public String getContent() {
        return content;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public Member getMember() {
        return member;
    }

    public Member getMentionedMember() {
        return mentionedMember;
    }

    public String[] getCommand() {
        return command;
    }

    public String[] getComment() {
        return comment;
    }

    public TextChannel getLanceAudit() {
        return lanceAudit;
    }

    public TextChannel getLanceNews() {
        return lanceNews;
    }

    public TextChannel getLanceOfficer() {
        return lanceOfficer;
    }

    public Role getLanceRole() {
        return lanceRole;
    }

    public Role getRecruitRole() {
        return recruitRole;
    }

    public Role getRoleCustom (String role){
        return event.getGuild().getRolesByName(role, true).get(0);
    }

    public boolean isMentioned() {
        return mentioned;
    }
}
