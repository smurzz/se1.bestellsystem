package system.impl;

import java.util.Optional;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import system.Calculator;
import system.Printer;
import system.RTE;
import system.DataRepository.ArticleRepository;
import system.DataRepository.CustomerRepository;
import system.DataRepository.OrderRepository;
import system.InventoryManager;
//
import static system.RTE.Configuration.KEY_DATASOURCE;
import static system.RTE.Configuration.JSON_DATASOURCE;
import static system.RTE.Configuration.KEY_DATASOURCE_CUSTOMER;
import static system.RTE.Configuration.KEY_DATASOURCE_ARTICLE;
import static system.RTE.Configuration.KEY_DATASOURCE_ORDER;


/**
 * Local implementation of RTE (Runtime Environment) interface.
 *
 */

class RTE_Impl implements RTE {


	/**
	 * Create and configure a new Runtime instance before it is launched.
	 *  
	 * @param config functional interface to provide configuration information.
	 * @return instance of Configuration that can be launched.
	 */

	@Override
	public ConfigImpl create( Consumer<Configuration> config ) {
		final ConfigImpl cfi = new ConfigImpl();
		if( config != null ) {
			config.accept( cfi );
		}
		return cfi;
	}


	/**
	 * Private class that implements the Configuration interface. Class inherits
	 * key-value store for configuration properties from java.util.Properties.
	 *
	 */

	@SuppressWarnings("serial")
	private class ConfigImpl extends Properties implements Configuration {


		/**
		 * Store configuration Property as String key-value pair.
		 * 
		 * @param key key to store value
		 * @param value value stored
		 * @return chainable self-reference
		 */

		@Override
		public Configuration put( String key, String value ) {
			if( key != null && key.length() > 0 && value != null ) {
				super.put( key, value );
			}
			return this;
		}

		/**
		 * Retrieve value stored for key.
		 * 
		 * @param key key to access value
		 * @return value stored for key or empty
		 */

		@Override
		public Optional<String> get( String key ) {
			return Optional.ofNullable( key != null? (String)super.get( key ) : null );
		}

		/**
		 * Create and launch new Runtime from configuration.
		 * 
		 * @param runtime functional interface with Configuration and Runtime to launch.
		 * @return instance of Runtime.
		 * @throws RuntimeException thrown with errors during launch
		 */

		@Override
		public Runtime launch( BiConsumer<Configuration, Runtime> runtime ) throws RuntimeException {
			final RuntimeInstance rti = new RuntimeInstance( this );
			if( runtime != null ) {
				runtime.accept( this, rti );
			}
			return rti;
		}

	}


	/**
	 * Private class that implements the Runtime interface. A Runtime instance
	 * represents a running instance of the system. It provides access to
	 * system components.
	 *
	 */

	private class RuntimeInstance implements Runtime {

		/**
		 * Configuration instance used by Runtime instance.
		 */
		private final Configuration config;

		/**
		 * Calculator singleton instance.
		 */
		private final Calculator calculator = new CalculatorImpl();

		/**
		 * Printer instance used by Runtime instance.
		 * 
		 * Has a dependency on Calculator.
		 */
		private final Printer printer;

		/**
		 * DataRepository implementations used by Runtime instance.
		 */
		private final DataRepositoryImpl dataRepositoryImpl = new DataRepositoryImpl();

		/**
		 * InventoryManager implementation used by Runtime instance.
		 * 
		 * Has a dependency on ArticleRepository.
		 */
		private final InventoryManager inventoryManager;


		/**
		 * Private constructor.
		 * 
		 * @param config Configuration to configure Runtime instance.
		 */

		private RuntimeInstance( final Configuration config ) {
			if( config == null )
				throw new IllegalArgumentException( "config: null" );
			this.config = config;
			//
			Calculator calculator = getCalculator();
			this.printer = new PrinterImpl( calculator );	// inject dependency
			//
			ArticleRepository articleRepository = dataRepositoryImpl.getArticleRepository();
			this.inventoryManager = InventoryManagerImpl
					.getInstance( articleRepository );		// inject dependency
		}

		/**
		 * Configuration getter.
		 * 
		 * @return Configuration.
		 */

		@Override
		public Configuration getConfiguration() {
			return config;
		}
		

		/**
		 * Shutting down Runtime instance.
		 * 
		 * @param runtime functional interface invoked during shutdown.
		 * @return Runtime Environment from which Runtime instance was launched.
		 * 
		 * @throws RuntimeException thrown with errors during shutdown
		 */

		@Override
		public RTE shutdown( Consumer<Runtime> runtime ) throws RuntimeException {
			if( runtime != null ) {
				runtime.accept( this );
			}
			return InstanceAccessor.getInstance();
		}


		/**
		 * Return singleton calculator instance.
		 * 
		 * @return singleton calculator instance.
		 */

		@Override
		public Calculator getCalculator() {
			return calculator;
		}


		/**
		 * Return singleton printer instance.
		 * 
		 * @return singleton printer instance.
		 */

		@Override
		public Printer getPrinter() {
			return printer;
		}


		/**
		 * Return singleton instance of CustomerRepository.
		 * 
		 * @return singleton instance of CustomerRepository
		 */

		@Override
		public CustomerRepository getCustomerRepository() {
			return dataRepositoryImpl.getCustomerRepository();
		}


		/**
		 * Return singleton instance of ArticleRepository.
		 * 
		 * REMOVED with feat.732 that fully hides ArticleRepository
		 * inside InventoryManager.
		 * 
		 * @return singleton instance of ArticleRepository
		 */

//		@Override
//		public ArticleRepository getArticleRepository() {
//			return dataRepositoryImpl.getArticleRepository();
//		}


		/**
		 * Return singleton instance of OrderRepository.
		 * 
		 * @return singleton instance of OrderRepository
		 */

		@Override
		public OrderRepository getOrderRepository() {
			return dataRepositoryImpl.getOrderRepository();
		}


		/**
		 * Return singleton InventoryManager instance.
		 * 
		 * @return singleton InventoryManager instance.
		 */

		@Override
		public InventoryManager getInventoryManager() {
			return inventoryManager;
		}


		/**
		 * Load data into repositories during Runtime launch,
		 * Runtime.launch( (config, rt) -> { rt.loadData(); } );
		 * 
		 * @return chainable self reference.
		 */

		@Override
		public Runtime loadData() {
			config.get( KEY_DATASOURCE )
				.filter( ds -> ds.equals( JSON_DATASOURCE ) )
				.ifPresent( ds -> {
					//
					DataSource jsonData = new DataSourceImpl();
					//
					config.get( KEY_DATASOURCE_CUSTOMER ).ifPresent( jsonFileName -> {
						long count = jsonData.importCustomerJSON( jsonFileName, getCustomerRepository() );
						System.out.println( " + loaded " + count + " obj from: " + jsonFileName );
					});
					//
					config.get( KEY_DATASOURCE_ARTICLE ).ifPresent( jsonFileName -> {
//						long count = jsonData.importArticleJSON( jsonFileName, getArticleRepository() );
						long count = jsonData.importArticleJSON( jsonFileName, getInventoryManager() );
						System.out.println( " + loaded " + count + " obj from: " + jsonFileName );
					});
					//
					config.get( KEY_DATASOURCE_ORDER ).ifPresent( jsonFileName -> {
						long count = jsonData.importOrderJSON( jsonFileName, getOrderRepository() );
						System.out.println( " + loaded " + count + " obj from: " + jsonFileName );
					});
			});
			return this;
		}

	}

}
