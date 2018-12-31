package com.nobodyhub.transcendence.zhihu.question.service.impl;

import com.google.common.collect.Lists;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import com.nobodyhub.transcendence.zhihu.question.domain.ZhihuAnswerList;
import com.nobodyhub.transcendence.zhihu.question.service.ZhihuQuestionApiService;
import com.nobodyhub.transcendence.zhihu.question.service.ZhihuUrlConvertService;
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
public class ZhihuQuestionApiServiceImplV4 implements ZhihuQuestionApiService {
    private final int limit;
    private final String sortBy;
    private final RestTemplate restTemplate;
    private final ZhihuUrlConvertService zhihuUrlConvertService;

    @Autowired
    public ZhihuQuestionApiServiceImplV4(RestTemplate restTemplate,
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
            && !answerList.getPaging().getIsEnd()) {
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
    public ZhihuMember getAuthor(String urlToken) {
        return doFetch(
            zhihuUrlConvertService.convert(
                "/api/v4/members/{urlToken}?include=locations,employments,gender,educations,business,voteup_count,thanked_Count,follower_count,following_count,cover_url,following_topic_count,following_question_count,following_favlists_count,following_columns_count,avatar_hue,answer_count,articles_count,pins_count,question_count,columns_count,commercial_question_count,favorite_count,favorited_count,logs_count,marked_answers_count,marked_answers_text,message_thread_token,account_status,is_active,is_force_renamed,is_bind_sina,sina_weibo_url,sina_weibo_name,show_sina_weibo,is_blocking,is_blocked,is_following,is_followed,mutual_followees_count,vote_to_count,vote_from_count,thank_to_count,thank_from_count,thanked_count,description,hosted_live_count,participated_live_count,allow_message,industry_category,org_name,org_homepage,is_org"),
            ZhihuMember.class,
            urlToken).orElse(new ZhihuMember());
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
