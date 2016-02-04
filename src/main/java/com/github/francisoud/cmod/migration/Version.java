package com.github.francisoud.cmod.migration;

/**
 * http://semver.org/spec/v2.0.0.html
 */
public interface Version {
	public String MAJOR = "0";
	public String MINOR = "0";
	public String PATCH = "1";
	public String VERSION = MAJOR + "." + MINOR + "." + PATCH;
}
