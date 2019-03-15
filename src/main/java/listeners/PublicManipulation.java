package listeners;

import general.PublicCommands;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PublicManipulation extends ListenerAdapter {

  private PublicCommands publics = new PublicCommands();

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {

    if (event.getAuthor().isBot()) return;


    if(event.getMessage().getContentRaw().toLowerCase().equals("!пом")){
      publics.help(event);
    }

    else if(event.getMessage().getContentRaw().toLowerCase().equals("!миномет")){
      publics.minomet(event.getChannel());
    }

     else if(event.getMessage().getContentRaw().toLowerCase().equals("!карта")){
      publics.mapKey(event.getChannel());
    }


  }


}
