package io.github.gonalez.znpcs;


import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GTWShopsIntegration {

    private final Plugin plugin;

    public GTWShopsIntegration(Plugin plugin) {
        this.plugin = plugin;
    }

    public void openShop(Player player, String shopName) {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("GTWHouses")) {
            plugin.getLogger().warning("GTWHouses is not enabled, cannot open shop");
            return;
        }

        Plugin gtwhouses = plugin.getServer().getPluginManager().getPlugin("GTWHouses");
        if (gtwhouses == null) {
            plugin.getLogger().warning("GTWHouses is not enabled, cannot open shop");
            return;
        }

        Class<?> gtwhousesClass = gtwhouses.getClass();
        try {
            gtwhousesClass.getMethod("openShop", Player.class, String.class).invoke(gtwhouses, player, shopName);
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to open shop: " + e.getMessage());
        }

    }

    public void openSell(Player player) {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("GTWHouses")) {
            plugin.getLogger().warning("GTWHouses is not enabled, cannot open sell");
            return;
        }

        Plugin gtwhouses = plugin.getServer().getPluginManager().getPlugin("GTWHouses");
        if (gtwhouses == null) {
            plugin.getLogger().warning("GTWHouses is not enabled, cannot open sell");
            return;
        }

        Class<?> gtwhousesClass = gtwhouses.getClass();
        try {
            gtwhousesClass.getMethod("openSell", Player.class).invoke(gtwhouses, player);
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to open sell shop: " + e.getMessage());
        }
    }
}