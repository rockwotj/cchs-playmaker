load("@bazel_skylib//rules:copy_file.bzl", "copy_file")

copy_file(
    name = "playmaker_jar",
    src = "//java/com/github/rockwotj/playmaker:playmaker_deploy.jar",
    out = "playmaker.jar",
)

filegroup(
    name = "mac",
    srcs = [
        ":playmaker_jar",
        "run.sh",
    ],
    visibility = ["//pkg:__subpackages__"],
)

exports_files(
  ["run.sh"],
  visibility = ["//pkg:__subpackages__"],
)

