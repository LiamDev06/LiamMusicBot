package me.liamhbest.musicbot.commands;

import me.liamhbest.musicbot.utility.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;

public class StartCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String command = "!start";

        if (event.getMessage().getContentRaw().equalsIgnoreCase(command)
                && !event.getAuthor().isBot()) {
            if (!Utils.hasMusicBotPermission(event.getMember())) return;

            if (event.getMember().getVoiceState().inVoiceChannel()) {
                AudioManager audioManager = event.getGuild().getAudioManager();
                audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());

                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.GREEN);
                embed.setTitle(":green_circle: Bot Joined");
                embed.appendDescription("The bot has joined the voice channel!");
                event.getChannel().sendMessage(embed.build()).queue();

            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.YELLOW);
                embed.setAuthor("You need to be in a voice channel for this command to work!");
                event.getChannel().sendMessage(embed.build()).queue();
            }

        }


    }


}
