package Repository.Sorting;

import Entities.BaseEntity;
import Repository.Repository;

public interface SortingRepository<ID, T extends BaseEntity<ID>> extends Repository<ID, T>
{
    Iterable<T> findALL(Sort sort);
}