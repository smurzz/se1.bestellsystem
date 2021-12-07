package system;

import system.DataRepository.ArticleRepository;
import system.DataRepository.CustomerRepository;
import system.DataRepository.OrderRepository;


/**
 * Public interface of a data provider that reads or writes data from/to a data source.
 * 
 * @since "0.1.0"
 * @version {@value package_info#Version}
 * @author Sofya
 *
 */

public interface DataSource {


	/**
	 * Import objects from file containing a JSON Array [ {obj1}, {obj2}, ... ].
	 * Collector is called for each JSON object to convert JSON object to Java Object
	 * and collect in a repository or a container.
	 * 
	 * @param jsonFileName name of JSON file to read
	 * @param collector called to convert JSON object to Java Object and collect in a repository or container
	 * @param limit maximum number of JSON objects
	 * @return number of objects imported
	 */

	long importCustomerJSON( String jsonFileName, CustomerRepository collector, Integer... limit );
	
	long importArticleJSON( String jsonFileName, ArticleRepository collector, Integer... limit );

	long importOrderJSON( String jsonFileName, OrderRepository collector, Integer... limit );


//	<T, ID> void importJSON( String jsonFileName, DataRepository.Repository<T, ID> collector, Integer... limit );
//	dp.<Customer, Long>importJSON( "data/customers_10.json", crep );
//	dp.<Article, String>importJSON( "data/articles_9.json", arep );
}
