package net.arnx.transform.gradle;

import org.gradle.api.Project;
import org.gradle.api.Plugin;

/**
 * A transform plugin.
 */
public class TransformGradlePlugin implements Plugin<Project> {
    public void apply(Project project) {
        project.getTasks()
            .register("transform", Transform.class);
    }
}
