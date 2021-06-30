workspace(
    name = "playmaker",
)

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive", "http_file")


http_archive(
    name = "rules_java",
    url = "https://github.com/bazelbuild/rules_java/releases/download/4.0.0/rules_java-4.0.0.tar.gz",
    sha256 = "34b41ec683e67253043ab1a3d1e8b7c61e4e8edefbcad485381328c934d072fe",
)

JDK_VERSION = "jdk-16.0.1+9"

JDK_VERSION_URL_SEGMENT = JDK_VERSION.replace("+", "%2B")

JDK_VERSION_FILE_SEGMENT = JDK_VERSION.replace("+", "_").replace("jdk-", "")

JDK_BASE_URL = "https://github.com/AdoptOpenJDK/openjdk16-binaries/releases/download"

http_archive(
    name = "windows_jdk",
    build_file = "//third_party:jdk.BUILD",
    strip_prefix = JDK_VERSION,
    sha256 = "0a91e179c4d34b5d905fd2945a21927a6acb798b4e8f2d528ece32c025bbcaff",
    urls = ["{base_url}/{dir}/OpenJDK16U-jdk_x64_windows_hotspot_{file}.zip".format(
        base_url = JDK_BASE_URL,
        dir = JDK_VERSION_URL_SEGMENT,
        file = JDK_VERSION_FILE_SEGMENT,
    )],
)

http_archive(
    name = "mac_jdk",
    build_file = "//third_party:jdk.BUILD",
    sha256 = "3be78eb2b0bf0a6edef2a8f543958d6e249a70c71e4d7347f9edb831135a16b8",
    strip_prefix = JDK_VERSION,
    urls = ["{base_url}/{dir}/OpenJDK16U-jdk_x64_mac_hotspot_{file}.tar.gz".format(
        base_url = JDK_BASE_URL,
        dir = JDK_VERSION_URL_SEGMENT,
        file = JDK_VERSION_FILE_SEGMENT,
    )],
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

load("@rules_java//java:repositories.bzl", "rules_java_dependencies", "remote_jdk15_repos", "rules_java_toolchains")

remote_jdk15_repos()

rules_java_dependencies()

rules_java_toolchains()

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
