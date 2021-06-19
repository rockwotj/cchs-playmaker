package com.github.rockwotj.tooling;

import static com.google.common.base.Verify.verify;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import com.beust.jcommander.converters.PathConverter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.MoreFiles;
import com.google.devtools.build.runfiles.Runfiles;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WarpJrePacker {
  @SuppressWarnings({"FieldMayBeFinal", "MismatchedQueryAndUpdateOfCollection"})
  static class Args {
    @Parameter(names = "--arch", converter = Architecture.OptionConverter.class, required = true)
    private Architecture arch = null;

    @Parameter(names = "--output", converter = FileConverter.class, required = true)
    private File output = null;

    @Parameter(names = "--jar", converter = PathConverter.class)
    private Path deployJar = null;

    @Parameter(names = "--bundle_jre")
    private Boolean bundleJre = false;

    @Parameter(converter = PathConverter.class)
    private List<Path> additionalFiles = new ArrayList<>();
  }

  private static final ImmutableSet<String> BAZEL_FILENAMES =
      ImmutableSet.of("WORKSPACE", "BUILD", "BUILD.bazel");

  private final Args args;

  private WarpJrePacker(Args args) {
    this.args = args;
  }

  public static void main(String[] argv) {
    Args args = new Args();
    JCommander.newBuilder().addObject(args).build().parse(argv);
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

  @SuppressWarnings("UnstableApiUsage")
  public int run() throws Exception {
    Path inputDir = Files.createTempDirectory("warp-inputs");

    // Copy the deploy jar over
    Files.copy(args.deployJar, inputDir.resolve("program.jar"), StandardCopyOption.COPY_ATTRIBUTES);

    // Copy our runner script
    String runnerFile = "runner-" + UUID.randomUUID().toString();
    Files.copy(
        runnerScript().toPath(), inputDir.resolve(runnerFile), StandardCopyOption.COPY_ATTRIBUTES);

    Path cwd = Paths.get(System.getProperty("user.dir"));

    // Copy over additional install files
    for (Path additionalFile : args.additionalFiles) {
      Path target = inputDir.resolve(additionalFile);
      Files.createDirectories(target.getParent());
      Files.copy(additionalFile, target, StandardCopyOption.COPY_ATTRIBUTES);
    }

    if (args.bundleJre) {
      // Copy the JRE
      Path javaHome = jreHome().toPath();
      for (Path src : MoreFiles.fileTraverser().breadthFirst(javaHome)) {
        if (!src.toFile().isFile()) continue;
        String basename = src.getFileName().toString();
        if (BAZEL_FILENAMES.contains(basename)) continue;
        Path target = inputDir.resolve("java-home").resolve(javaHome.relativize(src));
        Files.createDirectories(target.getParent());
        Files.copy(src, target, StandardCopyOption.COPY_ATTRIBUTES);
      }
    }

    // Pack them up using warp
    ImmutableList<String> command =
        ImmutableList.of(
            warpBinary().getAbsolutePath(),
            "--arch",
            args.arch.toString(),
            "--input_dir",
            inputDir.toAbsolutePath().toString(),
            "--exec",
            runnerFile,
            "--output",
            args.output.getAbsolutePath());
    Process process = new ProcessBuilder(command).start();
    return process.waitFor();
  }

  private File warpBinary() throws Exception {
    return checkedRunfile("playmaker/java/com/github/rockwotj/tooling/warp_packer");
  }

  private File runnerScript() throws Exception {
    return platformFile(
        "playmaker/java/com/github/rockwotj/tooling",
        ImmutableMap.of(Architecture.MACOS, "mac_runner.sh"));
  }

  private File jreHome() throws Exception {
    return platformFile(
        ImmutableMap.of(
            Architecture.MACOS, "mac_jre/Contents/Home",
            Architecture.WINDOWS, "windows_jre"));
  }

  private File platformFile(ImmutableMap<Architecture, String> platformMapping) throws Exception {
    return platformFile("", platformMapping);
  }

  private File platformFile(String prefix, ImmutableMap<Architecture, String> platformMapping)
      throws Exception {
    String runfilePath = platformMapping.get(args.arch);
    if (runfilePath == null) {
      throw new UnsupportedOperationException("Unsupported architecture: " + args.arch);
    }
    return checkedRunfile(Paths.get(prefix, runfilePath).toString());
  }

  private static File checkedRunfile(String path) throws Exception {
    Runfiles runfiles = Runfiles.create();
    File file = new File(runfiles.rlocation(path));
    verify(file.exists());
    return file;
  }
}
