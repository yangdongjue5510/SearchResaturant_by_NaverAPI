package com.example.searchresaturant_by_naverapi.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryDBRepositoryAbstract<T extends MemoryDBEntity> implements MemoryDBRepositoryIfs<T> {

    private final List<T> db = new ArrayList<>();
    private int index = 0;


    //데이터 찾기
    @Override
    public Optional<T> findById(int index) {
        return db.stream().filter(it -> it.getIndex() ==index).findFirst();
    }

    //동일 데이터가 이미 잇는 경우와 그렇지 않은 경우 구별해서 넣기
    @Override
    public T save(T entity) {

        Optional<T> optionalEntity = db.stream().filter(it -> it.getIndex() == entity.getIndex()).findFirst();

        if(optionalEntity.isEmpty()){
            index++;
            entity.setIndex(index);
            db.add(entity);
            return entity;
        }else{ //원래 있던걸 지우고 지금 걸 넣음.
            int preIndex = optionalEntity.get().getIndex();
            entity.setIndex(preIndex);

            deleteById(preIndex);
            db.add(entity);
            return entity;
        }
    }

    @Override
    public void deleteById(int index) {
        Optional<T> optionalEntity = db.stream().filter(it -> it.getIndex() == index).findFirst();
        if(optionalEntity.isPresent()){
            db.remove(optionalEntity);
        }
    }

    @Override
    public List<T> listAll() {
        return db;
    }
}
