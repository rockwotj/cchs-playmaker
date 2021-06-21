workspace(
    name = "playmaker",
)

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive", "http_file")

PACKAGED_JRE_VERSION = "jdk-11.0.11+9"

PACKAGED_JRE_VERSION_URL_SEGMENT = PACKAGED_JRE_VERSION.replace("+", "%2B")

PACKAGED_JRE_VERSION_FILE_SEGMENT = PACKAGED_JRE_VERSION.replace("+", "_").replace("jdk-", "")

PACKAGED_JRE_BASE_URL = "https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download"

http_archive(
    name = "windows_jre",
    build_file = "//third_party:jre.BUILD",
    strip_prefix = "{version}-jre".format(version = PACKAGED_JRE_VERSION),
    sha256 = "a7377fb0807fa619de49eec02ad7e2110c257649341f5ccffbaafa43cc8cbcc8",
    urls = ["{base_url}/{dir}/OpenJDK11U-jre_x64_windows_hotspot_{file}.zip".format(
        base_url = PACKAGED_JRE_BASE_URL,
        dir = PACKAGED_JRE_VERSION_URL_SEGMENT,
        file = PACKAGED_JRE_VERSION_FILE_SEGMENT,
    )],
)

http_archive(
    name = "mac_jre",
    build_file = "//third_party:jre.BUILD",
    sha256 = "ccb38c0b73bd0ba7006d00234a51eee9504ec8108c835e1f1763191806374707",
    strip_prefix = "{version}-jre".format(version = PACKAGED_JRE_VERSION),
    urls = ["{base_url}/{dir}/OpenJDK11U-jre_x64_mac_hotspot_{file}.tar.gz".format(
        base_url = PACKAGED_JRE_BASE_URL,
        dir = PACKAGED_JRE_VERSION_URL_SEGMENT,
        file = PACKAGED_JRE_VERSION_FILE_SEGMENT,
    )],
)

WARP_PACKAGER_VERSION = "0.3.0"

WARP_PACKAGER_BASE_URL = "https://github.com/dgiagio/warp/releases/download"

http_file(
  name = "mac_warp",
  urls = ["{base_url}/v{version}/macos-x64.warp-packer".format(version = WARP_PACKAGER_VERSION, base_url = WARP_PACKAGER_BASE_URL)],
  sha256 = "01d00038dbbe4e5a6e2ca19c1235f051617ac0e6e582d2407a06cec33125044b",
  executable = True,
)

http_file(
  name = "windows_warp",
  urls = ["{base_url}/v{version}/windows-x64.warp-packer.exe".format(version = WARP_PACKAGER_VERSION, base_url = WARP_PACKAGER_BASE_URL)],
  sha256 = "4f9a0f223f0e9f689fc718fdf86a147a357921ffa69c236deadc3274091070c1",
  executable = True,
)

BAZEL_SKYLIB_VERSION = "1.0.3"

BAZEL_SKYLIB_BASE_URL = "https://github.com/bazelbuild/bazel-skylib/releases/download"

http_archive(
    name = "bazel_skylib",
    urls = [
        "{base_url}/{version}/bazel-skylib-{version}.tar.gz".format(base_url = BAZEL_SKYLIB_BASE_URL, version = BAZEL_SKYLIB_VERSION),
    ],
    sha256 = "1c531376ac7e5a180e0237938a2536de0c54d93f5c278634818e0efc952dd56c",
)


RULES_JVM_EXTERNAL_TAG = "4.0"

RULES_JVM_EXTERNAL_SHA = "31701ad93dbfe544d597dbe62c9a1fdd76d81d8a9150c2bf1ecf928ecdf97169"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@bazel_skylib//:workspace.bzl", "bazel_skylib_workspace")

bazel_skylib_workspace()

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "com.beust:jcommander:1.81",
        "com.google.guava:guava:30.1.1-jre",
        "com.formdev:flatlaf:1.2",
    ],
    fetch_sources = True,
    version_conflict_policy = "pinned",
    maven_install_json = "//:maven_install.json",
    repositories = [
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()
