package com.cili.video.es;

import com.cili.video.model.entity.VideoBaseInfo;
import com.cili.video.repository.VideoRepository;
import com.cilicili.common.utils.JsonUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName VideoEsService
 * @Description
 * @Author Zhou JunJie
 * @Date 2024/3/3 23:38
 **/
@Service
public class VideoEsService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void addVideo(VideoBaseInfo videoBaseInfo){
        videoRepository.save(videoBaseInfo);
    }

    public List<VideoBaseInfo> getVideosByKeyWord(String keyword, int pageNum, int pageSize) throws IOException {
        SearchRequest searchRequest = new SearchRequest(new String[]{"videos"});
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(pageNum - 1).size(pageSize);
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "tags", "profile");
        searchSourceBuilder.query(multiMatchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.timeout(new TimeValue(5, TimeUnit.SECONDS));
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<VideoBaseInfo> res = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            VideoBaseInfo video = JsonUtils.toObject(hit.getSourceAsString(), VideoBaseInfo.class);
            res.add(video);
        }
        return res;
    }
}
