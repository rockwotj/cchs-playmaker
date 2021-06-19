load("@bazel_skylib//rules:native_binary.bzl", "native_binary")

java_binary(
  name = "WarpJrePacker",
  main_class = "com.github.rockwotj.tooling.WarpJrePacker",
  srcs = glob(["*.java"]),
  data = [
      ":warp",
      "@windows_jre//:files",
      "@mac_jre//:files",
      "mac_runner.sh",
  ],
  visibility = ["//visibility:public"],
  deps = [
      "@maven//:com_beust_jcommander",
      "@maven//:com_google_guava_guava",
      "@bazel_tools//tools/java/runfiles",
  ],
)

native_binary(
    name = "warp",
    src = select({
      "//pkg/platforms:windows_x86_64": "@mac_warp//file",
      "//pkg/platforms:osx_x86_64": "@mac_warp//file",
    }),
    out = "warp_packer",
)

