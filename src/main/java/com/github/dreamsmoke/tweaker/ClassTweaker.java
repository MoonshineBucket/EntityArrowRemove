package com.github.dreamsmoke.tweaker;

import com.github.dreamsmoke.tweaker.transformers.ClassTransformer;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.io.File;
import java.util.List;

public class ClassTweaker implements ITweaker {

    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {}

    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        classLoader.registerTransformer(ClassTransformer.class.getName());
    }

    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    public String[] getLaunchArguments() {
        return new String[0];
    }
    
}

