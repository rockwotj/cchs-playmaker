load("@bazel_skylib//rules:native_binary.bzl", "native_binary")
load("@bazel_skylib//rules:run_binary.bzl", "run_binary")


native_binary(
    name = "warp",
    src = select({
      "//pkg/platforms:windows_x86_64": "@mac_warp//file",
      "//pkg/platforms:osx_x86_64": "@mac_warp//file",
    }),
    out = "warp",
)

run_binary(
    name = "mac_pkg",
    srcs = ["//pkg/mac", "//pkg/mac:run.sh"],
    args = [
        "--arch", "macos-x64",
        "--input_dir", "$(location //pkg/mac:run.sh)/..",
        "--exec", "run.sh",
        "--output", "$(location playmaker.bin)"
        ],
    outs = ["playmaker.bin"],
    tool = ":warp",
)




