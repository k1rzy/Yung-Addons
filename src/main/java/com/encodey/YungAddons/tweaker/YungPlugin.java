package com.encodey.YungAddons.tweaker;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import scala.tools.nsc.transform.Mixin;

import java.util.Map;
/**
 * @author k1rzy (encodey)
 */
@IFMLLoadingPlugin.MCVersion("1.8.9")
@IFMLLoadingPlugin.Name("YungAddons")
public class YungPlugin implements IFMLLoadingPlugin {

    public YungPlugin() {
        MixinBootstrap.init();
    }
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{YungTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
