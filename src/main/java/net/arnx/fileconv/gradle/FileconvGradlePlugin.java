package net.arnx.fileconv.gradle;

import org.gradle.api.Project;
import org.gradle.api.Plugin;

/**
 * A simple 'hello world' plugin.
 */
public class FileconvGradlePlugin implements Plugin<Project> {
    public void apply(Project project) {
        // Register a task
        project.getTasks().register("fileconv", task -> {
            task.doLast(s -> System.out.println("Hello from plugin 'net.arnx.fileconv'"));
        });
    }
}
