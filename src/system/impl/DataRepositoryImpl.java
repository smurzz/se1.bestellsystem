package system.impl;

import java.util.*;

import datamodel.Article;
import datamodel.Customer;
import datamodel.DataModel;
import datamodel.Order;
import system.DataRepository;

public class DataRepositoryImpl {

	private static CustomerRepository cr;
	private static ArticleRepository ar;
	private static OrderRepository or;

	CustomerRepository getCustomerRepository() {
		if (cr == null) {
			cr = new CustomerRepository();
		}
		return cr;
	}

	ArticleRepository getArticleRepository() {
		if (ar == null) {
			ar = new ArticleRepository();
		}
		return ar;
	}

	OrderRepository getOrderRepository() {
		if (or == null) {
			or = new OrderRepository();
		}
		return or;
	}

	static abstract class DataModelRepository<K extends DataModel> {

		Map<String, K> entities = new HashMap<>();

		public Optional<K> findById(String id) {
			return Optional.of(entities.get(id));
		}

		public Iterable<K> findAll() {        // return all objects stored in repository
			return entities.values();
		}

		public long count() {                         // return count of objects in repository
			return entities.size();
		}

		public K save(K entity) {
			entities.put(String.valueOf(entity.getId()), entity);
			return entity;
		}
	}

	static class CustomerRepository extends DataModelRepository<Customer> implements DataRepository.CustomerRepository {
		@Override
		public Optional<Customer> findById(long id) {
			return super.findById(String.valueOf(id));
		}
	}

	static class ArticleRepository extends DataModelRepository<Article> implements DataRepository.ArticleRepository {
	}

	static class OrderRepository extends DataModelRepository<Order> implements DataRepository.OrderRepository {

	}

}



