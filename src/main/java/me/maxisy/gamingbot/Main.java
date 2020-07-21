package me.maxisy.gamingbot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Main {
    public static final String fileName = "pochwaly.txt";

    static Map<String, Integer> pochwaly = new HashMap<>();

    public static void main(String[] args) {
        String token = Private.TOKEN;
        File file = new File(fileName);



        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        api.addMessageCreateListener(event -> {
            if (event.getMessage().getContent().contains("www.facebook.com/groups")) {
                event.getMessage().delete();
                long id = event.getMessage().getAuthor().getId();
                event.getChannel().sendMessage("> <@" + id + ">, nie wysyłaj więcej grup z FB!");
            }
            if (event.getMessage().getContent().equalsIgnoreCase("!help-mod")) {
                long id = event.getMessage().getAuthor().getId();
                event.getChannel().sendMessage("> <@" + id + "> potrzebuje tutaj <@&724385014233432084>!");
            }
            if (event.getMessage().getContent().equalsIgnoreCase("!help-owner")) {
                long id = event.getMessage().getAuthor().getId();
                event.getChannel().sendMessage("> <@" + id + "> potrzebuje tutaj <@&725658664689598504>!");
            }
            if (event.getMessage().getContent().contains("!pochwal")) {
                String content = event.getMessage().getContent();
                if (event.getMessage().getAuthor().isServerAdmin()) {
                    if (content.contains(" ")) {
                        try {
                            String[] split = content.split(" ");
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            pochwal(split[1]);
                            event.getMessage().getChannel().sendMessage("> Sukces.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    event.getMessage().getChannel().sendMessage("> Nie masz uprawnień!");
                }
            }
            if (event.getMessage().getContent().contains("kto gra")) {
                if (!event.getMessage().getChannel().getIdAsString().equals("718571030154707074")) {
                    event.getMessage().delete();
                    event.getMessage().getChannel().sendMessage("> Zły kanał, wejdź na <#718571030154707074>");
                }
            }
            if (event.getMessage().getContent().contains("!py")) {
                if (!file.exists()) {
                    event.getMessage().getChannel().sendMessage("> Nie ma żadnych pochwał!");
                } else {
                    event.getMessage().getChannel().sendMessage("> Pochwały:");
                    for (Map.Entry<String, Integer> entry : pochwaly.entrySet()) {
                        String key = entry.getKey();
                        int value = entry.getValue();
                        event.getMessage().getChannel().sendMessage("> " + key + ": " + value);
                    }
                }
            }
            if (event.getMessage().getContent().contains("!cp")) {
                if (event.getMessage().getAuthor().isServerAdmin()) {
                    if (event.getMessage().getContent().contains(" ")) {
                        try {
                            String[] splitted = event.getMessage().getContent().split(" ");
                            File temp = File.createTempFile("file", ".txt", file.getParentFile());
                            String delete = splitted[1];
                            String charset = "UTF-8";
                            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
                            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
                            for (String line; (line = reader.readLine()) != null;) {
                                line = line.replace(delete, "");
                                writer.println(line);
                            }
                            reader.close();
                            writer.close();
                            file.delete();
                            temp.renameTo(file);
                            pochwaly.remove(delete);
                            event.getMessage().getChannel().sendMessage("> Sukces.");
                        } catch (IOException e) {
                            event.getChannel().sendMessage(e.getMessage());
                        }
                    }
                } else {
                    event.getMessage().getChannel().sendMessage("> Nie masz uprawnień!");
                }
            }
            if (event.getMessage().getContent().equalsIgnoreCase("!cpall")) {
                if (event.getMessage().getAuthor().isServerAdmin()) {
                    file.delete();
                    pochwaly.clear();
                    event.getChannel().sendMessage("> Sukces.");
                } else {
                    event.getChannel().sendMessage("> Nie masz uprawnień!");
                }
            }
            if (event.getMessage().getContent().contains("!powitaj")) {
                if (event.getMessage().getContent().contains(" ")) {
                    if (event.getMessage().getAuthor().isServerAdmin()) {
                        String[] split = event.getMessage().getContent().split(" ");
                        event.getMessage().getChannel().sendMessage("Witaj na serwerze " + split[1] + "! " +
                                "Zapoznaj się z <#718563898021773433>, dodaj sobie role na <#718500968408285284>," +
                                " możesz także przeczytać <#718510269898162238> jeśli chcesz.");
                    } else {
                        event.getMessage().getChannel().sendMessage("Nie masz uprawnień!");
                    }
                }
            }
        });

    }

    public static void pochwal(String u) {
        try (
                FileWriter fw = new FileWriter(fileName, true);
                BufferedWriter writer = new BufferedWriter(fw);
        ) {
            writer.write(u);
            writer.newLine();
            pochwaly.put(u, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (
                FileReader fr = new FileReader(fileName);
                BufferedReader reader = new BufferedReader(fr)
        ) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.equals(u)) {
                    int ps = pochwaly.get(u);
                    pochwaly.put(u, ++ps);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
