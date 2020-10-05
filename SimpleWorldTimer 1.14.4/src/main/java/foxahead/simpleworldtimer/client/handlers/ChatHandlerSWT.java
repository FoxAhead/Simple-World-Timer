package foxahead.simpleworldtimer.client.handlers;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ChatHandlerSWT {

  @SubscribeEvent
  public void onClientChatReceived(ClientChatReceivedEvent event) {
    //String message = (event.getMessage().getFormattedText());
    //System.out.println("Message" + message);
    /* if (message.contains(search.get()) && !message.startsWith(MC.getSession().getUsername() )) {
         String append;
         switch (mode.get().toUpperCase()) {
             case "REPLY":
                 append = "/w ";
                 break;
             case "CHAT":
             default:
                 append = Strings.EMPTY;
                 break;
         }
         getLocalPlayer().sendChatMessage(append + reply.get());
     }*/
  }
}
