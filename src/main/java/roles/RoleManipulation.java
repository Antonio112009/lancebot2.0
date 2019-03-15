package roles;

import general.Data;
import general.EmbedCreator;
import general.General;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class RoleManipulation {

    private General general = new General();

    private String[] roleName = {"Командование","Замком","Офицер","Сержант","Основной состав","Рекрут"};
    private String[] roleName2 = {"Замком","Офицер","Сержант","Основной состав","Рекрут", "Запас", "Lance"};
    private EmbedCreator embed = new EmbedCreator();

    public void upgradeSoldier(int roleID, MessageReceivedEvent event, int r, int g, int b){

        Data data = new Data(event);

        if (!data.isMentioned()) {
            //TODO улучшить вывод ошибки на экран
            data.getChannel().sendMessage("Вы забыли упомянуть человека").queue();
            return;
        }

        List<Role> roles = new ArrayList<>(data.getMentionedMember().getRoles());

        for (Role roleList : data.getMentionedMember().getRoles()) {
            if (roleList.getName().startsWith(roleName[roleID])){
                data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " имеет роль \"" + roleName[roleID] + "\"\n").queue();
                return;
            }
        }
        if (data.getCommand().length == 2) {

            data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " игрок не имеет роль \"" + roleName[roleID] + "\"\n").queue();

        } else if ((data.getCommand().length > 3) && (data.getComment().length == 2)){
            Recruit recruit = new Recruit();
            recruit.removeRecruit(data.getMentionedMember().getUser().getId());

            for (int i = 0; i < roles.size(); i++){
                for(String rName : roleName)
                    if(roles.get(i).getName().equals(rName)) roles.remove(i);
            }
            roles.add(data.getRoleCustom(roleName[roleID]));
            data.getController().modifyMemberRoles(data.getMentionedMember(), roles).queue();

            //TODO в зависимости выводить понижение или повышение должности
            embed.createMessageAudit(data.getLanceAudit(), data.getMember(), "Получение новой роли", "Игрок " + data.getMentionedMember().getAsMention() + " получил роль \"" + roleName[roleID] + "\"\nПричина: " + data.getComment()[1], r, g, b);



        } else {
            //TODO написать вывод ошибки о неправильно введенной команде
            data.getChannel().sendMessage("Команда была введена неправильно").queue();
        }
    }



    //Выгнать из клана
    public void addLeave(MessageReceivedEvent event) {
        Data data = new Data(event);
        if (!data.isMentioned()) {
            data.getChannel().sendMessage("Вы забыли упомянуть человека").queue();
            return;
        }

        if ((data.getCommand().length > 3) && (data.getComment().length == 2)){

            general.removeFromList(event, "resources2/audits/lance_holiday.txt");
            general.removeFromList(event, "resources2/audits/lance_recruit.txt");

            List<Role> roles = new ArrayList<>(data.getMentionedMember().getRoles());

            for (int i = 0; i < roles.size(); i++)
                for (String roleN : roleName2)
                    if(roles.get(i).getName().equals(roleN))
                        roles.remove(i);
            data.getController().modifyMemberRoles(data.getMentionedMember(), roles).queue();
            String text = "Вы были исключены из клана и более не имеете возможность использовать тег Lance.\n" +
                            "Исключивший: " + data.getMember().getUser().getAsMention() +"\n" +
                            "Причина: " + data.getComment()[1];
            embed.createMessageKick(data.getLanceAudit(), data.getMember(),"Исключение из клана", "Игрок " + data.getMentionedMember().getUser().getAsMention() + " был исключен из клана\n**Причина:** " + data.getComment()[1]);
            data.getMentionedMember().getUser().openPrivateChannel().queue((channel) ->{
                channel.sendMessage(text).queue();
            });
            data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " был исключен.").queue();
        } else {
            //TODO написать вывод ошибки о неправильно введенной команде
            data.getChannel().sendMessage("Команда была введена неправильно").queue();
        }
    }



    /* Данный метод получает список всех игрок клана по ролям и общее количество игроков в клане. */

    public void getlLanceList(MessageReceivedEvent event){
        String line = "";
        int counter = 0;

        if(event.getMessage().getContentRaw().equals("!список")){
            List<Role> roles = event.getGuild().getRoles();
            for(Role role : roles){
                List<Member> members = event.getGuild().getMembersWithRoles(role, event.getGuild().getRolesByName("lance", true).get(0));
                if (role.getName().equals("Lance")) {
                    counter = members.size();
                    line += "Игроков в клане - `" + counter + "`";
                    break;
                }
                if (members.size() > 0 && !(role.equals(event.getGuild().getRolesByName("lance", true).get(0)))) {
//                    changed role.getName() to lanceRole
                    line += "Роль: " + role.getName() + "\n";
                    counter = 0;
                    for (Member member : members) {
                        counter++;
                        line += counter + ". ";
                        if(member.getNickname() == null){
                            line += member.getUser().getName() + "\n";
                        } else {
                            line += member.getNickname() + "\n";
                        }
                    }
                    line += "\n";
                }
            }
        } else {
            //TODO добавить возможность просмотра по отдельным ролям
        }
        event.getChannel().sendMessage(line).queue();
    }

}