package general;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PublicCommands {

    private String minomet_text = "" +
            "При стрельбе из миномета в игре, мы настоятельно просим " +
            "пользоваться онлайн калькулятором. Лучший из них:\n" +
            "https://squadmc.ende.pro\n\n";

    private String mapKey_text = "" +
            "Для удобства просмотра мини-карты, мы настоятельно рекомендуем поставить " +
            "данную функцию на кнопку **CapsLock** или любую другую удобную вам клавишу";

    public void help(MessageReceivedEvent event){
        String text = "" +
                "Публичные команды:\n" +
                "`!миномет` - информация по миномету\n" +
                "`!карта` - информация по мини-карте\n";
        event.getChannel().sendMessage(text).queue();
    }


    public void minomet(MessageChannel channel){
        channel.sendMessage(minomet_text).queue();
    }


    public void mapKey(MessageChannel channel){
        channel.sendMessage(mapKey_text).queue();
    }

    public void mapPlusMinomet(MessageChannel channel, JDA api){
        channel.sendMessage(api.getRolesByName("Lance", true).get(0).getAsMention()+"\n" + mapKey_text + "\n" + minomet_text).queue();
    }



}
