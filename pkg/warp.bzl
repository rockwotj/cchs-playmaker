"""Generate a warp package."""

def _impl(ctx):
    mac_output = ctx.actions.declare_file(ctx.label.name + "-macos-x64")

    warp_args = ctx.actions.args()
    warp_args.add("--arch", "macos")
    warp_args.add("--output", mac_output)
    warp_args.add("--jar", ctx.file.deploy_jar)

    # Action to call the script.
    ctx.actions.run(
        inputs = [ctx.file.deploy_jar],
        outputs = [mac_output],
        arguments = [warp_args],
        progress_message = "Generating %s" % mac_output.short_path,
        executable = ctx.executable._warp_tool,
    )
    return DefaultInfo(files = depset([mac_output]))

self_contained_jar = rule(
    implementation = _impl,
    attrs = {
        "deploy_jar": attr.label(mandatory = True, allow_single_file = True),
        "_warp_tool": attr.label(
            executable = True,
            cfg = "exec",
            allow_files = True,
            default = Label("@playmaker//java/com/github/rockwotj/tooling:WarpJrePacker"),
        ),
    },
)
