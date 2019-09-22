package net.arnx.transform.gradle;

import org.gradle.api.internal.file.copy.CopyAction;
import org.gradle.api.tasks.AbstractCopyTask;

public class TransformTask extends AbstractCopyTask {

    @Override
    protected CopyAction createCopyAction() {
        return null;
    }
}