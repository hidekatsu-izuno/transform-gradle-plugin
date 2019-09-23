package net.arnx.transform.gradle;

import org.gradle.api.tasks.Sync;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.gradle.api.InvalidUserDataException;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.file.FileVisitDetails;
import org.gradle.api.file.FileVisitor;
import org.gradle.api.file.RelativePath;
import org.gradle.api.internal.file.CopyActionProcessingStreamAction;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.api.internal.file.collections.DirectoryFileTreeFactory;
import org.gradle.api.internal.file.collections.MinimalFileTree;
import org.gradle.api.internal.file.copy.CopyAction;
import org.gradle.api.internal.file.copy.CopyActionProcessingStream;
import org.gradle.api.internal.file.copy.FileCopyDetailsInternal;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.WorkResult;
import org.gradle.api.tasks.WorkResults;
import org.gradle.api.tasks.util.PatternFilterable;
import org.gradle.api.tasks.util.PatternSet;
import org.gradle.util.GFileUtils;

import groovy.lang.Closure;

public class TransformTask extends Sync {
    private Closure<?> processAction;

    public void process(Closure<?> processAction) {
        this.processAction = processAction;
    }

    @Override
    protected CopyAction createCopyAction() {
        File destinationDir = getDestinationDir();
        if (destinationDir == null) {
            throw new InvalidUserDataException("No destination directory has been specified, use 'into' to specify a target directory.");
        }
        return new TransformAction(
            destinationDir,
            getFileLookup().getFileResolver(destinationDir),
            getPreserve(),
            getDirectoryFileTreeFactory(),
            processAction
        );
    }

    private static class TransformAction implements CopyAction {
        private final File baseDestDir;
        private final FileResolver resolver;
        private final PatternSet preserveSet;
        private final Spec<FileTreeElement> preserveSpec;
        private final DirectoryFileTreeFactory directoryFileTreeFactory;
        private final Closure<?> processAction;
    
        public TransformAction(
            File baseDestDir,
            FileResolver resolver,
            PatternFilterable patternFilterable,
            DirectoryFileTreeFactory directoryFileTreeFactory,
            Closure<?> processAction
        ) {
            this.baseDestDir = baseDestDir;
            this.resolver = resolver;
            this.preserveSet = new PatternSet();
            if (patternFilterable != null) {
                preserveSet.include(patternFilterable.getIncludes());
                preserveSet.exclude(patternFilterable.getExcludes());
            }
            this.preserveSpec = preserveSet.getAsSpec();
            this.directoryFileTreeFactory = directoryFileTreeFactory;
            this.processAction = processAction;
        }

        @Override
        public WorkResult execute(CopyActionProcessingStream stream) {
            final Set<RelativePath> visited = new HashSet<>();

            AtomicBoolean didWork = new AtomicBoolean();
            
            stream.process(new CopyActionProcessingStreamAction() {
                public void processFile(FileCopyDetailsInternal details) {
                    visited.add(details.getRelativePath());

                    File target = resolver.resolve(details.getRelativePath().getPathString());

                    boolean processed;
                    if (processAction != null) {
                        Object result = processAction.call(new TransformFileCopyDetails(details), target);
                        processed = !Boolean.FALSE.equals(result);
                    } else {
                        processed = details.copyTo(target);
                    }
                    if (processed) {
                        didWork.set(true);
                    }
                }
            });

            MinimalFileTree walker = directoryFileTreeFactory.create(baseDestDir).postfix();
            walker.visit(new FileVisitor() {
                public void visitDir(FileVisitDetails dirDetails) {
                    maybeDelete(dirDetails, true);
                }

                public void visitFile(FileVisitDetails fileDetails) {
                    maybeDelete(fileDetails, false);
                }

                private void maybeDelete(FileVisitDetails fileDetails, boolean isDir) {
                    RelativePath path = fileDetails.getRelativePath();
                    if (!visited.contains(path)) {
                        if (preserveSet.isEmpty() || !preserveSpec.isSatisfiedBy(fileDetails)) {
                            if (isDir) {
                                GFileUtils.deleteDirectory(fileDetails.getFile());
                            } else {
                                GFileUtils.deleteQuietly(fileDetails.getFile());
                            }
                            didWork.set(true);
                        }
                    }
                }
            });
            visited.clear();

            return WorkResults.didWork(didWork.get());
        }
    }
}
