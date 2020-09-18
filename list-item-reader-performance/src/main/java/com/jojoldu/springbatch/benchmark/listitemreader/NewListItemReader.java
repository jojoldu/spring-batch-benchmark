package com.jojoldu.springbatch.benchmark.listitemreader;

import org.springframework.aop.support.AopUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jojoldu@gmail.com on 18/09/2020
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class NewListItemReader<T> implements ItemReader<T> {

    private List<T> list;
    private int listSize;
    private int current = 0;

    public NewListItemReader(List<T> list) {
        // If it is a proxy we assume it knows how to deal with its own state.
        // (It's probably transaction aware.)
        if (AopUtils.isAopProxy(list)) {
            this.list = list;
        } else {
            this.list = new ArrayList<>(list);
        }

        this.listSize = this.list.size();
    }

    @Nullable
    @Override
    public T read() {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        int next = current++;
        if (next <= listSize) {
            return list.get(next);
        } else {
            return null;
        }

    }

}