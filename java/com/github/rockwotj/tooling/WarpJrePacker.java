package com.github.rockwotj.tooling;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import com.google.common.collect.ImmutableList;
import com.google.devtools.build.runfiles.Runfiles;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Verify.verify;

public class WarpJrePacker {
    @SuppressWarnings({"FieldMayBeFinal", "MismatchedQueryAndUpdateOfCollection"})
    static class Args {
        @Parameter(names = "--runner", converter = FileConverter.class, required = true)
        private File runner = null;

        @Parameter(names = "--arch", converter = Architecture.OptionConverter.class, required = true)
        private Architecture arch = null;

        @Parameter(names = "--output", converter = FileConverter.class, required = true)
        private File output = null;

        @Parameter(converter = FileConverter.class)
        private List<File> inputs = new ArrayList<>();

    }

    private final Args args;

    private WarpJrePacker(Args args) {
        this.args = args;
    }

    public static void main(String[] argv) {
        Args args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);
        int exitCode;
        try {
            exitCode = new WarpJrePacker(args).run();
        } catch (Exception e) {
            e.printStackTrace();
            exitCode = 1;
        }
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    public int run() throws Exception {
        File inputDir = Files.createTempDirectory("warp-inputs").toFile();
        inputDir.deleteOnExit();

        for (File input : args.inputs) {
            Path src = input.toPath();
            Path target = inputDir.toPath().resolve(src.getFileName());
            Files.copy(src, target, StandardCopyOption.COPY_ATTRIBUTES);
        }

        String runnerFile = "runner-" + UUID.randomUUID().toString();
        Files.copy(args.runner.toPath(), inputDir.toPath().resolve(runnerFile), StandardCopyOption.COPY_ATTRIBUTES);

        ImmutableList<String> command = ImmutableList.of(
                warpBinary().getAbsolutePath(),
                "--arch",
                args.arch.toString(),
                "--input_dir",
                inputDir.getAbsolutePath(),
                "--exec",
                runnerFile,
                "--output",
                args.output.getAbsolutePath()
        );
        Process process = new ProcessBuilder(command).start();
        return process.waitFor();
    }

    private File warpBinary() throws Exception {
        Runfiles runfiles = Runfiles.create();
        String path = runfiles.rlocation("playmaker/java/com/github/rockwotj/tooling/warp_packer");
        File file = new File(path);
        verify(file.exists());
        return file;
    }
}
