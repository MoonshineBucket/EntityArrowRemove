package com.github.dreamsmoke.chunk;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import org.bukkit.plugin.java.JavaPlugin;

public class ChunkUnloading extends JavaPlugin {

    @Override
    public void onEnable() {
        WorldServer[] worldServers = MinecraftServer.getServer().worldServers;
        for(int length = worldServers.length, i = 0; i < length; ++i) {
            WorldServer worldServer = worldServers[i];
            if(worldServer == null) continue;

            worldServer.theChunkProviderServer.unloadAllChunks();

            getLogger().info(String.format("WorldServer: %s", worldServer.getWorldInfo().getWorldName()));
            getLogger().info(String.format("Count Chunks: %s", worldServer.activeChunkSet.size()));
            getLogger().info(String.format("Count Players: %s", worldServer.playerEntities.size()));
            getLogger().info(String.format("Count Entities: %s", worldServer.loadedEntityList.size()));
            getLogger().info(String.format("Count TileEntities: %s", worldServer.loadedTileEntityList.size()));
            getLogger().info(String.format("Count Unloaded: %s", worldServer.unloadedEntityList.size()));
        }
    }

}
