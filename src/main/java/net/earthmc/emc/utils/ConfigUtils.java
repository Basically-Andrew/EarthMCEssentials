package net.earthmc.emc.utils;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.earthmc.emc.EMCMod;
import net.earthmc.emc.ModConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigManager;
import me.sargunvohra.mcmods.autoconfig1u.serializer.ConfigSerializer;

public class ConfigUtils
{
    private ConfigUtils() { }

    public static void serializeConfig(ModConfig config)
    {
        try 
        {
            ((ConfigManager<ModConfig>) AutoConfig.getConfigHolder(ModConfig.class)).getSerializer().serialize(config);
        } catch (ConfigSerializer.SerializationException serializeException) {
            serializeException.printStackTrace();
        }
    }

    public static ConfigBuilder getConfigBuilder()
    {
        ConfigBuilder builder = ConfigBuilder.create().setTitle("EarthMC Essentials Config").setTransparentBackground(true);

        ConfigCategory general = builder.getOrCreateCategory("General");
        ConfigCategory townless = builder.getOrCreateCategory("Townless");
        ConfigCategory nearby = builder.getOrCreateCategory("Nearby");
        ConfigCategory townInfo = builder.getOrCreateCategory("Town Info");
        ConfigCategory nationInfo = builder.getOrCreateCategory("Nation Info");

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // #region Add Entries
        // Enable Mod
        general.addEntry(entryBuilder.startBooleanToggle("Enable Mod", EMCMod.config.general.enableMod)
                .setDefaultValue(true)
                .setTooltip("Toggles the mod on or off.")
                .setSaveConsumer(newValue -> EMCMod.config.general.enableMod = newValue)
                .build());

        // Enable EMC Only
        general.addEntry(entryBuilder.startBooleanToggle("EMC Only", EMCMod.config.general.emcOnly)
                .setDefaultValue(true)
                .setTooltip("While enabled, overlays only render while you are on EarthMC.")
                .setSaveConsumer(newValue -> EMCMod.config.general.emcOnly = newValue)
                .build());

        // Enable Townless
        townless.addEntry(entryBuilder.startBooleanToggle("Enabled", EMCMod.config.townless.enabled)
                .setDefaultValue(true)
                .setTooltip("Toggles townless players on or off.")
                .setSaveConsumer(newValue -> EMCMod.config.townless.enabled = newValue)
                .build());

        // Townless Preset positions
        townless.addEntry(entryBuilder.startBooleanToggle("Use Preset Positions", EMCMod.config.townless.presetPositions)
                .setDefaultValue(true)
                .setTooltip("Toggles the use of preset positions, uses sliders if off.")
                .setSaveConsumer(newValue -> EMCMod.config.townless.presetPositions = newValue)
                .build());

        // If advanced positioning isn't toggled, use preset position.
        if (EMCMod.config.townless.presetPositions)
        {
                // Townless Preset Position
                townless.addEntry(entryBuilder.startEnumSelector("Preset Position", ModUtils.State.class, EMCMod.config.townless.positionState)
                        .setDefaultValue(ModUtils.State.TOP_LEFT)
                        .setTooltip("The position of the Townless info.")
                        .setSaveConsumer(newValue -> EMCMod.config.townless.positionState = newValue)
                        .build());
        }
        else
        {   
                // Townless Horizontal Position
                townless.addEntry(entryBuilder.startIntField("Horizontal Position (X)", EMCMod.config.townless.xPos)
                        .setDefaultValue(770)
                        .setTooltip("The horizontal position on the HUD.")
                        .setSaveConsumer(newValue -> EMCMod.config.townless.xPos = newValue)
                        .build());

                // Townless Vertical Position
                townless.addEntry(entryBuilder.startIntField("Vertical Position (Y)", EMCMod.config.townless.yPos)
                        .setDefaultValue(375)
                        .setTooltip("The vertical position on the HUD.")
                        .setSaveConsumer(newValue -> EMCMod.config.townless.yPos = newValue)
                        .build());

        }

        // Townless Text Color
        townless.addEntry(entryBuilder.startSelector("Heading Colour", EMCMod.colors, EMCMod.config.townless.headingTextColour)
                .setDefaultValue(EMCMod.colors[8])
                .setTooltip("The colour of the 'Townless Players' text.")
                .setSaveConsumer(newValue -> EMCMod.config.townless.headingTextColour = newValue)
                .build());

        // Townless Player Color
        townless.addEntry(entryBuilder.startSelector("Player Colour", EMCMod.colors, EMCMod.config.townless.playerTextColour)
                .setDefaultValue(EMCMod.colors[8])
                .setTooltip("The colour of the townless player names.")
                .setSaveConsumer(newValue -> EMCMod.config.townless.playerTextColour = newValue)
                .build());

        // Townless Max length
        townless.addEntry(entryBuilder.startIntField("Maximum Length", EMCMod.config.townless.maxLength)
                .setDefaultValue(0)
                .setTooltip("The maximum length the townless list can be. Enter anything under 1 for no limit.")
                .setSaveConsumer(newValue -> EMCMod.config.townless.maxLength = newValue)
                .build());

        // Enable nearby
        nearby.addEntry(entryBuilder.startBooleanToggle("Enabled", EMCMod.config.nearby.enabled)
                .setDefaultValue(true)
                .setTooltip("Toggles nearby players on or off.")
                .setSaveConsumer(newValue -> EMCMod.config.nearby.enabled = newValue)
                .build());

        // Nearby Preset positions
        nearby.addEntry(entryBuilder.startBooleanToggle("Use Preset Positions", EMCMod.config.nearby.presetPositions)
                .setDefaultValue(true)
                .setTooltip("Toggles the use of preset positions, uses sliders if off.")
                .setSaveConsumer(newValue -> EMCMod.config.nearby.presetPositions = newValue)
                .build());

        if (EMCMod.config.nearby.presetPositions)
        {
                // Nearby Preset Position
                nearby.addEntry(entryBuilder.startEnumSelector("Preset Position", ModUtils.State.class, EMCMod.config.nearby.positionState)
                        .setDefaultValue(ModUtils.State.TOP_RIGHT)
                        .setTooltip("The position of the Nearby info.")
                        .setSaveConsumer(newValue -> EMCMod.config.nearby.positionState = newValue)
                        .build());
        }
        else
        {
                // Nearby Player Horizontal Position
                nearby.addEntry(entryBuilder.startIntSlider("Horizontal Position (X)", EMCMod.config.nearby.xPos, 1, 1000)
                        .setDefaultValue(770)
                        .setTooltip("The horizontal position on the HUD.")
                        .setSaveConsumer(newValue -> EMCMod.config.nearby.xPos = newValue)
                        .build());

                // Nearby Player Vertical Position
                nearby.addEntry(entryBuilder.startIntSlider("Vertical Position (Y)", EMCMod.config.nearby.yPos, 16, 1000)
                        .setDefaultValue(275)
                        .setTooltip("The vertical position on the HUD.")
                        .setSaveConsumer(newValue -> EMCMod.config.nearby.yPos = newValue)
                        .build());
        }

        // Nearby Player Text Color
        nearby.addEntry(entryBuilder.startSelector("Heading Colour", EMCMod.colors, EMCMod.config.nearby.headingTextColour)
                .setDefaultValue(EMCMod.colors[11])
                .setTooltip("The colour of the 'Nearby Players' text.")
                .setSaveConsumer(newValue -> EMCMod.config.nearby.headingTextColour = newValue)
                .build());

        // Nearby Player Player Color
        nearby.addEntry(entryBuilder.startSelector("Player Colour", EMCMod.colors, EMCMod.config.nearby.playerTextColour)
                .setDefaultValue(EMCMod.colors[11])
                .setTooltip("The colour of the nearby player names.")
                .setSaveConsumer(newValue -> EMCMod.config.nearby.playerTextColour = newValue)
                .build());
                
        // Nearby X Radius
        nearby.addEntry(entryBuilder.startIntSlider("X Radius", EMCMod.config.nearby.xRadius, 50, 10000)
                .setDefaultValue(500)
                .setTooltip("The x radius (in blocks) to check inside.")
                .setSaveConsumer(newValue -> EMCMod.config.nearby.xRadius = newValue)
                .build());

        // Nearby Z Radius
        nearby.addEntry(entryBuilder.startIntSlider("Z Radius", EMCMod.config.nearby.zRadius, 50, 10000)
                .setDefaultValue(500)
                .setTooltip("The z radius (in blocks) to check inside.")
                .setSaveConsumer(newValue -> EMCMod.config.nearby.zRadius = newValue)
                .build());

        builder.setSavingRunnable(() -> ConfigUtils.serializeConfig(EMCMod.config));

        return builder;
    }
}