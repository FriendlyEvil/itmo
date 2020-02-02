package ru.ifmo.rain.krivopaltsev.arrayset;

import java.util.AbstractList;
import java.util.List;

public class ReverseArrayList<T> extends AbstractList<T> {
    private List<T> data;
    private boolean flag;

    ReverseArrayList(List<T> coll) {
        if (coll instanceof ReverseArrayList) {
            ReverseArrayList<T> list = (ReverseArrayList<T>) coll;
            data = list.data;
            flag = !list.flag;
        } else {
            data = coll;
            flag = true;
        }
    }

    @Override
    public T get(int index) {
        if (flag) {
            return data.get(size() - index - 1);
        } else {
            return data.get(index);
        }
    }

    @Override
    public int size() {
        return data.size();
    }
}
