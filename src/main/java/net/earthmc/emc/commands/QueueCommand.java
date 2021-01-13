package net.earthmc.emc.commands;

import com.mojang.brigadier.CommandDispatcher;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import static net.earthmc.emc.EMCMod.queue;

public class QueueCommand
{
    public static void register(CommandDispatcher<CottonClientCommandSource> dispatcher)
    {
        dispatcher.register(ArgumentBuilders.literal("queue").executes(source ->
        {
            source.getSource().sendFeedback(new TranslatableText("EMCE > Current Queue size: " + queue).formatted(Formatting.byName("AQUA")));
            return 1;
        }));
    }
}
