package roles;

import general.Data;
import general.EmbedCreator;
import general.General;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Holiday {

    private General general = new General();
    private EmbedCreator embed = new EmbedCreator();

    private String[] roleName = {"Командование","Замком","Офицер","Сержант","Основной состав","Рекрут"};
    private String filename = "resources2/audits/lance_holiday.txt";


    private boolean onHoliday(Data data){
        for(Role role : data.getMentionedMember().getRoles()){
            if(role.getName().equals("Запас")) return true;
        }
        return false;
    }

    public void addHoliday(MessageReceivedEvent event){

        //FIXME если дата не введена - вводится эррор?!?!?
        //FIXME разбиение не выводит ошибки: "!запас_add @Tony Anglichanin#3069 13-08-19++ прост"

        //TODO Добавить дату ухода в запас и ПРИЧИНУ УХОДА В ЗАПАС

        Data data = new Data(event);

        if (data.getContent().equals("!запас список")) {
            data.getChannel().sendMessage(general.showList(event, filename, "Запас")).queue();
        } else {
            //Проверка mentioned
            if (!data.isMentioned()) {
                data.getChannel().sendMessage("Вы забыли упомянуть человека или команда введена неправильно").queue();
                return;
            }

            List<Role> roles = new ArrayList<>(data.getMentionedMember().getRoles());
            for(Role role : roles)
                if(role.getName().equals("Запас")){
                    //TODO доделать!
                    data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " уже получил роль \"В Запасе\"\n" +
                            "Запас продлится до: "/* + holidays.getFinalDate(nicknameMentioned)*/).queue();
                    return;
                }


            if(data.getCommand().length == 2){
                    data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " не имеет роли \"Запас\"\n").queue();
            } else if((data.getCommand().length > 3) && (data.getComment().length == 2)){

                String addRole = "";
                //Взять нынешнюю роль
                for(int i = 0; i < roles.size(); i++)
                    for (String roleN : roleName)
                        if(roles.get(i).getName().equals(roleN)) {
                            addRole = roleN;
                            roles.remove(i);
                            roles.add(data.getRoleCustom("Запас"));
                        }

                boolean result = general.addPerson(filename, data.getMentionedMember().getUser().getId(), general.getDate(data.getCommand()[2]),addRole);
                if(result) {
                    data.getController().modifyMemberRoles(data.getMentionedMember(), roles).queue();
                    embed.createMessageAudit(data.getLanceAudit(), data.getMember(), "Перевод в запас",
                            "Игрок " + data.getMentionedMember().getAsMention() + " переведен в запас\n" +
                                    "Дата окончания запаса: `" + general.getLastDay(data.getMentionedMember().getUser().getId(), filename) + "`\n" +
                                    "Роль игрока: " + addRole + "\nПричина: " + data.getComment()[1], 52, 152, 219);
                    //TODO доделать IN PROGRESS
                    data.getChannel().sendMessage("Запас продлится до: '" + general.getLastDay(data.getMentionedMember().getUser().getId(), filename) + "'").queue();
                } else {
                    data.getChannel().sendMessage("Произошла ошибка. Проверьте правильность написания команды").queue();
                }
            } else {
                data.getChannel().sendMessage("Произошла ошибка. Проверьте правильность написания команды").queue();
            }
        }
    }



    //Продлить запас
    public void addDaysHoliday(MessageReceivedEvent event){

        Data data = new Data(event);

        if (!data.isMentioned()) {
            //TODO правильно вывести команду на экран
            data.getChannel().sendMessage("Вы забыли упомянуть человека").queue();
            return;
        }

        if(!onHoliday(data)){
            data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " не находится в запасе.").queue();
            return;
        }

        if (data.getCommand().length == 2){
            data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() +
                    "\n Запас продлится до: `" + general.getLastDay(data.getMentionedMember().getUser().getId(), filename) + "`" +
                    "\n Осталось дней - `" + general.checkDaysLeft(data.getMentionedMember().getUser().getId(), filename) + "`").queue();
        } else if ((data.getCommand().length > 4) && (data.getComment().length == 2)) {
            if(general.addDateToPerson(filename, data.getMentionedMember().getUser().getId(), data.getCommand()[2])) {
                data.getChannel().sendMessage("Игроку " + data.getMentionedMember().getUser().getName() + " продлили запас" +
                        "\nДата окончания запаса: `" + general.getLastDay(data.getMentionedMember().getUser().getId(), filename) + "`" +
                        "\nДней до конца запаса - `" + general.checkDaysLeft(data.getMentionedMember().getUser().getId(), filename) + "`").queue();

                embed.createMessageAudit(data.getLanceAudit(), data.getMember(), "Продление Запаса", "Игроку " + data.getMentionedMember().getAsMention() + " продлили роль \"Запас\"\n" +
                        "Новая дата окончания запаса: `" + general.getLastDay(data.getMentionedMember().getUser().getId(), filename) + "`\n" +
                        "Причина: " + data.getComment()[1] + "\n", 52,152,219);
            } else {
                //TODO вывести ошибку в чат правильно!
                data.getChannel().sendMessage("Произошла ошибка. Проверьте правильность написания команды").queue();
            }
        }
    }



    //TODO провести тест метода!!!
    //Убрать из запаса
    public void removeHoliday(MessageReceivedEvent event){

        Data data = new Data(event);

        if (!data.isMentioned()) {
            data.getChannel().sendMessage("Вы забыли упомянуть человека").queue();
            return;
        }

        boolean hasRole = false;

        for (Role role : data.getMentionedMember().getRoles()) {
            if (role.equals(data.getRoleCustom("Запас")))
                hasRole = true;
        }

        if (hasRole) {
            List<Role> roles = new ArrayList<>(data.getMentionedMember().getRoles());

            try {
                File inputFile = new File(filename);
                File tempFile = new File("lance_holiday_temp.txt");

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String currentLine;

                while((currentLine = reader.readLine()) != null){
                    // trim newline when comparing with lineToRemove
                    String trimmedLine = currentLine.trim();
                    if(trimmedLine.startsWith(data.getMentionedMember().getUser().getId())){
                        String[] splistLine = trimmedLine.split("\\|");
                        roles.add(data.getRoleCustom(splistLine[2]));
                        continue;
                    }
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                writer.close();
                reader.close();
                boolean successful = tempFile.renameTo(inputFile);
            } catch (Exception e){
                //TODO улучшить запись ошибки в аудит
                System.out.println("Проблема в removeHoliday-методе");
                e.printStackTrace();
                general.addErrorToAudit(event.getMessage().getContentRaw(), e);
            }
            for (int i = 0; i < roles.size(); i++)
                if(roles.get(i).getName().equals("Запас"))
                    roles.remove(i);
            data.getController().modifyMemberRoles(data.getMentionedMember(), roles).queue();
            embed.createMessageAudit(data.getLanceAudit(), data.getMember(),"Выведение из запаса",
                    "Игрок " + data.getMentionedMember().getAsMention() + " выведен из запаса",52,152,219);
            data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " выведен из запаса\n").queue();
        } else {
            data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " не находится в запасе.").queue();
        }
    }
}
