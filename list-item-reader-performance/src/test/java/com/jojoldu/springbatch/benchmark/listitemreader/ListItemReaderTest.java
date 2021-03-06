package com.jojoldu.springbatch.benchmark.listitemreader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.support.ListItemReader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jojoldu@gmail.com on 18/09/2020
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@ExtendWith(MockitoExtension.class)
public class ListItemReaderTest {
    private List<String> list = new ArrayList<>();
    private final int size = 1_000_000;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < size; i++) {
             list.add("a");
        }
    }

    @Test
    void origin_reader_test() throws Exception {
        //given
        ListItemReader<String> reader = new ListItemReader<>(list);

        //when
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    @Test
    void origin_reader_LinkedList_test() throws Exception {
        //given
        List<String> linkedList = new LinkedList<>(list);
        ListItemReader<String> reader = new ListItemReader<>(linkedList);

        //when
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    @Test
    void linked_reader_test() throws Exception {
        //given
        LinkedListItemReader<String> reader = new LinkedListItemReader<>(list);

        //when
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }
}
