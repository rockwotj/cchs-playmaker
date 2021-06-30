load("@rules_pkg//:pkg.bzl", "pkg_tar")

filegroup(
  name = "files",
  srcs = glob(["**"]),
  visibility = ["//visibility:public"],
)

pkg_tar(
  name = "jdk",
  srcs = [":files"],
  strip_prefix = ".",
  extension = "tgz",
)
