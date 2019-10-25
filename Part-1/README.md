#### 1. 如果不缺内存，如何使用一个具有库的语言来实现一种排序算法以表示和排序集合？

涉及到5个JDK集合工具类中的排序方法：

- `Collections.sort(List<T> list)`
- `Arrays.sort(Object[] a)`
- `Arrays.parallelSort(Object[] a)`
- `Arrays.sort(int[] a)`
- `Arrays.parallelSort(int[] a)`

每个方法的默认签名如下：

```java
// Collections.sort(List<T> list)
// 本质上还是使用了Arrays.sort(Object[] a)
public static <T extends Comparable<? super T>> void sort(List<T> list) {
    list.sort(null);
}
```

```java
// Arrays.sort(Object[] a)
public static void sort(Object[] a) {
    if (LegacyMergeSort.userRequested)
        legacyMergeSort(a);
    else
        ComparableTimSort.sort(a, 0, a.length, null, 0, 0);
}
```

```java
// Arrays.parallelSort(Object[] a)
public static <T extends Comparable<? super T>> void parallelSort(T[] a) {
    int n = a.length, p, g;
    if (n <= MIN_ARRAY_SORT_GRAN ||
        (p = ForkJoinPool.getCommonPoolParallelism()) == 1)
        TimSort.sort(a, 0, n, NaturalOrder.INSTANCE, null, 0, 0);
    else
        new ArraysParallelSortHelpers.FJObject.Sorter<T>
        (null, a,
         (T[])Array.newInstance(a.getClass().getComponentType(), n),
         0, n, 0, ((g = n / (p << 2)) <= MIN_ARRAY_SORT_GRAN) ?
         MIN_ARRAY_SORT_GRAN : g, NaturalOrder.INSTANCE).invoke();
}
```

```java
// Arrays.sort(int[] a)
public static void sort(int[] a) {
    DualPivotQuicksort.sort(a, 0, a.length - 1, null, 0, 0);
}
```

```java
// Arrays.parallelSort(int[] a)
public static void parallelSort(int[] a) {
    int n = a.length, p, g;
    if (n <= MIN_ARRAY_SORT_GRAN ||
        (p = ForkJoinPool.getCommonPoolParallelism()) == 1)
        DualPivotQuicksort.sort(a, 0, n - 1, null, 0, 0);
    else
        new ArraysParallelSortHelpers.FJInt.Sorter
        (null, a, new int[n], 0, n, 0,
         ((g = n / (p << 2)) <= MIN_ARRAY_SORT_GRAN) ?
         MIN_ARRAY_SORT_GRAN : g).invoke();
}
```



对相同一份1千万条无序的数据进行排序，耗时如下：(i5 8400)

| 方法                              | 耗时   |
| --------------------------------- | ------ |
| `Collections.sort(List<T> list)`  | 3.724s |
| `Arrays.sort(Object[] a)`         | 3.965s |
| `Arrays.parallelSort(Object[] a)` | 1.813s |
| `Arrays.sort(int[] a)`            | 926ms  |
| `Arrays.parallelSort(int[] a)`    | 263ms  |

由图可得，当使用基本数据类型`int`时，排序耗时为926ms，并行排序仅有264ms。而使用`Integer`对象进行排序时，耗时发生了显著的变化。使用`int`时使用`DualPivotQuicksort`排序算法，使用`Integer`时使用`ComparableTimSort`排序算法。除去两种算法本身不同的性能外，`int`和`Integer`之间的自动开箱和自动装箱也会消耗大量的时间。

具体算法机测试代码[点击跳转](./src/question/Q1.java)

涉及到的基本排序算法有：

- 归并排序
- Tim排序
- 快速排序

todo 具体的排序算法说明，暂时不做说明，有空再更新

参考资料：

- [TimSort的实现](https://mp.weixin.qq.com/s?__biz=MzI2MTY0OTg2Nw==&mid=2247483816&idx=1&sn=079af3d70efcb68efa7400f09decb59c&chksm=ea56650cdd21ec1ace7c8fd168d62feb636e4b32f9a4d90329fe479489d8e7a70e612df8920b&token=2074049324&lang=zh_CN)
- [Java源码解析-DualPivotQuicksort](https://blog.csdn.net/xjyzxx/article/details/18465661)
- [Timsort原理介绍](https://blog.csdn.net/yangzhongblog/article/details/8184707)
- [Timsort排序算法](https://www.zybuluo.com/zero1036/note/618233)



