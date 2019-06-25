package server.strategy;

import domain.model.AbstractEntity;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EntitiesByCategoryStrategyImpl<T extends AbstractEntity> implements EntitiesByCategoryStrategy<T> {

    private Comparator comparator;

    @Override
    public Map<String, List<T>> entitiesByCategory(Iterable<T> iterable) {
        Map<String, List<T>> multimap = new HashMap<>();
        List<T> entityList = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
        Collections.sort(entityList, comparator);
        entityList.forEach(entity -> {
            multimap.putIfAbsent(entity.getCategory(), new ArrayList<>());
            multimap.get(entity.getCategory()).add(entity);
        });
        return multimap;
    }

    public Comparator getComparator() {
        return comparator;
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }
}
