package com.nobodyhub.transcendence.hub.deed.service.impl;

import com.nobodyhub.transcendence.common.merge.MergeUtils;
import com.nobodyhub.transcendence.hub.deed.client.PeopleHubClient;
import com.nobodyhub.transcendence.hub.deed.client.TopicHubClient;
import com.nobodyhub.transcendence.hub.deed.repository.DeedRepository;
import com.nobodyhub.transcendence.hub.deed.service.DeedService;
import com.nobodyhub.transcendence.hub.domain.Deed;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuComment;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import static com.nobodyhub.transcendence.hub.domain.Deed.DeedType.ZHIHU_ANSWER;
import static com.nobodyhub.transcendence.hub.domain.Deed.DeedType.ZHIHU_COMMENT;

@Service
public class DeedServiceImpl implements DeedService {
    private final DeedRepository deedRepository;
    private final TopicHubClient topicHubClient;
    private final PeopleHubClient peopleHubClient;

    public DeedServiceImpl(DeedRepository deedRepository,
                           TopicHubClient topicHubClient,
                           PeopleHubClient peopleHubClient) {
        this.deedRepository = deedRepository;
        this.topicHubClient = topicHubClient;
        this.peopleHubClient = peopleHubClient;
    }

    @Override
    public void save(ZhihuAnswer answer) {
        Optional<Deed> deed = this.deedRepository.findFirstByDataIdAndType(answer.getId(), ZHIHU_ANSWER);
        if (deed.isPresent()) {
            Deed existDeed = deed.get();
            if (ZHIHU_ANSWER.equals(existDeed.getType())) {
                ZhihuAnswer exist = existDeed.getZhihuAnswer();
                exist = MergeUtils.merge(answer, exist);
                existDeed.setZhihuAnswer(exist);
                deedRepository.save(existDeed);
                return;
            }
        }
        createNewDeedByZhihuAnswer(answer);
    }

    private Deed createNewDeedByZhihuAnswer(ZhihuAnswer answer) {
        Deed deed = new Deed();
        deed.setDataId(answer.getId());
        deed.setContent(answer.getContent());
        deed.setExcerpt(answer.getExcerpt());
        deed.setTopic(topicHubClient.getByZhihuQuestion(answer.getQuestion()));
        deed.setPeople(peopleHubClient.getByZhihuMember(answer.getAuthor()));
        deed.setCreatedAt(answer.getCreatedTime());
        deed.setType(ZHIHU_ANSWER);
        deed.setZhihuAnswer(answer);
        return deed;
    }

    @Override
    public void save(ZhihuComment comment) {
        Optional<Deed> deed = this.deedRepository.findFirstByDataIdAndType(comment.getId(), ZHIHU_COMMENT);
        if (deed.isPresent()) {
            Deed existDeed = deed.get();
            if (ZHIHU_COMMENT.equals(existDeed.getType())) {
                ZhihuComment exist = existDeed.getZhihuComment();
                exist = MergeUtils.merge(comment, exist);
                existDeed.setZhihuComment(exist);
                deedRepository.save(existDeed);
                return;
            }
        }
        createNewDeedByZhihuComment(comment);
    }

    private Deed createNewDeedByZhihuComment(ZhihuComment comment) {
        Deed deed = new Deed();
        deed.setDataId(comment.getId());
        deed.setContent(comment.getContent());
        deed.setExcerpt(comment.getContent());
        // topic is null, it should belong to answer, which is specified by the parentId
        deed.setPeople(peopleHubClient.getByZhihuMember(comment.getAuthor().getMember()));
        deed.setCreatedAt(comment.getCreatedTime());
        deed.setType(ZHIHU_COMMENT);
        deed.setZhihuComment(comment);
        deed.setParentId(comment.getAnswerId());
        return deed;
    }

    @Override
    public List<Deed> findByTopicName(String name, Pageable pageable) {
        return deedRepository.findByTopic_NameOrderByCreatedAtDesc(name, pageable);
    }

    @Override
    public List<Deed> findByPeopleName(String name, Pageable pageable) {
        return deedRepository.findByPeople_NameOrderByCreatedAtDesc(name, pageable);
    }

    @Override
    public List<Deed> findAllChilrent(String parentId, Pageable pageable) {
        return deedRepository.findByParentIdOrderByCreatedAt(parentId, pageable);
    }
}
