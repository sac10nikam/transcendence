package com.nobodyhub.transcendence.zhihu.answer.service;

import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiAnswer;

import java.util.List;
import java.util.Optional;

public interface ZhihuAnswerApiService {
    /**
     * Get the answer of given id
     * http://www.zhihu.com/api/v4/answers/360364772?include=data%5B%2A%5D.is_normal%2Cis_collapsed%2Ccollapse_reason%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Cmark_infos%2Ccreated_time%2Cupdated_time%2Crelationship.is_authorized%2Cvoting%2Cis_author%2Cis_thanked%2Cis_nothelp%2Cupvoted_followees&limit=100&offset=10&data%5B%2A%5D.author.badge%5B%3F%28type%3Dbest_answerer%29%5D.topics=
     *
     * @param answerId
     * @return
     */
    Optional<ZhihuApiAnswer> getById(String answerId);

    /**
     * Get the answers of given member
     * https://www.zhihu.com/api/v4/members/yun-dong-shan/answers?sort_by=default&include=data%5B%2A%5D.is_normal%2Cis_collapsed%2Ccollapse_reason%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Cmark_infos%2Ccreated_time%2Cupdated_time%2Crelationship.is_authorized%2Cvoting%2Cis_author%2Cis_thanked%2Cis_nothelp%2Cupvoted_followees&limit=100&offset=10&data%5B%2A%5D.author.badge%5B%3F%28type%3Dbest_answerer%29%5D.topics=
     *
     * @param memberToken
     * @return
     */
    List<ZhihuApiAnswer> getByMember(String memberToken);

    /**
     * Get the answer of given question
     * https://www.zhihu.com/api/v4/questions/60531155/answers?offset=0&limit=62&sort_by=default
     *
     * @param questionId
     * @return
     */
    List<ZhihuApiAnswer> getByQuestion(String questionId);
}
