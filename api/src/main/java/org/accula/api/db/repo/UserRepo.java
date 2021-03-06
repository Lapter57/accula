package org.accula.api.db.repo;

import org.accula.api.db.model.GithubUser;
import org.accula.api.db.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

/**
 * @author Anton Lamtev
 */
public interface UserRepo {
    Mono<User> upsert(GithubUser githubUser, String githubAccessToken);

    Mono<User> findById(Long id);

    Flux<User> findByGithubIds(Collection<Long> ids);

    void addOnUpsert(OnUpsert onUpsert);

    @FunctionalInterface
    interface OnUpsert {
        void onUpsert(Long userId);
    }
}
