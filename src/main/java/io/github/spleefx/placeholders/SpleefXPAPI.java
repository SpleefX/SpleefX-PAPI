/*
 * * Copyright 2019 github.com/ReflxctionDev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.spleefx.placeholders;

import io.github.spleefx.SpleefX;
import io.github.spleefx.data.GameStats;
import io.github.spleefx.data.PlayerStatistic;
import io.github.spleefx.extension.ExtensionMode;
import io.github.spleefx.extension.ExtensionsManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

/**
 * PlaceholderAPI expansion for SpleefX
 */
public class SpleefXPAPI extends PlaceholderExpansion {

    /**
     * The SpleefX plugin
     */
    private SpleefX plugin;

    /**
     * The placeholder identifier of this expansion
     *
     * @return placeholder identifier that is associated with this expansion
     */
    public String getIdentifier() {
        return "spleefx";
    }

    /**
     * The author of this expansion
     *
     * @return name of the author for this expansion
     */
    public String getAuthor() {
        return "Reflxction";
    }

    /**
     * The version of this expansion
     *
     * @return current version of this expansion
     */
    public String getVersion() {
        return "1.0-SNAPSHOT";
    }

    /**
     * The name of the plugin that this expansion hooks into. by default will return the deprecated
     * {@link #getPlugin()} method
     *
     * @return plugin name that this expansion requires to function
     */
    @Override
    public String getRequiredPlugin() {
        return "SpleefX";
    }

    /**
     * If any requirements need to be checked before this expansion should register, you can check
     * them here
     *
     * @return true if this hook meets all the requirements to register
     */
    @Override
    public boolean canRegister() {
        if (!Bukkit.getPluginManager().isPluginEnabled(getRequiredPlugin())) {
            return false;
        }
        plugin = (SpleefX) Bukkit.getPluginManager().getPlugin(getRequiredPlugin());
        return super.register() && plugin != null;
    }

    /**
     * called when a placeholder value is requested from this hook
     *
     * @param player     {@link OfflinePlayer} to request the placeholder value for, null if not needed for a
     *                   player
     * @param identifier String passed to the hook to determine what value to return
     * @return value for the requested player and params
     */
    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        if (player == null) return "0";
        GameStats stats = plugin.getDataProvider().getStatistics(player);
        switch (identifier.toLowerCase()) {
            case "games_played":
                return Integer.toString(stats.get(PlayerStatistic.GAMES_PLAYED, null));
            case "wins":
                return Integer.toString(stats.get(PlayerStatistic.WINS, null));
            case "losses":
                return Integer.toString(stats.get(PlayerStatistic.LOSSES, null));
            case "draws":
                return Integer.toString(stats.get(PlayerStatistic.DRAWS, null));
            case "blocks_mined":
                return Integer.toString(stats.get(PlayerStatistic.BLOCKS_MINED, null));
            default:
                String[] split = identifier.split("_");
                String key = split[split.length - 1];
                ExtensionMode mode = ExtensionsManager.getByKey(key);
                switch (identifier.substring(0, identifier.indexOf(split[split.length - 1]) - 1).toLowerCase()) {
                    case "games_played":
                        return Integer.toString(stats.get(PlayerStatistic.GAMES_PLAYED, mode));
                    case "wins":
                        return Integer.toString(stats.get(PlayerStatistic.WINS, mode));
                    case "losses":
                        return Integer.toString(stats.get(PlayerStatistic.LOSSES, mode));
                    case "draws":
                        return Integer.toString(stats.get(PlayerStatistic.DRAWS, mode));
                    case "blocks_mined":
                        return Integer.toString(stats.get(PlayerStatistic.BLOCKS_MINED, mode));
                    default:
                        return "0";
                }
        }
    }
}
