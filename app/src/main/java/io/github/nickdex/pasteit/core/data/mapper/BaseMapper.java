package io.github.nickdex.pasteit.core.data.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Mapper class to convert Object of Type T1 to Type T2 or vice versa.
 *
 * @param <T1> Object of Type 1
 * @param <T2> Object of Type 2
 */
public abstract class BaseMapper<T1, T2> {

    public abstract T1 mapToFirst(T2 o2);

    public abstract T2 mapToSecond(T1 o1);

    public List<T1> mapToFirst(List<T2> o2List) {
        List<T1> o1List = null;
        if (o2List != null) {
            o1List = new ArrayList<>();
            T1 o1;
            for (T2 o2 : o2List) {
                o1 = mapToFirst(o2);
                o1List.add(o1);
            }
        }
        return o1List;
    }

    public List<T2> mapToSecond(List<T1> o1List) {
        List<T2> o2List = null;
        if (o1List != null) {
            o2List = new ArrayList<>();
            T2 o2;
            for (T1 o1 : o1List) {
                o2 = mapToSecond(o1);
                o2List.add(o2);
            }
        }
        return o2List;
    }
}
