package general;

import config.BotConfig;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class General {

    private String fileTemp = "resources2/audits/lance_temp.txt";
    private String fileError = "resources2/audits/lance_errors.txt";

    //Помощь
    public void help(MessageChannel channel, String version) {

        channel.sendMessage("Команды для бота: \n\n" +

                        "Версия бота - " + version +
                        "\nВерсия документа - 2.0+" +

                        "\n\nПомощь:\n" +
                        "`!помощь` - посмотреть команды для данного Бота\n\n" +

                        "Добавить/изменить игроку роль:\n" +
                        "`!рекрут @НИКНЕЙМ` - добавить игрока в клан. Бот вешает соответствующие теги, делает запись в аудите.\n __**Данная фунция работает только в чате #hall**__\n" +
                        "`!рекрут список` - показать список рекрутов, их даты начала рекрутства и сколько дней до окончания\n" +
                        "`!рекрут_add @НИКНЕЙМ N ++ ПРИЧИНА` - продлить игроку роль Рекрута в клане. Бот добавляет дни в свой аудит, делает запись в аудите. N - `N-нед`, `N-мес`, `ДД-ММ-ГГ`\n" +
                        "`!основа @НИКНЕЙМ ++ ПРИЧИНА` - изменить текущую роль игрока на \"Основной состав\". Бот вешает соответствующие теги, делает запись в аудите\n" +
                        "`!сержант @НИКНЕЙМ ++ ПРИЧИНА` - изменить текущую роль игрока на \"Сержант\". Бот вешает соответствующие теги, делает запись в аудите\n" +
                        "`!офицер @НИКНЕЙМ ++ ПРИЧИНА` - изменить текущую роль игрока на \"Офицер\". Бот вешает соответствующие теги, делает запись в аудите\n\n" +

                        "Перевести игрока в запас/убрать из запаса\n" +
                        "`!запас список` - показать список запасников, их даты начала запаса и сколько дней до окончания\n" +
                        "`!запас @НИКНЕЙМ` - бот смотрит есть ли игрок в запасе или нет. Если есть, то говорит дату окончание запаса\n" +
                        "`!запас @НИКНЕЙМ N ++ ПРИЧИНА` - переводит игрока в запас. Бот вешает соответствующие теги, делает запись в аудите. N - `N-нед`, `N-мес`, `ДД-ММ-ГГ`\n" +
                        "`!запас_add @НИКНЕЙМ N ++ ПРИЧИНА` - продлить срок запаса. N - `N-нед`, `N-мес`, `ДД-ММ-ГГ`.\n" +
                        "`!запас_нет @НИКНЕЙМ` - выведение игрока из запаса. Бот убирает роль \"В запасе\", делает запись в аудите\n\n" +

                        "Убрать человека из клана:\n" +
                        "`!исключить @НИКНЕЙМ ++ ПРИЧИНА` - исключает человека из клана. Бот убирает все роли с игрока, делает запись в аудите\n\n" +

                        "Сервер **Lance Training Range:**\n" +
                        "`!пароль ПАРОЛЬ_НОВЫЙ` - изменяет нынешний пароль на новый `временный пароль` и сообщает от этом в #lance_news\n"


//                + "В Разработке:\n\n" +
//                "Тренировки:\n" +
//                "`!тренировка_add ДД-ММ-ГГГГ ЧЧ:ММ @НИКНЕЙМ @НИКНЕЙМ2 ... @НИКНЕЙМ100` - создает напоминание о предстоящей тренировке. Бот делает запись в training range, в **:00 по мск напоминает о тренировке, " +
//                "а также за 1 час и 10 минут, отправляя оповещание в lance_chat. После отправки данной команды, Бот предлагает выбрать название к напоминанию из списка доступных названий или написать свое название\n" +
//                "`!запас_add @НИКНЕЙМ N \"ПРИЧИНА\"` - продлить срок запаса. N - Число дней[цифрами].\n" +
//                "\nПланируется создать напоминалку (одно из) для командного состава, который заключается в уведомлении о том, что человек превысил срок проведенный в запасе дней. Командный состав должен решить: " +
//                "продлить срок запаса или исключить игрока из клана.\n"

        ).queue();
        channel.sendMessage("`!пароль дефолт` - возвращает пароль к дефолтному. Сообщает об этом в #lance_news\n\n" +
                "Работа в #lance_bot:\n" +
                "`!версия` - посмотреть версию бота\n\n" +
                "Общие команды:\n" +
                "`!talk НИКНЕЙМ` - создает временные приватный текстовой и голосовой чат для общения с `НИКНЕЙМ`. Удобная вещь при добавлении новых рекрутов.\n" +
                "`!close` - удаляет созданные временный приватный текстовой и голосовой чат. Прописывается команда только в текстовом привытном чате, который хотите завершить.\n\n" +
                "**НОВОВЕДЕНИЕ:**\n"+
                "Теперь при добавлении даты не пишем больше дней (прим. - `!запас НИКНЕЙМ 14 ++ ПРИЧИНА`),  а теперь пишем одну из трех вещей:\n" +
                "- Недели (N-нед): `!запас НИКНЕЙМ 2-нед ++ ПРИЧИНА`\n" +
                "- Месяцы (N-мес): `!запас НИКНЕЙМ 3-мес ++ ПРИЧИНА`\n" +
                "- Дату (ДД-ММ-ГГ): `!запас НИКНЕЙМ 21-03-19 ++ ПРИЧИНА`\n\n" +
                "Данное нововедение также относится к командам **продление запаса и рекрутства!**"

        ).queue();
    }



    public void deleteMessages(MessageReceivedEvent event, MessageChannel channel, String[] command) {
        if (command.length == 2 && (event.getAuthor().getId().equals(BotConfig.SPECIAL_ID) || event.getAuthor().getId().equals(BotConfig.KAYANN_ID))) {
            int i = 0;
            int until = Integer.parseInt(command[1]) + 1;
            System.out.println("Удалить сообщений: " + until);
            for (Message mes : channel.getIterableHistory()) {
                if (i == until) break;
                try {
                    channel.deleteMessageById(mes.getId()).queue();
                } catch (Exception e) {
                    System.out.println("Проблема в deleteMessage-method");
                    e.printStackTrace();
                }
                i++;
            }
            System.out.println("done");
        }
    }

    public void napalmMessages(MessageReceivedEvent event){
        if (event.getAuthor().getId().equals(BotConfig.KAYANN_ID)){
            for (Message mes : event.getChannel().getIterableHistory()) {
                try {
                    event.getChannel().deleteMessageById(mes.getId()).queue();
                } catch (Exception e) {
                    System.out.println("Проблема в deleteMessage-method");
                    e.printStackTrace();
                }
            }
        }
    }

    public String getDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate today = LocalDate.now();

        return dataManipulation(today, date, formatter);
    }



    private String sumDates(String initDate, String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate firstDate = LocalDate.parse(initDate, formatter);

        return dataManipulation(firstDate, date, formatter);
    }



    private String dataManipulation(LocalDate firstDate, String date, DateTimeFormatter formatter) {
        String[] dateSplit;
        try {
            if (date.split("-").length == 2) {

                dateSplit = date.split("-");

                if (dateSplit[1].equals("нед")) {
                    LocalDate addWeeks = firstDate.plus(Integer.valueOf(dateSplit[0]), ChronoUnit.WEEKS);
                    return addWeeks.format(formatter);
                } else if (date.split("-")[1].equals("мес")) {
                    LocalDate addMonths = firstDate.plus(Integer.valueOf(dateSplit[0]), ChronoUnit.MONTHS);
                    return addMonths.format(formatter);
                }
                return "error";
            } else if (date.split("-").length == 3) {
                try {
                    LocalDate nextDate = LocalDate.parse(date, formatter);
                    if (nextDate.compareTo(firstDate) > 0)
                        return nextDate.format(formatter);
                    return "error";
                } catch (Exception e) {
                    return "error";
                }
            }
            return "error";
        } catch (Exception e) {
            System.out.println("ошибка в getDate");
            e.printStackTrace();
            addErrorToAudit(date, e);
            return "error";
        }
    }


    //Добавить человека в файл
    public boolean addPerson(String filename, String mentionedID, String date, String... additional) {
        if(date.equals("error"))
            return false;
        try {
            FileWriter out = new FileWriter(filename, true);
            BufferedWriter bufferWriter = new BufferedWriter(out);
            if (additional.length == 0) {
                bufferWriter.write(mentionedID + "|" + date);
            } else {
                String add = "";
                for (String item : additional)
                    add += "|" + item;
                bufferWriter.write(mentionedID + "|" + date + add);
            }
            bufferWriter.newLine();
            bufferWriter.close();
            return true;
        } catch (Exception e) {
            System.out.println("Проблема в addPerson");
            e.printStackTrace();
            addErrorToAudit(filename + " " + mentionedID + " " + date, e);
            return false;
        }
    }


    //редактировать дату в файлах
    public boolean addDateToPerson(String filename, String mentionedID, String date) {

        boolean output = true;
        try {

            File inputFile = new File(filename);
            File tempFile = new File(fileTemp);

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if (trimmedLine.startsWith(mentionedID)) {


                    //TODO перепроверить все варианты исходных событий

                    String[] lineArray = trimmedLine.split("\\|");
                    String result = sumDates(lineArray[1], date);
                    if (result.equals("error")) {
                        output = false;
                    } else {
                        currentLine = lineArray[0];
                        for (int i = 1; i < lineArray.length; i++) {

                            if (i == 1) {
                                currentLine += "|" + result;
                            } else {
                                currentLine += "|" + lineArray[i];
                            }
                        }
                        output = true;
                    }


                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(inputFile);
            return output;
        } catch (Exception e) {
            //TODO записать ошибку в аудит ошибок
            System.out.println("Проблема в addDaysRecruit-методе");
            e.printStackTrace();
            addErrorToAudit(filename + " " + mentionedID + " " + date, e);
            return false;
        }
    }


    public String showList(MessageReceivedEvent event, String filename, String roleType) {
        //FIXME переименовать пути к файлам!

        try {
            Data data = new Data(event);
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String answer = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] arrayLine = line.split("\\|");
                try {
                    if(event.getJDA().awaitReady().getGuilds().get(0).getMemberById(arrayLine[0]).getNickname() != null) {
                        //event.getJDA().awaitReady().getUserById(arrayLine[0]).getName()
                        answer += "Игрок: **" + event.getJDA().awaitReady().getGuilds().get(0).getMemberById(arrayLine[0]).getNickname() +"**";
                    } else {
                        answer += "Игрок: **" + event.getJDA().awaitReady().getUserById(arrayLine[0]).getName() +"**";
                    }
                    answer += "\nОсталось дней: " + checkDaysLeft(arrayLine[0], filename);
                    if(arrayLine.length > 2){
                        answer += "\nОсновная роль: " + arrayLine[2];
                    }
                    answer += "\n\n";
                } catch (Exception e){
                }
            }
            //TODO перепроверить вывод пустого поля на экран
            if (answer.equals("")) {
                if (roleType.equals("Рекрут"))
                    return "На данный момент рекрутов в клане нет";
                if (roleType.equals("Запас"))
                    return "На данный момент запасников в клане нет";
            }
            return answer;
        } catch (Exception e) {
            //TODO записать
            System.out.println("showList - wrong things happening");
            e.printStackTrace();
            addErrorToAudit(event.getMessage().getContentRaw(), e);
        }
        return "Ошибка в коде";
    }

    public void removeFromList(MessageReceivedEvent event, String filename) {
        try {

            Data data = new Data(event);

            File inputFile = new File(filename);
            File tempFile = new File(fileTemp);

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if (trimmedLine.startsWith(data.getMentionedMember().getUser().getId())) {
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(inputFile);
        } catch (Exception e) {
            //TODO улучшить запись ошибки в аудит
            System.out.println("Проблема в removeFromList-методе");
            e.printStackTrace();
            addErrorToAudit(event.getMessage().getContentRaw(), e);
        }
    }


    public void addErrorToAudit(String command, Exception e) {
        try {
            FileWriter out = new FileWriter(fileError, true);
            BufferedWriter bufferWriter = new BufferedWriter(out);
            String add = "";
            LocalTime localTime = LocalTime.now();
            add += localTime.format(DateTimeFormatter.ofPattern("HH:mm")) + "\n";
            add += "Команда:" + command + "\n";

            for (StackTraceElement error : e.getStackTrace())
                add += error.toString() + "\n";
            add += "\n";
            bufferWriter.write(add);
            bufferWriter.newLine();
            bufferWriter.close();
        } catch (Exception exp) {
            System.out.println("Проблема в addErrorToAudit");
            exp.printStackTrace();
        }
    }




    public int checkDaysLeft(String mentionedId, String filename) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate today = LocalDate.now();

        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String dateString = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] arrayLine = line.split("\\|");
                if (arrayLine[0].equals(mentionedId)) {
                    dateString = arrayLine[1];
                    break;
                }
            }
            LocalDate dateInFile = LocalDate.parse(dateString, formatter);
            long daysLeft = ChronoUnit.DAYS.between(today, dateInFile);
            return (int) daysLeft;
        } catch (Exception e) {
            System.out.println("Проблема в checkDaysLeft-методе");
            e.printStackTrace();
            addErrorToAudit(filename, e);
        }
        return 0;
    }

    public String getLastDay (String mentionedId, String filename){
        try {

            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] arrayLine = line.split("\\|");
                if (arrayLine[0].equals(mentionedId)) {
                    return arrayLine[1];
                }
            }
        } catch (Exception e) {
            System.out.println("Проблема в getLastDay-методе");
            e.printStackTrace();
            addErrorToAudit(filename, e);
        }
        return "error";
    }



    public String getInfoConfig(String confline){
        try {
            FileReader fileReader = new FileReader("resources2/lancebot.conf.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] arrayLine = line.split("=");
                if (arrayLine[0].equals(confline))
                    return arrayLine[1];
            }
        } catch (Exception e) {
            System.out.println("Проблема в checkDaysLeft-методе");
            e.printStackTrace();
            addErrorToAudit("getInfoConfig", e);
        }

        return null;
    }

    public void season(MessageReceivedEvent event, String answer){

        boolean equal = false;
        Data data = new Data(event);
        try {
            equal = lanceConfig("seasonClose", answer);
            if(equal){
                if(answer.equals("true")) {
                    data.getChannel().sendMessage("Сезон уже закрыт").queue();
                    return;
                } else {
                    data.getChannel().sendMessage("Сезон уже открыт").queue();
                    return;
                }
            }

            if(answer.equals("true")){
                data.getChannel().sendMessage("Нынешний сезон дла клана закрывется.").queue();
            } else {
                data.getChannel().sendMessage("Новый сезон дла клана открыт!").queue();
            }

        } catch (Exception e) {
            //TODO записать ошибку в аудит ошибок
            System.out.println("Проблема в Season-методе");
            e.printStackTrace();
            addErrorToAudit(event.getMessage().getContentRaw(), e);
        }
    }

    public boolean lanceConfig(String lineConfig, String answer){

        boolean equal = false;

        try {
            File inputFile = new File("resources2/lancebot.conf.txt");
            File tempFile = new File("resources2/lancebot_temp.conf.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();

                if(trimmedLine.startsWith(lineConfig)){
                    if(trimmedLine.split("=")[1].equals(answer)){
                        equal = true;
                        writer.write(currentLine + System.getProperty("line.separator"));
                    } else {
                        writer.write(lineConfig + "=" + answer + System.getProperty("line.separator"));
                    }
                } else {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }
            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(inputFile);

        } catch (Exception e){
            System.out.println("Проблема в lanceConfig-методе");
            e.printStackTrace();
            addErrorToAudit("прост", e);

        }

        return equal;
    }


    //Аудит
    public void auditInfo(MessageReceivedEvent event){
         String[] arrayLine = event.getMessage().getContentRaw().split("\\+\\+");
         String description = arrayLine[1];
         String title = arrayLine[0].substring(7);
         EmbedCreator embed = new EmbedCreator();
         embed.createMessageNotification(event.getGuild().getTextChannelById(BotConfig.AUDIT_CHANNEL), event.getMember(), title, description);
    }


    public void information(MessageReceivedEvent event){
//        String text = "О"
    }



}
