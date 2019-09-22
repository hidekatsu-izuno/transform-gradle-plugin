package net.arnx.transform.gradle.filecopydetails;

import java.io.File;
import java.io.FilterReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.gradle.api.Transformer;
import org.gradle.api.file.ContentFilterable;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.file.RelativePath;

import groovy.lang.Closure;

public abstract class AbstractFileCopyDetails implements FileCopyDetails {
    private FileCopyDetails parent;

    public AbstractFileCopyDetails(FileCopyDetails parent) {
        this.parent = parent;
    }

    @Override
    public void copyTo(OutputStream out) {
        parent.copyTo(out);
    }

    @Override
    public boolean copyTo(File file) {
        return parent.copyTo(file);
    }

    @Override
    public File getFile() {
        return parent.getFile();
    }

    @Override
    public long getLastModified() {
        return parent.getLastModified();
    }

    @Override
    public int getMode() {
        return parent.getMode();
    }

    @Override
    public long getSize() {
        return parent.getSize();
    }

    @Override
    public boolean isDirectory() {
        return parent.isDirectory();
    }

    @Override
    public InputStream open() {
        return parent.open();
    }

    @Override
    public ContentFilterable expand(Map<String, ?> map) {
        return parent.expand(map);
    }

    @Override
    public ContentFilterable filter(Class<? extends FilterReader> filter) {
        return parent.filter(filter);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ContentFilterable filter(Closure filter) {
        return parent.filter(filter);
    }

    @Override
    public ContentFilterable filter(Transformer<String, String> filter) {
        return parent.filter(filter);
    }

    @Override
    public ContentFilterable filter(Map<String, ?> map, Class<? extends FilterReader> filter) {
        return parent.filter(map, filter);
    }

    @Override
    public void exclude() {
        parent.exclude();
    }

    @Override
    public DuplicatesStrategy getDuplicatesStrategy() {
        return parent.getDuplicatesStrategy();
    }

    @Override
    public String getName() {
        return parent.getName();
    }

    @Override
    public String getPath() {
        return parent.getPath();
    }

    @Override
    public RelativePath getRelativePath() {
        return parent.getRelativePath();
    }

    @Override
    public RelativePath getRelativeSourcePath() {
        return parent.getRelativeSourcePath();
    }

    @Override
    public String getSourceName() {
        return parent.getSourceName();
    }

    @Override
    public String getSourcePath() {
        return parent.getSourcePath();
    }

    @Override
    public void setDuplicatesStrategy(DuplicatesStrategy strategy) {
        parent.setDuplicatesStrategy(strategy);
    }

    @Override
    public void setMode(int mode) {
        parent.setMode(mode);
    }

    @Override
    public void setName(String name) {
        parent.setName(name);
    }

    @Override
    public void setPath(String path) {
        parent.setPath(path);
    }

    @Override
    public void setRelativePath(RelativePath path) {
        parent.setRelativePath(path);
    }

    @Override
    public String toString() {
        return parent.toString();
    }
}