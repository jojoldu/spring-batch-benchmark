# ListItemReader Performance Benchmark

ListItemReader uses ```list.remove()``` in the ```read()``` method.
```list.remove()``` works inefficiently in arrayList.

```java
@Nullable
@Override
public T read() {
    if (!list.isEmpty()) {
        return list.remove(0);
    }
    return null;
}
```

Compare this with the test code.  
  
**TestCode**

```java
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
    void linked_reader_test() throws Exception {
        //given
        LinkedListItemReader<String> reader = new LinkedListItemReader<>(list);

        //when
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }
}
```
## Result 1. ArrayList ItemReader (Origin)

```java
public ListItemReader(List<T> list) {
    // If it is a proxy we assume it knows how to deal with its own state.
    // (It's probably transaction aware.)
    if (AopUtils.isAopProxy(list)) {
        this.list = list;
    }
    else {
        this.list = new ArrayList<>(list);
    }
}
```

![result1](./images/result1.png)

## Result 2. LinkedList ItemReader (New)

```java
public LinkedListItemReader(List<T> list) {
    // If it is a proxy we assume it knows how to deal with its own state.
    // (It's probably transaction aware.)
    if (AopUtils.isAopProxy(list)) {
        this.list = list;
    }
    else {
        this.list = new LinkedList<>(list);
    }
}
```
![result2](./images/result2.png)