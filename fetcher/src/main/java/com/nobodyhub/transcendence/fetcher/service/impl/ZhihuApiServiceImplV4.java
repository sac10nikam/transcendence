package com.nobodyhub.transcendence.fetcher.service.impl;

import com.google.common.collect.Lists;
import com.nobodyhub.transcendence.fetcher.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.fetcher.domain.ZhihuAnswerList;
import com.nobodyhub.transcendence.fetcher.domain.ZhihuAuthor;
import com.nobodyhub.transcendence.fetcher.service.ZhihuApiService;
import com.nobodyhub.transcendence.fetcher.service.ZhihuUrlConvertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@ConditionalOnProperty(name = "zhihu.api.version", havingValue = "4")
@Service
@Slf4j
public class ZhihuApiServiceImplV4 implements ZhihuApiService {
    private final int limit;
    private final String sortBy;
    private final RestTemplate restTemplate;
    private final ZhihuUrlConvertService zhihuUrlConvertService;

    @Autowired
    public ZhihuApiServiceImplV4(RestTemplate restTemplate,
                                 ZhihuUrlConvertService zhihuUrlConvertService,
                                 @Value("${zhihu.api.limit}") int limit,
                                 @Value("${zhihu.api.sortBy}") String sortBy) {
        this.limit = limit;
        this.sortBy = sortBy;
        this.restTemplate = restTemplate;
        this.zhihuUrlConvertService = zhihuUrlConvertService;
    }

    @Override
    public List<ZhihuAnswer> getAnswersForQuestion(String questionId) {
        List<ZhihuAnswer> answers = Lists.newArrayList();
        Optional<ZhihuAnswerList> answerList = doFetch(
            zhihuUrlConvertService.convert(
                "/api/v4/questions/{questionId}/answers?include=data[*].is_normal,admin_closed_comment,reward_info,is_collapsed,annotation_action,annotation_detail,collapse_reason,is_sticky,collapsed_by,suggest_edit,comment_count,can_comment,content,editable_content,voteup_count,reshipment_settings,comment_permission,created_time,updated_time,review_info,relevant_info,question,excerpt,relationship.is_authorized,is_author,voting,is_thanked,is_nothelp,is_labeled;data[*].mark_infos[*].url;data[*].author.follower_count,badge[*].topics" +
                    "&limit={limit}" +
                    "&offset=0" +
                    "&sort_by={sortBy}"),
            ZhihuAnswerList.class,
            questionId, limit, sortBy);
        answerList.ifPresent(zhihuAnswerList -> {
            answers.addAll(answerList.get().getData());
            fetchMoreAnswers(zhihuAnswerList, answers);
        });
        return answers;
    }

    @Override
    public List<ZhihuAnswer> getAnswersForMember(String token) {
        List<ZhihuAnswer> answers = Lists.newArrayList();
        Optional<ZhihuAnswerList> answerList = doFetch(
            zhihuUrlConvertService.convert(
                "/api/v4/members/{token}/answers?include=data[*].is_normal,is_collapsed,collapse_reason,suggest_edit,comment_count,can_comment,content,voteup_count,reshipment_settings,comment_permission,mark_infos,created_time,updated_time,relationship.is_authorized,voting,is_author,is_thanked,is_nothelp,upvoted_followees;data[*].author.badge[?(type=best_answerer)].topics" +
                    "&limit={limit}" +
                    "&offset=0" +
                    "&sort_by={sortBy}"),
            ZhihuAnswerList.class,
            token, limit, sortBy);
        answerList.ifPresent(zhihuAnswerList -> {
            answers.addAll(answerList.get().getData());
            fetchMoreAnswers(zhihuAnswerList, answers);
        });
        return answers;
    }

    private void fetchMoreAnswers(ZhihuAnswerList answerList,
                                  @NotNull List<ZhihuAnswer> answers) {
        Assert.notNull(answerList, "The answer list can not be null!");
        //TODO: use multi-threading
        while (answerList != null
            && answerList.getPaging() != null
            && answerList.getPaging().getNext() != null
            && !answerList.getPaging().getIs_end()) {
            Optional<ZhihuAnswerList> result = doFetch(
                zhihuUrlConvertService.convert(answerList.getPaging().getNext()), ZhihuAnswerList.class);
            if (result.isPresent()) {
                answerList = result.get();
                answers.addAll(answerList.getData());
            } else {
                answerList = null;
            }
        }
    }

    @Override
    public ZhihuAuthor getAuthor(String urlToken) {
        return doFetch(
            zhihuUrlConvertService.convert(
                "/members/{urlToken}?include=allow_message,is_followed,is_following,is_org,is_blocking,employments,answer_count,follower_count,articles_count,gender,badge[*].topics"),
            ZhihuAuthor.class,
            urlToken).orElse(new ZhihuAuthor());
    }

    private <T> Optional<T> doFetch(String url, Class<T> clz, Object... urlVariables) {
        try {
            log.info("Accessing : [{}]",
                this.restTemplate.getUriTemplateHandler().expand(url, urlVariables));
            return Optional.ofNullable(this.restTemplate.getForObject(url, clz, urlVariables));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error when access URL: [{}]",
                this.restTemplate.getUriTemplateHandler().expand(url, urlVariables));
            log.error(e.getMessage());
        }
        return Optional.empty();
    }
}
