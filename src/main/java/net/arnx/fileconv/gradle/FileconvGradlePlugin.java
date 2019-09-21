package net.arnx.fileconv.gradle;

import org.gradle.api.Project;
import org.gradle.api.Plugin;

/**
 * A simple 'hello world' plugin.
 */
public class FileconvGradlePlugin implements Plugin<Project> {
    public void apply(Project project) {
        project.getTasks()
            .register("fileconv", FileconvTask.class);
    }
}
