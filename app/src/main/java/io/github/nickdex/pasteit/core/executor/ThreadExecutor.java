package io.github.nickdex.pasteit.core.executor;

import java.util.concurrent.Executor;

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute UseCases out of the UI thread.
 *
 * @author Nikhil Warke
 * @version 1.0
 * @since 0.16
 */
public interface ThreadExecutor extends Executor {
}
