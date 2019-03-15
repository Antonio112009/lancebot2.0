package roles;

import config.BotConfig;
import general.Data;
import general.EmbedCreator;
import general.General;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Recruit {

    private General general = new General();
    private EmbedCreator embed = new EmbedCreator();

    private String[] roleName = {"Командование","Замком","Офицер","Сержант","Основной состав","Запас"};

    //Сколько дней в рекрутах
    private String recruitLast = "2-мес";
    private String filename = "resources2/audits/lance_recruit.txt";


    private boolean isRecruit(Data data){
        for(Role role : data.getMentionedMember().getRoles()){
            if(role.getName().equals("Рекрут")) return true;
        }
        return false;
    }


    public void addRecruit(MessageReceivedEvent event) {

        boolean hasRoleRecruit = false;
        boolean hasRoleLance = false;

        Data data = new Data(event);

        if (data.getContent().equals("!рекрут список")) {
            data.getChannel().sendMessage(general.showList(event, filename, "Рекрут")).queue();
        } else {
            //Проверка mentioned
            if (!data.isMentioned()) {
                data.getChannel().sendMessage("Вы забыли упомянуть человека").queue();
                return;
            }

            if ((data.getCommand().length == 2) || ((data.getCommand().length > 3) && (data.getComment().length == 2))) {

                //Проверить рекрута
                for (Role roleList : data.getMentionedMember().getRoles()) {
                    if (roleList.getName().startsWith("Рекрут")) hasRoleRecruit = true;
                    if (roleList.equals(data.getLanceRole())) hasRoleLance = true;
                }

                List<Role> roles = new ArrayList<>(data.getMentionedMember().getRoles());

//                System.out.println("command = " + data.getCommand().length + " comment = " + data.getComment().length);
                //Проверить, что Ланс
                if(hasRoleLance){
                    //Проверить, что рекрут
                    if(hasRoleRecruit){
                        data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " уже получил роль \"Рекрут\"\n" +
                                "Дата окончания рекрутства: `IN PROGRESS`").queue();
                    } else if (data.getCommand().length > 3 && data.getComment().length == 2) {

                        //Удаление ролей выше Рекрутаa
                        for (String nameR : roleName){
                            for (int i = 0; i < roles.size(); i++){
                                if (roles.get(i).getName().equals(nameR)) roles.remove(i);
                            }
                        }

                        roles.add(data.getRecruitRole());
                        data.getController().modifyMemberRoles(data.getMentionedMember(), roles).queue();

                        boolean result = general.addPerson( filename, data.getMentionedMember().getUser().getId(), general.getDate(recruitLast));
                        if(result) {
                            embed.createMessageAudit(data.getLanceAudit(), data.getMember(), "Понижение до рекрута", "Игрок " + data.getMentionedMember().getAsMention() + " понижен до роли \"Рекрут\"\nПричина: " + data.getComment()[1], 26, 188, 156);
                        } else {
                            data.getChannel().sendMessage("Произошла ошибка в написании команды. Вероятнее всего в написании даты").queue();
                        }
                        //                        recruit.addPerson(nicknameMentioned, getTodaysDate());

                    } else {
                        data.getChannel().sendMessage("Игрок уже состоит в клане и по званию выше Рекрута. Если вы хотите понизить игрока до рекрута, то напишите `!рекрут @НИКНЕЙМ ++ ПРИЧИНА`").queue();
                    }

                } else {

                    roles.add(data.getLanceRole());

                    if(hasRoleRecruit){
                        data.getController().modifyMemberRoles(data.getMentionedMember(), roles).queue();
                        data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " уже получил роль \"Рекрут\"\n" +
                                "Дата окончания рекрутства: `IN PROGRESS`").queue();
                    } else {
                        roles.add(data.getRecruitRole());
                        data.getController().modifyMemberRoles(data.getMentionedMember(), roles).queue();
                        data.getChannel().deleteMessageById(data.getEvent().getMessageId()).queue();
                        data.getChannel().sendMessage("Новый рекрут принят в клан").queue();

                        boolean result = general.addPerson( filename, data.getMentionedMember().getUser().getId(), general.getDate(recruitLast));
                        if(result) {
                            embed.createMessageAudit(data.getLanceAudit(), data.getMember(), "Новый рекрут", "В клан принят новый рекрут " + data.getMentionedMember().getAsMention(), 26, 188, 156);
                        } else {
                            data.getChannel().sendMessage("Произошла ошибка. Обратитесь к <@" + BotConfig.SPECIAL_ID +"> за помощью").queue();
                        }
                    }
                }
            } else {
                data.getChannel().sendMessage("Произошла ошибка. Проверьте правильность написания команды").queue();
            }
        }
    }



    //Продление рекрутства
    public void addDaysRecruit(MessageReceivedEvent event){

        Data data = new Data(event);

        if (!data.isMentioned()) {
            data.getChannel().sendMessage("Вы забыли упомянуть человека").queue();
            return;
        }

        if(!isRecruit(data)){

            data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " не является рекрутом.").queue();
            return;
        }

        if (data.getCommand().length == 2){
            data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() +
                    "\nДата окончания рекрутства: `" + general.getLastDay(data.getMentionedMember().getUser().getId(), filename) + "`" +
                    "\nОсталось дней - `" + general.checkDaysLeft(data.getMentionedMember().getUser().getId(), filename) + "`").queue();
        } else if ((data.getCommand().length > 4) && (data.getComment().length == 2)) {
            if(general.addDateToPerson(filename, data.getMentionedMember().getUser().getId(), data.getCommand()[2])) {
                data.getChannel().sendMessage("Игроку " + data.getMentionedMember().getUser().getName() + " продлили рекрутство" +
                        "\nДата окончания рекрутства: `" + general.getLastDay(data.getMentionedMember().getUser().getId(), filename) + "`" +
                        "\nОсталось дней - `" + general.checkDaysLeft(data.getMentionedMember().getUser().getId(), filename) + "`").queue();

                embed.createMessageAudit(data.getLanceAudit(), data.getMember(), "Продление Рекрутства", "Игроку " + data.getMentionedMember().getAsMention() + " продлили роль \"Рекрута\"\n" +
                        "Причина: " + data.getComment()[1] + "\n" +
                        "Новая дата окончания рекрутства: `" + general.getLastDay(data.getMentionedMember().getUser().getId(), filename) + "`\n", 26, 188, 156);
            } else {
                data.getChannel().sendMessage("Произошла ошибка. Проверьте правильность написания команды").queue();
            }
        }
    }



    //Дата окончания рекрутства


    //Удаление рекрута из аудита.
    public void removeRecruit (String mentionedID){
        try {
            File inputFile = new File(filename);
            File tempFile = new File("lance_recruit_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(trimmedLine.startsWith(mentionedID)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(inputFile);
        } catch (Exception e){
            //TODO правильно записать ошибку в чат
            e.printStackTrace();
            general.addErrorToAudit("removeRecruit", e);
        }
    }
}
