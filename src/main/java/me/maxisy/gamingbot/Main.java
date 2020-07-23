package me.maxisy.gamingbot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Main {
    public static final String fileName = "pochwaly.txt";

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
                            if (split[0].equalsIgnoreCase("!pochwal")) {
                                if (!file.exists()) {
                                    file.createNewFile();
                                }
                                pochwal(split[1]);
                                event.getMessage().getChannel().sendMessage("> Sukces.");
                            }
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
            if (event.getMessage().getContent().equalsIgnoreCase("!py")) {
                if (!file.exists()) {
                    event.getMessage().getChannel().sendMessage("> Nie ma żadnych pochwał!");
                } else {
                    try (
                            BufferedReader reader = new BufferedReader(new FileReader(fileName));
                    ) {
                        String line = null;
                        event.getChannel().sendMessage("> Pochwały:");
                        int i = 0;
                        Map<String, Integer> ps = new HashMap<>();
                        while ((line = reader.readLine()) != null) {
                            if (line.equalsIgnoreCase("")) {
                            } else if (!ps.containsKey(line)) {
                                ps.put(line, 1);
                            } else {
                                i = ps.get(line) + 1;
                                ps.put(line, i);
                            }
                        }
                        Set<Map.Entry<String, Integer>> entries = ps.entrySet();
                        for (Map.Entry<String, Integer> entry : entries) {
                            event.getChannel().sendMessage(entry.getKey() + ": " + entry.getValue());
                        }
                        ps.clear();
                    } catch (IOException e) {
                        event.getChannel().sendMessage("Wystąpił błąd: " + e.getMessage());
                    }
                }
            }
            if (event.getMessage().getContent().contains("!cp")) {
                if (event.getMessage().getAuthor().isServerAdmin()) {
                    if (event.getMessage().getContent().contains(" ")) {
                        try {
                            String[] splitted = event.getMessage().getContent().split(" ");
                            if (splitted[0].equalsIgnoreCase("!cp")) {
                                File temp = File.createTempFile("file", ".txt", file.getParentFile());
                                String delete = splitted[1];
                                String charset = "UTF-8";
                                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
                                PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
                                for (String line; (line = reader.readLine()) != null; ) {
                                    line = line.replace(delete, "");
                                    writer.println(line);
                                }
                                reader.close();
                                writer.close();
                                file.delete();
                                temp.renameTo(file);
                                event.getMessage().getChannel().sendMessage("> Sukces.");
                            }
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
            if (event.getMessage().getContent().contains("!zatwierdz")) {
                if (event.getMessage().getContent().contains(" ")) {
                    if (event.getMessage().getAuthor().isServerAdmin()) {
                        String[] split = event.getMessage().getContent().split(" ");
                        if (split[0].equalsIgnoreCase("!zatwierdz")) {
                            String uId = split[1].replaceAll("<", "").
                                    replaceAll(">", "").
                                    replaceAll("@", "");
                            uId = uId.replaceAll("!", "").replaceAll("&", "");
                            event.getServer().get().addRoleToUser(event.getServer().get().getMemberById(uId).get(),
                                    event.getServer().get().getRoleById("718759264591544391").get());
                            event.getServer().get().removeRoleFromUser(event.getServer().get().getMemberById(uId).get(),
                                    event.getServer().get().getRoleById("718956119502094348").get());
                            event.getChannel().sendMessage("> Sukces.");
                        }
                    } else {
                        event.getChannel().sendMessage("> Nie masz uprawnień!");
                    }
                }

            }
            if (event.getMessage().getContent().contains("!komendy")) {
                String[] split = event.getMessage().getContent().split(" ");
                if (split[0].equalsIgnoreCase("!komendy")) {
                    if (split.length == 2) {
                        if (split[1].equalsIgnoreCase("admin")) {
                            new MessageBuilder().setEmbed(new EmbedBuilder().
                                    setTitle("Komendy administratorskie:").
                                    setDescription("`!pochwal @user` - pochwala użytkownika;\n" +
                                            "`!cp @user` - usuwa pochwałę użytkownika;\n" +
                                            "`!cpall` - usuwa wszystkie pochwały;\n" +
                                            "`!powitaj @user` - wita użytkownika;\n" +
                                            "`!zatwierdz @user` - zatwierdza użytkownika.").
                                    setColor(Color.ORANGE)).
                                    send(event.getMessage().getChannel());
                        }
                    } else {
                        new MessageBuilder().setEmbed(new EmbedBuilder().
                                setTitle("Komendy:").
                                setDescription("`!help-mod` - wzywa Moderatora;\n" +
                                        "`!help-owner` - wzywa Właściciela;\n" +
                                        "`!py` - wyświetla pochwały;").
                                setColor(Color.ORANGE)).send(event.getMessage().getChannel());
                    }

                }
            }
        });

        api.addServerMemberJoinListener(event -> {
            Role role = event.getServer().getRoleById("718956119502094348").get();
            event.getUser().addRole(role);
        });

    }

    public static void pochwal(String u) {
        try (
                FileWriter fw = new FileWriter(fileName, true);
                BufferedWriter writer = new BufferedWriter(fw);
        ) {
            writer.write(u);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
