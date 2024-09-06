package com.fang.screw.communal.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @FileName ElasticSearchTemplateImpl
 * @Description
 * @Author yaoHui
 * @date 2024-08-04
 **/
@Slf4j
public class ElasticSearchUtils {

    public static <T> T getElasticSearchById(ElasticsearchRepository<T, String> repository,String id){

        Optional<T> optionalT =  repository.findById(id);
        if(ObjectUtils.isEmpty(optionalT)){
            return null;
        }

        return optionalT.get();
    }

    public static <T> List<T> getElasticSearchByIds(ElasticsearchRepository<T, String> repository,List<String> ids){
        Iterable<T> iterable = repository.findAllById(ids);
        if(ObjectUtils.isEmpty(iterable)){
            return null;
        }
        return StreamSupport.stream(iterable.spliterator(),false).collect(Collectors.toList());
    }

    public static <T> List<T> getElasticSearchAllInfo(ElasticsearchRepository<T, String> repository){
        Iterable<T> iterable = repository.findAll();
        if(ObjectUtils.isEmpty(iterable)){
            return null;
        }
        return StreamSupport.stream(iterable.spliterator(),false).collect(Collectors.toList());
    }


}
