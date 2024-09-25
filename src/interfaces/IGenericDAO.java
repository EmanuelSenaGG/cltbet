package interfaces;

public interface IGenericDAO<T,Type> {
	void insert(T obj);
	void delete(Type id);
	T update(T obj);
	T getByID(Type id);
}
