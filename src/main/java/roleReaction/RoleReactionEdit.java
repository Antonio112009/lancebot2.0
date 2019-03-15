package roleReaction;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.RestAction;

import java.io.*;
import java.util.List;

public class RoleReactionEdit extends ListenerAdapter{

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getUser().isBot()) return;
        String answer = findReactionInFile(event.getChannel().getName(), event.getMessageId() ,event.getReaction().getReactionEmote().getName());
        if(!answer.equals("error")){
            event.getGuild().getController().addSingleRoleToMember(event.getMember(), event.getGuild().getRolesByName(answer,true).get(0)).queue();
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        if (event.getUser().isBot()) return;
        String answer = findReactionInFile(event.getChannel().getName(), event.getMessageId() ,event.getReaction().getReactionEmote().getName());
        if(!answer.equals("error")){
            event.getGuild().getController().removeSingleRoleFromMember(event.getMember(), event.getGuild().getRolesByName(answer,true).get(0)).queue();
        }
    }

    ///TODO создать метод, который будет возвращать true или false, если реация есть в файле или нет
    private String findReactionInFile (String channel, String messageId, String reaction){
        try {
            File filename = new File("resources2/roleReaction.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if((line.split("\\|")[0].equals(channel)) && (line.split("\\|")[1].equals(messageId)) && (line.split("\\|")[2].equals(reaction))){
                    return line.split("\\|")[3];
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return "error";
    }


    /**
     * Добавить/удалить роль
     */

    public void addReactionRole(MessageReceivedEvent event, String content){

        String channel;
        String messageId;
        String reaction;
        String role;

        if(content.split(" ").length != 5){
            event.getChannel().sendMessage("Произошла ошибка при написании команды. Длина строки не равно 5").queue();
            return;
        }

        try {
            channel = event.getMessage().getMentionedChannels().get(0).getName();
            messageId = content.split(" ")[2];
            reaction = content.split(" ")[3];
            role = event.getMessage().getMentionedRoles().get(0).getName();
        } catch (Exception e){
            event.getChannel().sendMessage("Произошла ошибка при написании команды. Возможно вы не указали роль и канал").queue();
            return;
        }

        //Первая проверка сообщения по Id
        if(!isExistMessageId(event, channel, messageId)){
            event.getChannel().sendMessage("Произошла ошибка. Сообщения, по введенным Id сообщения и канала, не существует").queue();
        }

        //Вторая проверка
        String answer = checkReactionsList(event, channel, reaction, role, messageId);
        if(answer.equals("реакция")){
            event.getChannel().sendMessage("Произошла ошибка. Указанная реакция уже привязана к роли").queue();
            return;
        }

        if(answer.equals("роль")){
            event.getChannel().sendMessage("Произошла ошибка. Указанная роль уже привязана к реакции").queue();
            return;
        }

        if (findReactionInFile(channel, messageId, reaction).equals("error")) {
            try {
                File filename = new File("resources2/roleReaction.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(filename,true));
                writer.write(channel + "|" + messageId + "|" + reaction + "|" + role);
                writer.newLine();
                writer.close();
                event.getChannel().sendMessage("Реакция " + reaction + " была успешно присвоина роли " + event.getGuild().getRolesByName(role,true).get(0).getAsMention()).queue();
                for (Message  message: event.getGuild().getTextChannelsByName(channel, true).get(0).getIterableHistory()){
                    if (message.getId().equals(messageId)){
                        message.addReaction(reaction).queue();
                    }
                }
            } catch (Exception e){}
        } else {
            event.getChannel().sendMessage("Произошла ошибка при написании команды или данная реация уже существует").queue();
        }
    }



    public void deleteReactionRole(MessageReceivedEvent event){

        if(event.getMessage().getContentRaw().split(" ").length != 3){
            event.getChannel().sendMessage("Произошла ошибка при написании команды. Длина строки не равно 2").queue();
            return;
        }

        String emojiRole = event.getMessage().getContentRaw().split(" ")[2];
        String messageId = event.getMessage().getContentRaw().split(" ")[1];

        try {
            File filename = new File("resources2/roleReaction.txt");
            File tempFile = new File("resources2/roleReaction_temp.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile, true));
            String line;
            boolean answer = false;
            while ((line = bufferedReader.readLine()) != null) {
                if((line.split("\\|")[2].equals(emojiRole)) || (line.split("\\|")[3].equals(emojiRole))){
                    for (Message  message: event.getGuild().getTextChannelsByName(line.split("\\|")[0], true).get(0).getIterableHistory()){
                        if (message.getId().equals(messageId)){
                            for(MessageReaction messageReaction : message.getReactions())
                                if(messageReaction.getReactionEmote().getName().equals(line.split("\\|")[2]))
                                    messageReaction.removeReaction().queue();
                        }
                    }
                    answer = true;
                    continue;
                }
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
            boolean success = tempFile.renameTo(filename);

            if(answer){
                event.getChannel().sendMessage("Реацкия и прикрепленная роль успешно удалены").queue();
            } else {
                event.getChannel().sendMessage("Реацкия и прикрепленная роль не удалось найти и удалить. Возможно вы ее удалили или не создавали").queue();
            }
        } catch (Exception e){
            event.getChannel().sendMessage("Произошла ошибка. Обратитесь к механику)").queue();
            e.printStackTrace();
        }
    }

    private String checkReactionsList(MessageReceivedEvent event, String channel, String reaction, String role, String messageId){
        try {
            File filename = new File("resources2/roleReaction.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] arrayLine = line.split("\\|");
                if(arrayLine[0].equals(channel) && arrayLine[1].equals(messageId)){
                    if(arrayLine[2].equals(reaction))
                        return "реакция";
                    if(arrayLine[3].equals(role))
                        return "роль";
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return "нет";
    }

    private boolean isExistMessageId(MessageReceivedEvent event, String channel, String messageId){
        for (Message  message: event.getGuild().getTextChannelsByName(channel, true).get(0).getIterableHistory()){
            if (message.getId().equals(messageId)){
                return true;
            }
        }
        return false;
    }
}
