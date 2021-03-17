package Method.Client.utils.SeedViewer;

import Method.Client.utils.system.Wrapper;
import com.google.common.collect.Sets;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveDataMemoryStorage;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraft.world.storage.WorldInfo;

public class AwesomeWorld extends World {

    public ChunkProviderClient getChunkProvider()
    {
        return (ChunkProviderClient)super.getChunkProvider();
    }
    private ChunkProviderClient clientChunkProvider;

    protected AwesomeWorld(WorldInfo worldInfo) {
        super(new SaveHandlerMP(), worldInfo, net.minecraftforge.common.DimensionManager.createProviderFor(0), Wrapper.mc.profiler, true);

        this.getWorldInfo().setDifficulty(EnumDifficulty.PEACEFUL);
        this.provider.setWorld(this);
        this.setSpawnPoint(new BlockPos(8, 64, 8)); //Forge: Moved below registerWorld to prevent NPE in our redirect.
        this.chunkProvider = this.createChunkProvider();
        this.mapStorage = new SaveDataMemoryStorage();
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
        this.initCapabilities();
    }

    @Override
    protected IChunkProvider createChunkProvider()
    {
        this.clientChunkProvider = new ChunkProviderClient(this);
        return this.clientChunkProvider;
    }

    @Override
    protected boolean isChunkLoaded(int x, int z, boolean allowEmpty)
    {
        return allowEmpty || !this.getChunkProvider().provideChunk(x, z).isEmpty();
    }
}
