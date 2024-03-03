package com.cili.video.repository;

import com.cili.video.model.entity.VideoBaseInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Zhou JunJie
 */
public interface VideoRepository extends ElasticsearchRepository<VideoBaseInfo,Long> {
}
