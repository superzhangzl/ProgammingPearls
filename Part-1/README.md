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

具体算法和测试代码[点击跳转](./src/question/Q1.java)

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



#### 2. 如何使用位逻辑运算（如与、或、移位）来实现位向量？

 位向量的作用就是用“位”来存储一个数，以N=1000_0000个数为例，则每一位代表一个数。通常情况下，我们使用数组来存储对应位置的数，例如：`nums[5]=1`说明当前序列中存在数值5。但是当数据量急剧增加或者最大数较大或者序列过于稀疏，当前方式的空间开销非常大且容易造成严重的浪费。

此时可以引入位向量的概念， int型的数有4个字节，也就是32位，那么我们可以用[N/32]个int型数来表示这N个数。（注：[x]表示向上取整。）

a[0]表示第1~32个数（0~31）

a[1]表示第33~64个数（32~63）

…

这样，每当输入一个数字`i`，我们应该先找到该数字在数组的第几个元素中，也就是`a[?]`，然后再确定在这个元素的第几位中。

举个例子来说，比如输入35，那么35/32为1余3，则应该将a[1]的第4位置为1。

```java
public static final int BIT_SIZE = 32;
public static final int SHIFT = 5;
public static final int MASK = 0x1F;
public static final int N = 1000_0000;
public static final int[] arr = new int[1 + N / BIT_SIZE];

// 设置
public static void set(int i) {
    arr[i >> SHIFT] |= (1 << (i & MASK));
}
// 清除
public static void clear(int i) {
    arr[i >> SHIFT] &= ~(1 << (i & MASK));
}
// 测试
public static int test(int i) {
    return arr[i >> SHIFT] & (1 << (i & MASK));
}
```

具体如何使用逻辑运算符实现位向量，查阅参考资料：

> 根据题目的要求，我们不可以用/运算符来设计程序，那除的话我们可以用右移来替代：
>
> `m >> n`，表示m往右移动n位
>
> 输入i，除以32相当于往右移动5位，则`i >> SHIFT`代表i/32得到应该放在数组的第几个元素中，然后要置相应的位置位1了：
>
> 先来看看`1 << (i & MASK)`是什么意思。`i & MASK`相当于取i右移掉的部分，说白了就是取余数。
>
> > 比如35的二进制表示是：… 0010 0011
> >
> > MASK的二进制是0001 1111
> >
> > 两个相与操作得到0 0011
> >
> > 而右移5位，移掉的数是0 0011，换算成10进制是3，正是余数，与上面的操作值相等，都是0 0011。
>
> 因此`1 << (i & MASK)`就变成了1<<3，也就是将1右移3位，变成了1000。
>
> 然后在做一个`|`操作就将a[1]的第4位置1了。
>
> 
>
> 对于clear函数，就是找到位置，然后清零
>
> 对于test函数，就是找到位置，做一个与操作，如果存在这个数，则返回1，不存在的话，因为是`&`操作，所以返回0。

具体算法和测试代码[点击跳转](./src/question/Q2.java)

参考资料：

- [如何使用位逻辑运算来实现位向量的理解](https://www.cnblogs.com/marsdu/p/3181734.html)