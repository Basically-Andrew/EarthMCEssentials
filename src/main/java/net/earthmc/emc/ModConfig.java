package net.earthmc.emc;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import net.minecraft.util.Formatting;

@Config(name = "emc-essentials")
public class ModConfig implements ConfigData
{
    @ConfigEntry.Gui.CollapsibleObject
	public General general = new General();
    public Townless townless = new Townless();
    public Nearby nearby = new Nearby();

    public static class General
    {
        public boolean enableMod = true;
        public boolean emcOnly = true;
    }

    public static class Townless
    {
        public int townlessListXPos = 770;
        public int townlessListYPos = 375;

        public String townlessTextColor = "LIGHT_PURPLE";
        public String townlessPlayerColor = "LIGHT_PURPLE";
    }

    public static class Nearby
    {
        public int nearbyListXPos = 770;
        public int nearbyListYPos = 275;

        public String nearbyTextColor = "GOLD";
        public String nearbyPlayerColor = "GOLD";

        public String playerName = "";

        public int xRadius = 500;
        public int zRadius = 500;
    }
}