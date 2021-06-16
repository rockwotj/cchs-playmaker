"""Generate a warp package.
The example below executes the binary target "//actions_run:merge" with
some arguments. The binary will be automatically built by Bazel.
The rule must declare its dependencies. To do that, we pass the target to
the attribute "_merge_tool". Since it starts with an underscore, it is private
and users cannot redefine it.
"""


def _impl(ctx):
    ctx.actions.declare_directory("foo")
    # The list of arguments we pass to the script.
    args = [
      "--arch", ctx.attr.arch,
      "--input", ctx.file.runner.dirname,
      "--runner", ctx.file.runner.basename,
      "--output", ctx.outputs.out.path,
    ]

    # Action to call the script.
    ctx.actions.run(
        inputs = ctx.files.input + [ctx.file.runner],
        outputs = [ctx.outputs.out],
        arguments = args,
        progress_message = "Generating %s" % ctx.outputs.out.short_path,
        executable = ctx.executable._warp_tool,
    )

concat = rule(
    implementation = _impl,
    attrs = {
        "arch": attr.string(mandatory = True, values=['macos-x64', 'linux-x64', 'windows-x64'])
        "input": attr.label(mandatory = True, allow_files = True),
        "runner": attr.label(mandatory = True, allow_files = True, allow_single_file = True),
        "out": attr.output(mandatory = True),
        "_warp_tool": attr.label(
            executable = True,
            cfg = "exec",
            allow_files = True,
            default = Label("//pkg:warp"),
        ),
    },
)
