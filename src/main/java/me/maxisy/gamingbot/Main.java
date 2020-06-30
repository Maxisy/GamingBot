package me.maxisy.gamingbot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;

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
            if (event.getMessage().getContent().contains("!pochwal")) {
                String content = event.getMessage().getContent();
                if (event.getMessage().getAuthor().isServerAdmin()) {
                    if (content.contains(" ")) {
                        String[] split = content.split(" ");
                        sendPochwalonoMessage(split, api);
                        event.getMessage().getChannel().sendMessage("Sukces, wejdz na <#727587187306266755>");
                    }
                } else {
                    event.getMessage().getChannel().sendMessage("Nie masz uprawnien!");
                }
            }
            if (event.getMessage().getContent().contains("kto gra")) {
                if (!event.getMessage().getChannel().getIdAsString().equals("718571030154707074")) {
                    event.getMessage().delete();
                    event.getMessage().getChannel().sendMessage("Zly kanal, wejdz na <#718571030154707074>");
                }
            }
        });

    }

    public static void sendPochwalonoMessage(String[] split, DiscordApi api) {
        api.getTextChannelById("727587187306266755").ifPresent(textChannel -> {
            textChannel.sendMessage(" > Pochwalono " + split[1]);
        });
    }
}
