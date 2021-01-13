package net.earthmc.emc.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.earthmc.emc.EMCMod;
import net.earthmc.emc.utils.ModUtils;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import static net.earthmc.emc.utils.Timers.*;

public class TownlessCommand 
{
    public static void register(CommandDispatcher<CottonClientCommandSource> dispatcher)
    {
        dispatcher.register(ArgumentBuilders.literal("townless").executes
        (
            source ->
            {
                final JsonArray townless = EMCMod.townless;
                StringBuilder townlessString = new StringBuilder();
                Formatting headingFormatting = Formatting.byName(EMCMod.config.townless.headingTextColour);
                Formatting playerNameFormatting = Formatting.byName(EMCMod.config.townless.playerTextColour);

                for (int i = 0; i < townless.size(); i++)
                {
                    JsonObject currentPlayer = (JsonObject) townless.get(i);
                    townlessString.append(currentPlayer.get("name").getAsString()).append(" ");
                }

                if (townlessString.length() != 0) {
                    source.getSource().sendFeedback(new TranslatableText("Townless Players [" + townless.size() + "]").formatted(headingFormatting));
                    source.getSource().sendFeedback(new TranslatableText(townlessString.toString()).formatted(playerNameFormatting));
                } else {
                    source.getSource().sendFeedback(new TranslatableText("There don't seem to be any townless players online at the moment.").formatted(Formatting.byName("RED")));
                }

                return 1;
            }
        ).then(ArgumentBuilders.literal("inviteAll").executes(source -> {
                if (ModUtils.shouldRender())
                {
                    final JsonArray townless = EMCMod.townless;
                    StringBuilder townlessString = new StringBuilder();

                    for (int i = 0; i < townless.size(); i++) {
                        JsonObject currentPlayer = (JsonObject) townless.get(i);
                        if (("/towny:town invite " + townlessString + currentPlayer.get("name").getAsString()).length() > 256)
                            break;
                        else townlessString.append(currentPlayer.get("name").getAsString()).append(" ");
                    }

                    if (EMCMod.client.player != null)
                        EMCMod.client.player.sendChatMessage("/towny:town invite " + townlessString);

                    source.getSource().sendFeedback(new TranslatableText("EMCE > Invites sent!").formatted(Formatting.byName("AQUA")));
                    source.getSource().sendFeedback(new TranslatableText("EMCE > Note: You still need permissions to invite players to your town.").formatted(Formatting.byName("RED")));
                }
                else source.getSource().sendFeedback(new TranslatableText("EMCE > Unable to invite players, try again on EarthMC.").formatted(Formatting.byName("RED")));

                return 1;
            }
        ).then(ArgumentBuilders.literal("refresh").executes(source -> {
            restartTimer(townlessTimer);
            source.getSource().sendFeedback(new TranslatableText("EMCE > Refreshing townless players...").formatted(Formatting.byName("AQUA")));

            return 1;
        })).then(ArgumentBuilders.literal("clear").executes(source -> {
            EMCMod.townless = new JsonArray();
            source.getSource().sendFeedback(new TranslatableText("EMCE > Clearing townless players...").formatted(Formatting.byName("AQUA")));

            return 1;
        }))));
    }
}
