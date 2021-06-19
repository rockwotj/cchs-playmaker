package com.github.rockwotj.tooling;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import com.google.common.collect.ImmutableList;
import com.google.devtools.build.runfiles.Runfiles;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Verify.verify;
import static java.util.stream.Collectors.toList;

public class WarpCreator {
    @SuppressWarnings("FieldMayBeFinal")
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

    private Args args;

    private WarpCreator(Args args) {
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
            exitCode = new WarpCreator(args).run();
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
        System.out.println(args.inputs.stream().map(File::exists).collect(toList()));
        ImmutableList<String> command = ImmutableList.of(
                warpBinary().getAbsolutePath(),
                "--arch",
                args.arch.toString(),
                "--input_dir",
                null,
                "--exec",
                args.runner.getAbsolutePath(),
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
