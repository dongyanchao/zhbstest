package com.zh.list;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by zhanghao on 2018/4/14.
 * 实现arraylist
 * 为了尽量自己写，不继承任何其他类
 * 原ArrayList实现中，有很多细节部分是为了加快运行速度。此实现点到为止，取其精华，理解精髓并带着思考自己实现。
 * 实现简单的增删和迭代器
 */
public class ZhArrayList<E> implements List<E> {
    private int size = 0;

    private static final int DEFAULT_LENGTH = 10;

    private static Object[] elementData = new Object[DEFAULT_LENGTH];

    /**
     * 若指定大小，则创建该大小的数组
     *
     * @param size
     */
    public ZhArrayList(int size) {
        this.size = size;
        this.elementData = new Object[size];
    }

    public ZhArrayList() {
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return elementData.length == 0 ? true : false;
    }

    @Override
    public boolean contains(Object o) {
        if (indexOf(o) > 0) return true;
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        //TODO
        return new ZhItr();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        checkRange(0);
        elementData[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index < 0) return false;
        remove(index);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {

        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        this.elementData = new Object[DEFAULT_LENGTH];
    }

    @Override
    public E get(int index) {

        return (E) elementData[index];
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {
        checkRange(index);
        elementData[index] = element;
        if (index > size) {
            size = index + 1;
        }
    }

    @Override
    public E remove(int index) {
        if (index > size - 1)
            return null;
        E element = (E) elementData[index];
        System.arraycopy(elementData, index + 1, elementData, index, size - index);
        size--;
        return element;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i] == null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--)
                if (elementData[i] == null)
                    return i;
        } else {
            for (int i = size - 1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    /**
     * 原实现是返回一个截取片段的视图。即返回的list和原list还有联系
     * 没想通为啥这么设计，等想通了再实现
     * 简单的截取用System.arraycopy就可以实现了
     *
     * @param fromIndex
     * @param toIndex
     * @return
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    /**
     * 检查list空间是否足够，不够的话扩容
     */
    private void checkRange(int index) {

        if (size + 1 > elementData.length || index >= elementData.length) {
            group();
        }
    }

    /**
     * 增长为原来的1.5倍
     */
    private void group() {
        //巧妙的写法，>>的意思是向左移动一位，即除2
        int newCapacity = elementData.length + (elementData.length >> 1);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    /**
     * 内部类 迭代器
     * 明细jdk的实现更优雅快速，但是这是我自己的实现，明白哪不如人家就好了
     */
    private class ZhItr implements Iterator<E> {
        //内部计数器
        private int index = 0;

        /**
         * 这么看来在add（index，o）的时候 应该限制不能超出size
         *
         * @return
         */
        @Override
        public boolean hasNext() {
            if (index >= size) return false;
            return true;
        }

        @Override
        public E next() {
            if (index >= size) return null;
            return (E) elementData[index++];
        }

        @Override
        public void remove() {
            ZhArrayList.this.remove(index);
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {

        }

        /**
         * 此方法是为了确定在此迭代器有效时，list没有被修改过
         * 大神的细节考虑还是周全啊
         * 不过这是多少年的精华结晶
         */
        final void checkForComodification() {
//            if (modCount != expectedModCount)
//                throw new ConcurrentModificationException();
        }
    }
}
