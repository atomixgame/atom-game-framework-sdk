package org.netbeans.gradle.project.query;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.netbeans.api.java.queries.SourceForBinaryQuery;
import org.netbeans.spi.java.queries.SourceForBinaryQueryImplementation2;
import org.openide.filesystems.FileUtil;

public abstract class AbstractSourceForBinaryQuery implements SourceForBinaryQueryImplementation2 {
    // This cache cannot shrink because SourceForBinaryQueryImplementation
    // requires that we return the exact same object when the same URL is
    // querried. This is a very limiting constraint but I don't want to risk to
    // violate the constraint.
    private final ConcurrentMap<File, Result> cache;

    public AbstractSourceForBinaryQuery() {
        this.cache = new ConcurrentHashMap<>();
    }

    protected abstract Result tryFindSourceRoot(File binaryRoot);

    @Override
    public final Result findSourceRoots2(URL binaryRoot) {
        File binaryRootFile = FileUtil.archiveOrDirForURL(binaryRoot);
        if (binaryRootFile == null) {
            return null;
        }

        Result result = cache.get(binaryRootFile);
        if (result != null) {
            return result;
        }

        result = tryFindSourceRoot(binaryRootFile);
        if (result == null) {
            return null;
        }

        Result oldResult = cache.putIfAbsent(binaryRootFile, result);
        return oldResult != null ? oldResult : result;
    }

    @Override
    public final SourceForBinaryQuery.Result findSourceRoots(URL binaryRoot) {
        return findSourceRoots2(binaryRoot);
    }
}
