load(":warp.bzl", "warp_pkg")

warp_pkg(
    name = "playmaker_macos",
    arch = "macos",
    inputs = [
        "//java/com/github/rockwotj/playmaker:playmaker_deploy.jar",
    ],
    runner = "mac_runner.sh",
    out = "playmaker-macos.bin",
)