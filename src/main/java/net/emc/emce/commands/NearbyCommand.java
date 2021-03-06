package net.emc.emce.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.emc.emce.PlayerMessaging;
import net.emc.emce.utils.Timers;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import static net.emc.emce.EMCE.config;
import static net.emc.emce.EMCE.client;
import static net.emc.emce.EMCE.nearby;

public class NearbyCommand {
    public static void register(CommandDispatcher<CottonClientCommandSource> dispatcher) {
        dispatcher.register(ArgumentBuilders.literal("nearby").executes(c -> {
            if (client.player == null) return -1;

            Formatting headingFormatting = Formatting.byName(config.nearby.headingTextColour);
            Formatting textFormatting = Formatting.byName(config.nearby.playerTextColour);

            PlayerMessaging.sendMessage("text_nearby_header", headingFormatting, false);

            for (int i = 0; i < nearby.size(); i++) {
                JsonObject currentPlayer = (JsonObject) nearby.get(i);
                int distance = Math.abs(currentPlayer.get("x").getAsInt() - (int) client.player.getX()) +
                               Math.abs(currentPlayer.get("z").getAsInt() - (int) client.player.getZ());

                String prefix = "";

                if (config.nearby.showRank) {
                    if (!currentPlayer.has("town")) prefix = new TranslatableText("text_nearby_rank_townless").toString();
                    else prefix = "(" + currentPlayer.get("rank").getAsString() + ") ";
                }

                PlayerMessaging.sendMessage(prefix + currentPlayer.get("name").getAsString() + ": " + distance + "m", textFormatting, false);
            }

            return 1;
        }).then(ArgumentBuilders.literal("refresh").executes(c ->
        {
            Timers.restartTimer(Timers.nearbyTimer);
            PlayerMessaging.sendMessage("msg_nearby_refresh", Formatting.AQUA, true);
            return 1;
        })).then(ArgumentBuilders.literal("clear").executes(c ->
        {
            nearby = new JsonArray();
            PlayerMessaging.sendMessage("msg_nearby_clear", Formatting.AQUA, true);
            return 1;
        })));
    }
}
