package ru.ifmo.rain.krivopaltsev.arrayset;

import java.util.*;

public class ArraySet<T> extends AbstractSet<T> implements NavigableSet<T> {
    private final List<T> data;
    private final Comparator<? super T> comparator;

    public ArraySet() {
        this(Collections.emptyList(), null);
    }

    public ArraySet(Collection<? extends T> coll) {
        this(coll, null, true);
    }

    public ArraySet(Collection<? extends T> coll, Comparator<? super T> comparator) {
        this(coll, comparator, true);
    }

    private ArraySet(Collection<? extends T> coll, Comparator<? super T> comparator, boolean sort) {
        this.comparator = comparator;
        if (sort) {
//            if (!coll.isEmpty()) {
//                List<? extends T> list = new ArrayList<>(coll);
//                list.sort(comparator);
//                data = new ArrayList<>();
//                Iterator<? extends T> it = list.iterator();
//                T prev = it.next();
//                data.add(prev);
//                while (it.hasNext()) {
//                    T temp = it.next();
//                    if (compare(prev, temp) != 0) {
//                        data.add(temp);
//                        prev = temp;
//                    }
//                }
            TreeSet<T> set = new TreeSet<>(comparator);
            set.addAll(coll);
            data = new ArrayList<>(set);
//            } else {
//                data = Collections.emptyList();
//            }
        } else {
            data = new ArrayList<>(coll);
        }
    }
//
//    private int compare(T first, T second) {
//        if (comparator != null) {
//            return comparator.compare(first, second);
//        } else {
//            return ((Comparable<T>) first).compareTo(second);
//        }
//    }

    private void checkEmpty() {
        if (data.isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public T first() {
        checkEmpty();
        return data.get(0);
    }

    @Override
    public T last() {
        checkEmpty();
        return data.get(data.size() - 1);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    private int binarySearch(T el) {
        return Collections.binarySearch(data, el, comparator);
    }

    private int ceilingIndex(T el) {
        int ind = binarySearch(el);
        return ind >= 0 ? ind : (-ind - 1);
    }

    private int floorIndex(T el) {
        int ind = binarySearch(el);
        return ind >= 0 ? ind : (-ind - 1) - 1;
    }

    private int higherIndex(T el) {
        int ind = binarySearch(el);
        return ind >= 0 ? ind + 1 : (-ind - 1);
    }

    private int lowerIndex(T el) {
        int ind = binarySearch(el);
        return ind >= 0 ? ind - 1 : (-ind - 1) - 1;
    }

    @Override
    public boolean contains(Object o) {
        return binarySearch((T) o) >= 0;
    }


    @Override
    public T lower(T t) {
        int ind = lowerIndex(t);
        return ind != -1 ? data.get(ind) : null;
    }

    @Override
    public T floor(T t) {
        int ind = floorIndex(t);
        return ind != -1 ? data.get(ind) : null;
    }

    @Override
    public T ceiling(T t) {
        int ind = ceilingIndex(t);
        return ind != data.size() ? data.get(ind) : null;
    }

    @Override
    public T higher(T t) {
        int ind = higherIndex(t);
        return ind != data.size() ? data.get(ind) : null;
    }

    @Override
    public T pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        return Collections.unmodifiableCollection(data).iterator();
    }

    @Override
    public NavigableSet<T> descendingSet() {
        return new ArraySet<>(new ReverseArrayList<>(data), Collections.reverseOrder(comparator), false);
    }

    @Override
    public Iterator<T> descendingIterator() {
        return descendingSet().iterator();
    }

    @Override
    public NavigableSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
        if (data.isEmpty()) {
            return this;
        }
        int left;
        if (fromInclusive) {
            left = ceilingIndex(fromElement);
        } else {
            left = higherIndex(fromElement);
        }
        int right;
        if (toInclusive) {
            right = floorIndex(toElement);
        } else {
            right = lowerIndex(toElement);
        }
        if (left == -1 || right == -1 || left > right) {
            return new ArraySet<>(Collections.emptyList(), comparator, false);
        }
        return new ArraySet<>(data.subList(left, right + 1), comparator, false);
    }

    @Override
    public NavigableSet<T> headSet(T toElement, boolean inclusive) {
        if (isEmpty())
            return this;
        return subSet(first(), true, toElement, inclusive);
    }

    @Override
    public NavigableSet<T> tailSet(T fromElement, boolean inclusive) {
        if (isEmpty())
            return this;
        return subSet(fromElement, inclusive, last(), true);
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return tailSet(fromElement, true);
    }
}
