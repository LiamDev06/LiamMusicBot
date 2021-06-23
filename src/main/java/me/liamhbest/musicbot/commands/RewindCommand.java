package me.liamhbest.musicbot.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.liamhbest.musicbot.utility.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class RewindCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String command = "!rewind";
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(command) && !event.getAuthor().isBot()) {
            if (!Utils.botInVoiceChannel(event)) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                embed.setAuthor("There is no music currently playing!");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            if (PlayCommand.widePlayer.getPlayingTrack() == null){
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                embed.setAuthor("There is no music currently playing!");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            if (PlayCommand.widePlayer.isPaused()) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.YELLOW);
                embed.setAuthor("The song is paused! Resume it before performing this.");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            if (args.length <= 1){
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.YELLOW);
                embed.setAuthor("Please perform !rewind [amount][s/m]");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            AudioTrack playingTrack = PlayCommand.widePlayer.getPlayingTrack();
            long forwardAmountInLong;

            try {
                forwardAmountInLong = Long.parseLong(args[1].replace("s", "").replace("m", ""));
            } catch (Exception exception){
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                embed.setTitle("Invalid Format!");
                embed.appendDescription("The amount can only contain numbers!");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            if (forwardAmountInLong < 0) {
                int args1Amount = args[1].length();
                char sOrM = args[1].charAt(args1Amount-1);

                if (sOrM == 's') {
                    //Set seconds
                    event.getChannel().sendMessage("**Testing:** Rewind in seconds").queue();
                } else if (sOrM == 'm') {
                    //Set minute
                    event.getChannel().sendMessage("**Testing:** Rewind in minutes").queue();
                } else {
                    //Error, you can only set minute and second
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.RED);
                    embed.setTitle("Wrong Format");
                    embed.appendDescription("You can only Rewind in seconds or minutes! Please use 's' or 'm'.");
                    event.getChannel().sendMessage(embed.build()).queue();
                }

                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.GREEN);
                embed.setTitle(":arrow_backward: Rewinded Song");
                embed.appendDescription("You rewinded in the song!\n");

            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.red);
                embed.setAuthor("You cannot rewind longer than the song!");
                event.getChannel().sendMessage(embed.build()).queue();
            }

        }

    }

}
