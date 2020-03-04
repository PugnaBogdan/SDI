package Repository;

        import Entities.BaseEntity;
        import Entities.Validators.Validator;
        import Entities.Validators.ValidatorException;

        import java.util.*;
        import java.util.stream.Collector;
        import java.util.stream.Collectors;

/**
 * @author Rares.
 * @param <ID>
 * @param <T>
 */
public class InMemoryRepository<ID,T extends BaseEntity<ID>> implements Repository<ID,T> {

    private Map<ID,T> objects;
    private Validator<T> validator;

    public InMemoryRepository(Validator<T> initValidator)
    {
        validator=initValidator;
        objects= new HashMap<>();
    }

    @Override
    public Optional<T> findOne(ID id) {
        return Optional.empty();
        //TO DO
    }

    @Override
    public Iterable<T> findAll() {
        return new HashSet<>(objects.values());
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {

        if(entity==null)
            throw new IllegalArgumentException("id must not be null");
        validator.validate(entity);

        return Optional.ofNullable(objects.putIfAbsent(entity.getId(), entity));

    }

    /**
     * @param id must not be null.
     * @return it will delete the object.
     */

    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(objects.remove(id));
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        return Optional.empty();
        //TO DO
    }
}
