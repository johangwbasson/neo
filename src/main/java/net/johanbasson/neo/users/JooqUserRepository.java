package net.johanbasson.neo.users;

import net.johanbasson.neo.database.jooq.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;

import static net.johanbasson.neo.database.jooq.tables.Users.USERS;

@Repository
public class JooqUserRepository implements UserRepository {

    private final DSLContext dslContext;
    private final RecordMapper<UsersRecord, User> userMapper = r -> new User(r.getId(), r.getEmail(), r.getHash(), Collections.singleton(Role.valueOf(r.getRole())));


    public JooqUserRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return dslContext.selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOptional(userMapper);
    }

    @Override
    public void insert(User u) {
        dslContext.insertInto(USERS)
                .set(USERS.ID, u.id())
                .set(USERS.EMAIL, u.email())
                .set(USERS.HASH, u.hash())
                .set(USERS.ROLE, u.roles().stream().iterator().next().name())
                .execute();
    }
}
