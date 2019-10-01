package net.arnx.gradle.plugins;

import org.gradle.api.Project;

import net.arnx.gradle.api.transform.TransformSync;

import org.gradle.api.Plugin;

/**
 * A transform-sync plugin.
 */
public class TransformSyncPlugin implements Plugin<Project> {
    public void apply(Project project) {
        project.getTasks()
            .register("transformSync", TransformSync.class);
    }
}
