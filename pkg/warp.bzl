"""Generate a warp package.
The example below executes the binary target "//actions_run:merge" with
some arguments. The binary will be automatically built by Bazel.
The rule must declare its dependencies. To do that, we pass the target to
the attribute "_merge_tool". Since it starts with an underscore, it is private
and users cannot redefine it.
"""


def _impl(ctx):
    # The list of arguments we pass to the script.
    warp_args = ctx.actions.args()
    warp_args.add("--arch", ctx.attr.arch)
    warp_args.add("--runner", ctx.file.runner)
    warp_args.add_all(ctx.files.inputs)
    warp_args.add("--output", ctx.outputs.out)

    # Action to call the script.
    ctx.actions.run(
        inputs = ctx.files.inputs + [ctx.file.runner],
        outputs = [ctx.outputs.out],
        arguments = [warp_args],
        progress_message = "Generating %s" % ctx.outputs.out.short_path,
        executable = ctx.executable._warp_tool,
    )

warp_pkg = rule(
    implementation = _impl,
    attrs = {
        "arch": attr.string(mandatory = True, values=['macos', 'linux', 'windows']),
        "inputs": attr.label_list(mandatory = True, allow_files = True),
        "runner": attr.label(mandatory = True, allow_single_file = True),
        "out": attr.output(mandatory = True),
        "_warp_tool": attr.label(
            executable = True,
            cfg = "exec",
            allow_files = True,
            default = Label("//java/com/github/rockwotj/tooling:WarpCreator"),
        ),
    },
)
