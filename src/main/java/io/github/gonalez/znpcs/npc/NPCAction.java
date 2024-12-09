package io.github.gonalez.znpcs.npc;

import com.google.common.base.MoreObjects;
import io.github.gonalez.znpcs.npc.event.ClickType;
import io.github.gonalez.znpcs.ServersNPC;
import io.github.gonalez.znpcs.user.ZUser;
import io.github.gonalez.znpcs.utility.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class NPCAction {
    private final ActionType actionType;

    private final ClickType clickType;

    private final String action;

    private int delay;

    public NPCAction(ActionType actionType, ClickType clickType, String action, int delay) {
        this.actionType = actionType;
        this.clickType = clickType;
        this.action = action;
        this.delay = delay;
    }

    public NPCAction(String actionType, String action) {
        this(ActionType.valueOf(actionType), ClickType.DEFAULT, action, 0);
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public ClickType getClickType() {
        return this.clickType;
    }

    public String getAction() {
        return this.action;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public long getFixedDelay() {
        return 1000000000L * this.delay;
    }

    public void run(ZUser user, String action) {
        this.actionType.run(user, Utils.PLACEHOLDER_SUPPORT ? Utils.getWithPlaceholders(action, user.toPlayer()) : action);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("actionType", this.actionType)
                .add("clickType", this.clickType)
                .add("action", this.action)
                .add("delay", this.delay)
                .toString();
    }

    enum ActionType {
        SHOP {
            @Override
            public void run(ZUser param1ZUser, String param1String) {
                ServersNPC.GTW_SHOPS_INTEGRATION.openShop(param1ZUser.toPlayer(), param1String);
            }
        },
        CMD {
            public void run(ZUser user, String actionValue) {
                user.toPlayer().performCommand(actionValue);
            }
        },
        CONSOLE {
            public void run(ZUser user, String actionValue) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actionValue);
            }
        },
        CHAT {
            public void run(ZUser user, String actionValue) {
                user.toPlayer().chat(actionValue);
            }
        },
        MESSAGE {
            public void run(ZUser user, String actionValue) {
                user.toPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', actionValue));
            }
        },
        SERVER {
            public void run(ZUser user, String actionValue) {
                ServersNPC.BUNGEE_UTILS.sendPlayerToServer(user.toPlayer(), actionValue);
            }
        };

        public abstract void run(ZUser param1ZUser, String param1String);
    }
}
