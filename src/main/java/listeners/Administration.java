package listeners;

import config.BotConfig;
import general.General;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import roleReaction.RoleReactionEdit;
import roles.Holiday;
import roles.Recruit;
import roles.RoleManipulation;
import server.ServerManipulation;
public class Administration extends ListenerAdapter {

    private String version = "2.0.6";
    private String[] roleAdmin = {"Командование", "Замком", "Офицер"};
    private String[] roleAdmin2 = {"Командование", "Замком", "Офицер", "Сержант"};

    //Данные для работы
    private JDA api;
    private String content;
    private MessageChannel channel;

    //Другие классы
    private General general = new General();
    private Recruit recruit = new Recruit();
    private Holiday holiday = new Holiday();
    private RoleManipulation roleManipulation = new RoleManipulation();
    private RoleReactionEdit reactionEdit = new RoleReactionEdit();
    private ServerManipulation serManip = new ServerManipulation();


    //Constructor
    public Administration(JDA api) {
        this.api = api;
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;

        if(!giveAccess(event, roleAdmin2)) return;

        try {
            setInitialData(event, event.getChannel(), event.getMember());
        } catch (Exception e) {
            System.out.println("Проблема в setInitialData-метод");
            e.printStackTrace();
        }

        if(content.toLowerCase().startsWith("!список")) {
            roleManipulation.getlLanceList(event);
            return;
        }

        if(content.toLowerCase().startsWith("!доки"))
            serManip.sendDocuments(event);


        if (!giveAccess(event, roleAdmin)) return;

        String[] command = content.split(" ");

        //Работает везде
        if(content.toLowerCase().startsWith("!дел"))
            general.deleteMessages(event, channel, command);

        else if(content.toLowerCase().startsWith("!напалм"))
            general.napalmMessages(event);

        else if (content.startsWith("!рекрут "))
            recruit.addRecruit(event);

        else if(content.startsWith("!talk "))
            serManip.startPrivateConversation(event);

        else if(content.equals("!close"))
            serManip.stopPrivateConversation(event);


        //Работает только в боте
        //TODO добавить только чтение из одного канала!!
        else if(event.getChannel().getName().equals("lance_bot")) {
            if (content.toLowerCase().equals("!помощь"))
                general.help(channel, version);

            else if (content.toLowerCase().equals("!версия"))
                channel.sendMessage("Текущая версия бота - " + version).queue();

            else if (content.startsWith("!рекрут_add"))
                recruit.addDaysRecruit(event);

            else if (content.startsWith("!основа"))
                roleManipulation.upgradeSoldier(4, event, 230, 126, 34);

            else if (content.startsWith("!сержант"))
                roleManipulation.upgradeSoldier(3, event, 241, 196, 15);

            else if (content.startsWith("!офицер"))
                roleManipulation.upgradeSoldier(2, event, 153, 45, 34);

            else if (content.startsWith("!запас "))
                holiday.addHoliday(event);

            else if (content.startsWith("!запас_add"))
                holiday.addDaysHoliday(event);

            else if (content.startsWith("!запас_нет "))
                holiday.removeHoliday(event);

            else if (content.startsWith("!исключить "))
                roleManipulation.addLeave(event);

            else if (content.startsWith("!exit")) {
                event.getGuild().getTextChannelsByName("lance_bot", true).get(0).deleteMessageById(event.getMessageId()).queue();
                System.exit(1);

            } else if (content.startsWith("!сезон закрыт"))
                general.season(event, "true");

            else if (content.startsWith("!сезон открыт"))
                general.season(event, "false");

            else if (content.startsWith("!аудит "))
                general.auditInfo(event);

            else if (content.startsWith("!инфо"))
                general.information(event);

            else if (content.startsWith("!статус закрыт"))
                general.lanceConfig("serverStatus", "false");

            else if (content.startsWith("!статус открыт"))
                general.lanceConfig("serverStatus", "true");


            //TODO Возможно надо удалить будет.. Пока ХЗ

//            else if(content.startsWith("er!add")){
//                reactionEdit.addReactionRole(event, content);
//
//            }
//            else if(content.startsWith("er!del")){
//                reactionEdit.deleteReactionRole(event);
//
//            }



            //TODO если будут часто происходить ошибки - убрать комментарии
//            else if (content.startsWith("!"))
//                channel.sendMessage("Ошибка при вводе команды!").queue();
        }
    }










    //Даем права доступа
    private boolean giveAccess(MessageReceivedEvent event, String[] roleAdmin){
        for(String role : roleAdmin){
            if(event.getAuthor().getId().equals(BotConfig.SPECIAL_ID)){
                return true;
            }

            for (Role Drole : event.getMember().getRoles()){
                if(Drole.getName().equals(role)){
                    return true;
                }
            }
        }
        return false;
    }


    //Заполнение данных
    private void setInitialData(MessageReceivedEvent event, MessageChannel channel, Member member) {
        this.content = event.getMessage().getContentRaw().replaceAll("\\s+"," ");
        this.channel = channel;
    }
}
