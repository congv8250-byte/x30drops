package com.x30drops;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * X30 Drops - mọi thứ rơi vật phẩm (khối bị phá, quái chết, câu cá, ...) đều rơi gấp 30 lần.
 *
 * Cấu hình trong file config/x30drops.properties:
 *   multiplier=30        số lần nhân (1 đến 1000)
 *   multiplyChests=false true = nhân luôn đồ trong rương/hầm ngục khi mở lần đầu
 *
 * Mod chạy phía server: chơi 1 người (singleplayer) cứ cài vào client là được,
 * còn chơi server thì phải cài vào server.
 */
public class X30Drops implements ModInitializer {
    public static final String MOD_ID = "x30drops";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final int DEFAULT_MULTIPLIER = 30;
    private static final int MAX_MULTIPLIER = 1000;

    private static volatile int multiplier = DEFAULT_MULTIPLIER;
    private static volatile boolean multiplyChests = false;

    @Override
    public void onInitialize() {
        loadConfig();
        LOGGER.info("X30 Drops loaded, multiplier = x{}, chests = {}", multiplier, multiplyChests);

        LootTableEvents.MODIFY_DROPS.register((holder, context, drops) -> {
            int m = multiplier;
            if (m <= 1 || drops.isEmpty()) {
                return;
            }
            // Loot table rương (vd "minecraft:chests/...") mặc định không nhân
            if (!multiplyChests && holder.getRegisteredName().contains(":chests/")) {
                return;
            }

            List<ItemStack> result = new ArrayList<>();
            for (ItemStack stack : drops) {
                if (stack.isEmpty()) {
                    continue;
                }
                long total = (long) stack.getCount() * m;
                int maxStack = Math.max(1, stack.getMaxStackSize());
                // Tách thành nhiều stack, mỗi stack không vượt quá giới hạn của vật phẩm
                while (total > 0) {
                    int take = (int) Math.min(total, maxStack);
                    result.add(stack.copyWithCount(take));
                    total -= take;
                }
            }
            drops.clear();
            drops.addAll(result);
        });
    }

    private static void loadConfig() {
        Path file = FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + ".properties");
        Properties props = new Properties();
        props.setProperty("multiplier", String.valueOf(DEFAULT_MULTIPLIER));
        props.setProperty("multiplyChests", "false");
        try {
            if (Files.exists(file)) {
                try (var in = Files.newInputStream(file)) {
                    props.load(in);
                }
            } else {
                try (var out = Files.newOutputStream(file)) {
                    props.store(out, "X30 Drops - multiplier (1 to " + MAX_MULTIPLIER
                            + "), multiplyChests (true/false)");
                }
            }
            int value = Integer.parseInt(props.getProperty("multiplier").trim());
            multiplier = Math.max(1, Math.min(MAX_MULTIPLIER, value));
            multiplyChests = Boolean.parseBoolean(props.getProperty("multiplyChests", "false").trim());
        } catch (IOException | NumberFormatException e) {
            LOGGER.warn("Could not read {}, using defaults", file, e);
            multiplier = DEFAULT_MULTIPLIER;
            multiplyChests = false;
        }
    }
}
