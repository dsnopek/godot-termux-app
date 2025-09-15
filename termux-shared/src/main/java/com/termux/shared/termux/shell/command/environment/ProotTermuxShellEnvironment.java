package com.termux.shared.termux.shell.command.environment;

import android.content.Context;

import androidx.annotation.NonNull;

import com.termux.shared.shell.command.ExecutionCommand;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ProotTermuxShellEnvironment extends TermuxShellEnvironment {

    private String mLibDir;

    public ProotTermuxShellEnvironment(String libDir) {
        super();
        mLibDir = libDir;
    }

    @NonNull
    @Override
    public String[] setupShellCommandArguments(@NonNull String executable, String[] arguments) {
        String[] oldArgs = super.setupShellCommandArguments(executable, arguments);
        
        File proot = new File(mLibDir, "libproot.so");

        List<String> newArgs = new ArrayList<>(Arrays.asList(oldArgs));
        newArgs.add(0, proot.getAbsolutePath());
        
        return newArgs.toArray(new String[0]);
    }

    @NonNull
    @Override
    public HashMap<String, String> setupShellCommandEnvironment(@NonNull Context currentPackageContext,
                                                                @NonNull ExecutionCommand executionCommand) {
        HashMap<String, String> env = super.setupShellCommandEnvironment(currentPackageContext, executionCommand);
        
        File prootLoader = new File(mLibDir, "libproot-loader.so");
        File prootLoader32 = new File(mLibDir, "libproot-loader32.so");

        env.put("PROOT_LOADER", prootLoader.getAbsolutePath());
        env.put("PROOT_LOADER_32", prootLoader32.getAbsolutePath());

        return env;
    }

}
