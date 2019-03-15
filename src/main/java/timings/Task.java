package timings;

import general.EmbedCreator;
import general.General;
import general.PublicCommands;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;
import server.ServersStatus;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

class Task{

    private JDA api;
    private TextChannel news;
    private TextChannel bot;
    private TextChannel officers;
    private String fileRecruit = "resources2/audits/lance_recruit.txt";
    private String fileHoliday = "resources2/audits/lance_holiday.txt";

    private General general = new General();
    private EmbedCreator embed = new EmbedCreator();
    private ServersStatus status = new ServersStatus();
    private PublicCommands publics = new PublicCommands();


    public Task(JDA api){
        this.api = api;
        try {
            officers = api.awaitReady().getTextChannelsByName("lance_officer", true).get(0);
            bot = api.awaitReady().getGuilds().get(0).getTextChannelsByName("lance_bot",true).get(0);
            news = api.awaitReady().getGuilds().get(0).getTextChannelsByName("lance_news", true).get(0);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    void taskHoliday() {
        ArrayList<String> list = getDataFromFile(fileHoliday);
        String addPerson = "";
        if (list == null)
            return;
        try {
            if (list.size() != 0) {
                embed.createMessageNotification(officers, api.getGuilds().get(0).getSelfMember(), "Истек срок запаса",
                        "Срок запаса у представленных ниже игроков подошел к концу.\n" +
                                "Рассмотрите вопрос о продлении запаса или исключении из клана.");
                for (String person : list)
                    addPerson += person;
                officers.sendMessage(addPerson).queue();
            }
        } catch (Exception e){
            System.out.println("Произошла ошибка в taskHoliday-методе");
            e.printStackTrace();
            general.addErrorToAudit("taskHoliday", e);
        }
    }




    void taskRecruit(){
        ArrayList<String> list = getDataFromFile(fileRecruit);
        if (list == null)
            return;
        String addPerson = "";
        try {
            if (list.size() != 0) {
                embed.createMessageNotification(officers, api.getGuilds().get(0).getSelfMember(), "Истек срок рекрутства",
                        "Срок рекрутства у представленных ниже игроков подошел к концу.\n" +
                                "Рассмотрите вопрос о переводе в основной состав, продлении рекрутства или исключении из клана.");
                for (String person : list)
                    addPerson += person;
                officers.sendMessage(addPerson).queue();
            }
        } catch (Exception e){
            System.out.println("Произошла ошибка в taskRecruit-методе");
            e.printStackTrace();
            general.addErrorToAudit("taskRecruit", e);
        }
    }




    void addDaysRecruit(){
        if (general.getInfoConfig("seasonClose").equals("false")) {
//            System.out.println("false");
            return;
        }
        try {
            File inputFile = new File(fileRecruit);
            File outputFile = new File("lance.txt");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] lineArray = currentLine.split("\\|");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
                LocalDate increased = LocalDate.parse(lineArray[1], formatter).plusDays(1L);
                lineArray[1] = increased.format(formatter);
                bufferedWriter.write(lineArray[0] + "|" + lineArray[1] + System.getProperty("line.separator"));
            }
            bufferedReader.close();
            bufferedWriter.close();
            boolean successful = outputFile.renameTo(inputFile);

        } catch (Exception e){
            System.out.println("Произошла ошибка в addDaysRecruit-методе");
            e.printStackTrace();
            general.addErrorToAudit("addDaysRecruit", e);
        }
    }




    void eraseLanceBot() {
        if(LocalTime.now().getHour() == 0 && LocalTime.now().getMinute() == 0) {
            System.out.println("Очистка чата");
            for (Message mes : bot.getIterableHistory()) {
                try {
                    bot.deleteMessageById(mes.getId()).queue();
                } catch (Exception e) {
                    e.printStackTrace();
                    general.addErrorToAudit("проблема в eraseLanceBot", e);
                }
            }
            bot.sendMessage("Чат очищен").queue();
            System.out.println("Чат очищен");
        }
    }




    void serverStatus() {

        if (general.getInfoConfig("serverStatus").equals("false")) {
            return;
        }

        String[] serverList = {
                "2093385",  // РОССИЯ #1
                "2782830", // РОССИЯ #2
                "2972657", // SW Server
                "1986412", // TGO Россия
                "603530",  // Mumblerines
                "2028012", // [G]Guardians
                "542530",  // Germany 1
                "1972911", // Germany 2
                "2484943", // Germany 3
        };

        TextChannel serverChannel = api.getGuilds().get(0).getTextChannelsByName("статус-серверов", true).get(0);

        try {
            MessageHistory history = new MessageHistory(serverChannel);
            List<Message> msgs = history.retrievePast(serverList.length * 2).complete();
            try {
                serverChannel.deleteMessages(msgs).complete();
            } catch (Exception e){
                e.printStackTrace();
            }

            for (String serverID : serverList) {

                String[] array = status.getServersStatus(serverID);
                int people = Integer.valueOf(array[2]) - Integer.valueOf(array[1]);

                if (array[3].equals("dead") || array[3].equals("offline"))
                    System.out.print("");
//                    serverChannel.sendMessage(status.displayTheme(array[0], array[3], array[1] + "/" + array[2], array[4], array[5], 0)).queue();
                else if (people <= 7)
                    serverChannel.sendMessage(status.displayTheme(array[0], array[3], array[1] + "/" + array[2], array[4], array[5], 3)).queue();
                else if (people <= 35)
                    serverChannel.sendMessage(status.displayTheme(array[0], array[3], array[1] + "/" + array[2], array[4], array[5], 1)).queue();
                else
                    serverChannel.sendMessage(status.displayTheme(array[0], array[3], array[1] + "/" + array[2], array[4], array[5], 2)).queue();
            }

        } catch (Exception e) {
            System.out.println("ПРОБЛЕМА В SERVER STATUS(");
            e.printStackTrace();
            general.addErrorToAudit("Server status", e);
        }

    }

    void notificationTactics(){
        if(LocalDate.now().getDayOfWeek().name().toLowerCase().equals("wednesday"))
            if((LocalTime.now().getHour() == 15 && LocalTime.now().getMinute() == 0) || (LocalTime.now().getHour() == 19 && LocalTime.now().getMinute() == 0)) {
                publics.mapPlusMinomet(api.getTextChannelById("417389837306298368"), api);
            }
    }












    private ArrayList<String> getDataFromFile(String filename){
        if (general.getInfoConfig("seasonClose").equals("true")) {
            return null;
        }
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            ArrayList<String> addPerson = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] arrayLine = line.split("\\|");
                if(general.checkDaysLeft(arrayLine[0],filename) <= 0){
                    addPerson.add("<@" + arrayLine[0] + ">\n");
                }
            }
            return addPerson;
        } catch (Exception e){
            System.out.println("Произошла ошибка в getDataFromFile");
            e.printStackTrace();
            general.addErrorToAudit("getDataFromFile", e);
        }
        return null;
    }
}