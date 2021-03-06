package org.accula.api.converter;

import org.accula.api.db.model.CommitSnapshot;
import org.accula.api.db.model.GithubRepo;
import org.accula.api.db.model.GithubUser;
import org.accula.api.db.model.Pull;
import org.accula.api.github.model.GithubApiCommitSnapshot;
import org.accula.api.github.model.GithubApiPull;
import org.accula.api.github.model.GithubApiRepo;
import org.accula.api.github.model.GithubApiUser;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author Anton Lamtev
 */
@Component
@SuppressWarnings("PMD.ConfusingTernary")
public final class GithubApiToModelConverter {
    private static final String EMPTY = "";

    public GithubRepo convert(final GithubApiRepo apiRepo) {
        return new GithubRepo(
                apiRepo.getId(),
                apiRepo.getName(),
                orEmpty(apiRepo.getDescription()),
                convert(apiRepo.getOwner())
        );
    }

    public GithubUser convert(final GithubApiUser apiUser) {
        return new GithubUser(
                apiUser.getId(),
                apiUser.getLogin(),
                apiUser.getName(),
                apiUser.getAvatarUrl(),
                apiUser.getType() == GithubApiUser.Type.ORGANIZATION
        );
    }

    public GithubUser convert(final Map<String, Object> attributes) {
        final var id = ((Number) attributes.get("id")).longValue();
        final var login = (String) attributes.get("login");
        final var name = (String) attributes.get("name");
        final var avatar = (String) attributes.get("avatar_url");
        return new GithubUser(id, login, name, avatar, false);
    }

    public CommitSnapshot convert(final GithubApiCommitSnapshot snapshot, final Long pullId) {
        return CommitSnapshot.builder()
                .sha(snapshot.getSha())
                .branch(snapshot.getRef())
                .pullId(pullId)
                .repo(convert(Objects.requireNonNull(snapshot.getRepo())))
                .build();
    }

    public Pull convert(final GithubApiPull pull, final Long projectId) {
        return Pull.builder()
                .id(pull.getId())
                .number(pull.getNumber())
                .title(pull.getTitle())
                .open(pull.getState() == GithubApiPull.State.OPEN)
                .createdAt(pull.getCreatedAt())
                .updatedAt(pull.getUpdatedAt())
                .head(convert(pull.getHead(), pull.getId()))
                .base(convert(pull.getBase(), pull.getId()))
                .author(convert(pull.getUser()))
                .projectId(projectId)
                .build();
    }

    private static String orEmpty(@Nullable final String s) {
        return s != null && !s.isBlank() ? s : EMPTY;
    }
}
