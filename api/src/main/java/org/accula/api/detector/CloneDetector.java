package org.accula.api.detector;

import lombok.Builder;
import lombok.Value;
import org.accula.api.code.FileEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.function.Supplier;

/**
 * @author Vadim Dyachkov
 * @author Anton Lamtev
 */
public interface CloneDetector {
    /**
     * Find clones inside {@code targetFiles} that could be copied from {@code sourceFiles}.
     * Each element emitted by the resulting {@code Flux} is a pair of:
     * - a snippet from target file
     * - a snippet from source file
     */
    Flux<Tuple2<CodeSnippet, CodeSnippet>> findClones(Flux<FileEntity> targetFiles, Flux<FileEntity> sourceFiles);

    interface ConfigProvider extends Supplier<Mono<Config>> {
        @Override
        Mono<Config> get();
    }

    @Builder
    @Value
    class Config {
        int minCloneLength;
    }
}
