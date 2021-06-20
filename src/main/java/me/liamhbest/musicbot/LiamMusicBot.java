package me.liamhbest.musicbot;

import me.liamhbest.musicbot.commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.*;

public class LiamMusicBot {

    private static LiamMusicBot INSTANCE;

    public static JDA jda;
    private static final String token = "ODU1NzYyNzQxMjg1NzQ4NzQ2.YM3NKA.wfw4rMY6JcLcrsoHQFuZqbWbhnE";

    public static void main(String[] args) {

        try {
            jda = JDABuilder.createDefault(token)
                    .setActivity(Activity.playing("Music"))
                    .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
                    .enableCache(CacheFlag.VOICE_STATE)
                    .build();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        System.out.println("The bot has now been loaded and is active.");

        registerDiscordListeners();
    }

    public static void registerDiscordListeners(){
        jda.addEventListener(new StartCommand());
        jda.addEventListener(new StopCommand());
        jda.addEventListener(new PlayCommand());
        jda.addEventListener(new VolumeCommand());
        jda.addEventListener(new NowPlayingCommand());
        jda.addEventListener(new SkipCommand());
        jda.addEventListener(new PauseCommand());
        jda.addEventListener(new ResumeCommand());
        jda.addEventListener(new QueueAddCommand());
        jda.addEventListener(new QueueCommand());
    }

}
