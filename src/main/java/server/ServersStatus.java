package server;

import com.google.gson.Gson;
import general.General;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import server.json.BattlemetricsJSON;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;

public class ServersStatus {

    private OkHttpClient client = new OkHttpClient();

    private String getJSON(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String line = response.body().string();
        response.body().close();
        return line;
    }

    public String[] getServersStatus(String serverID){
        String json = null;
        try{
            json = getJSON("https://api.battleMetrics.com/servers/" + serverID);
        } catch (Exception e){
            General general = new General();
            general.addErrorToAudit("getServersStatus", e);
            e.printStackTrace();
        }

        Gson gson = new Gson();
        BattlemetricsJSON battleM = gson.fromJson(json, BattlemetricsJSON.class);


        return new String[]{
            battleM.getData().getAttributes().getName(),
            battleM.getData().getAttributes().getPlayers(),
            battleM.getData().getAttributes().getMaxPlayers(),
            battleM.getData().getAttributes().getStatus(),
            battleM.getData().getAttributes().getDetails().getMap(),
            battleM.getData().getAttributes().getDetails().getGameMode()
        };


    }

    public MessageEmbed displayTheme(String title, String stat, String players, String map, String mode, int status) {

        EmbedBuilder embed = new EmbedBuilder();
        if(status == 0)
            embed.setColor(Color.red);
        if(status == 1)
            embed.setColor(Color.green);
        if(status == 2)
            embed.setColor(Color.WHITE);
        if (status == 3)
            embed.setColor(Color.orange);

        embed.setTitle(title);
        embed.setDescription("Статус сервера: **" + stat + "**");
        embed.addField("Кол-во Игроков",players + "\n :bust_in_silhouette: ",true);
        embed.addField("Карта", map, true);
        embed.addField("Режим", mode, true);
        embed.setTimestamp(Instant.now());

        return embed.build();

    }



}
