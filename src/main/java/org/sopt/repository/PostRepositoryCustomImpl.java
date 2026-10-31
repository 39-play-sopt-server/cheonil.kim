package org.sopt.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static org.sopt.domain.QMember.member;
import static org.sopt.domain.QPost.post;

public class PostRepositoryCustomImpl implements PostRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public PostRepositoryCustomImpl(JPAQueryFactory queryFactory){
        this.queryFactory=queryFactory;
    }

    @Override
    public Page<Post> search(PostSearchCondition condition, Pageable pageable){
        List<Post> content = queryFactory
                .selectFrom(post)
                .join(post.member, member).fetchJoin()
                .where(
                        titleStartsWith(condition.title()),
                        categoryEq(condition.category()),
                        memberIdEq(condition.memberId())
                )
                .orderBy(post.createdAt.desc(), post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(
                        titleStartsWith(condition.title()),
                        categoryEq(condition.category()),
                        memberIdEq(condition.memberId())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression titleStartsWith(String title) {
        return (title == null || title.isBlank()) ? null : post.title.startsWith(title);
    }

    private BooleanExpression categoryEq(PostCategory category) {
        return (category == null) ? null : post.category.eq(category);
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return (memberId == null) ? null : post.member.id.eq(memberId);
    }
}
