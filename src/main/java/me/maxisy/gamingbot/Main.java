package me.maxisy.gamingbot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Main {
    public static void main(String[] args) {
        String token = Private.TOKEN;

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        api.addMessageCreateListener(event -> {
            if (event.getMessage().getContent().contains("www.facebook.com/groups")) {
                event.getMessage().delete();
                long id = event.getMessage().getAuthor().getId();
                event.getChannel().sendMessage("<@" + id + ">, nie wysylaj wiecej grup z FB!");
            }
            if (event.getMessage().getContent().equalsIgnoreCase("!help-mod")) {
                long id = event.getMessage().getAuthor().getId();
                event.getChannel().sendMessage("<@" + id + "> potrzebuje tutaj <@&724385014233432084>!");
            }
            if (event.getMessage().getContent().equalsIgnoreCase("!help-owner")) {
                long id = event.getMessage().getAuthor().getId();
                event.getChannel().sendMessage("<@" + id + "> potrzebuje tutaj <@&725658664689598504>!");
            }
        });
    }
}
