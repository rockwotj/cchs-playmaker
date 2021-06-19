"""Generate a warp package."""

def _generate_binary(ctx, arch, output):
    warp_args = ctx.actions.args()
    warp_args.add("--arch", arch)
    warp_args.add("--output", output)
    warp_args.add("--jar", ctx.file.deploy_jar)
    # TODO: Support bundling jre in optimized compilations
    warp_args.add_all(ctx.files.aux_files)

    ctx.actions.run(
        inputs = [ctx.file.deploy_jar] + ctx.files.aux_files,
        outputs = [output],
        arguments = [warp_args],
        progress_message = "Generating %s" % mac_output.short_path,
        executable = ctx.executable._warp_tool,
    )

def _impl(ctx):
    mac_output = ctx.actions.declare_file(ctx.label.name + "-macos-x64")
    # linux_output = ctx.actions.declare_file(ctx.label.name + "-linux-x64")
    # windows_output = ctx.actions.declare_file(ctx.label.name + "-windows-x64.exe")

    _generate_binary(ctx, "macos", mac_output)
    # _generate_binary(ctx, "linux", linux_output)
    # _generate_binary(ctx, "windows", windows_output)

    return DefaultInfo(
        files = depset([
            mac_output,
            # linux_output,
            # windows_output,
        ]),
    )

self_contained_jar = rule(
    implementation = _impl,
    attrs = {
        "deploy_jar": attr.label(mandatory = True, allow_single_file = True),
        "aux_files": attr.label_list(allow_files = True),
        "_warp_tool": attr.label(
            executable = True,
            cfg = "exec",
            allow_files = True,
            default = Label("@playmaker//java/com/github/rockwotj/tooling:WarpJrePacker"),
        ),
    },
)
