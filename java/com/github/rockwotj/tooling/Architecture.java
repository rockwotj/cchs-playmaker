package com.github.rockwotj.tooling;

import com.beust.jcommander.converters.EnumConverter;

import java.util.Locale;

public enum Architecture {
    WINDOWS, MACOS, LINUX;

    public class OptionConverter extends EnumConverter<Architecture> {
        public OptionConverter() { super("--arch", Architecture.class); }
    }

    @Override
    public String toString() {
        return name().toLowerCase(Locale.US) + "-x64";
    }
}
