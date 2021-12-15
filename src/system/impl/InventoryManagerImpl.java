package system.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import datamodel.Article;
import datamodel.Currency;
import datamodel.Order;
import system.DataRepository;
import system.Formatter;
import system.Formatter.TableFormatter;
import system.InventoryManager;

class InventoryManagerImpl implements InventoryManager {

	/**
	 * private static singleton instance (lazy instantiation).
	 */
	private static InventoryManager instance = null;
	
	
	/**
	 * create a new ArticleRepository
	 */
	private final DataRepository.ArticleRepository articleRepository;
	
	/**
	 * internal data structure to manage inventory (unitsInStore) by Article‐id's.
	 */
	private final Map<String, Integer> inventory;

	/**
	 * Static access method to InventoryManagerImpl singleton instance (singleton
	 * pattern).
	 * 
	 * @return reference to InventoryManagerImpl singleton instance.
	 */

	public static InventoryManager getInstance(DataRepository.ArticleRepository articleRepository) {
		/*
		 * lazy instance creation (only when getInstance() is called)
		 */
		if (instance == null) {
			instance = new InventoryManagerImpl(articleRepository);
		}
		return instance;
	}

	private InventoryManagerImpl(DataRepository.ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
		this.inventory = new HashMap<>();

	}

	@Override
	public int getUnitsInStock(String id) {
		return this.inventory.get(id);
	}

	@Override
	public void update(String id, int updatedUnitsInStock) {
		if(!this.inventory.containsKey(id)){
			this.inventory.put(id, updatedUnitsInStock);
		} else{
			int oldUnitsInStock = this.inventory.get(id);
			this.inventory.put(id, oldUnitsInStock + updatedUnitsInStock);
		}
	}

	@Override
	public boolean isFillable(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fill(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StringBuffer printInventory() {
		return printInventory(0, true);
	}

	@Override
	public StringBuffer printInventory(int sortedBy, boolean decending, Integer... limit) {
		Stream<Article> articleStream = StreamSupport.stream(articleRepository.findAll().spliterator(), false);
		Stream<Article> articleStreamSort;
		//
		if (sortedBy == 1){
			articleStreamSort = articleStream.sorted(Comparator.comparingLong(p -> p.getUnitPrice()));
		} else if (sortedBy == 2){
			articleStreamSort =articleStream.sorted(Comparator.comparingLong(p -> p.getUnitPrice() * getUnitsInStock(p.getId())));
		}else if (sortedBy == 3){
			articleStreamSort = articleStream.sorted(Comparator.comparingInt(u -> getUnitsInStock(u.getId())));
		}else if(sortedBy == 4){
			articleStreamSort = articleStream.sorted(Comparator.comparing(u -> u.getDescription()));
		} else if(sortedBy == 5){
			articleStreamSort = articleStream.sorted(Comparator.comparing(u -> u.getId()));
		}else{
			articleStreamSort = StreamSupport.stream(articleRepository.findAll().spliterator(), false);
		}
		
		Formatter formatter = new FormatterImpl();
		TableFormatter tfmt = new TableFormatterImpl(formatter, new Object[][] {
				// five column table with column specs: width and alignment ('[' left, ']'
				// right)
				{ 12, '[' }, { 32, '[' }, { 12, ']' }, { 10, ']' }, { 14, ']' }
		})
				.liner("+-+-+-+-+-+") // print table header
				.hdr("||", "Inv.-Id", "Article / Unit", "Unit", "Units", "Value")
				.hdr("||", "", "", "Price", "in-Stock", "(in €)")
				.liner("+-+-+-+-+-+");
		//
		long totalValue = articleStreamSort
				.map(a -> {
					long unitsInStock = this.inventory.get(a.getId()).intValue();
					long value = a.getUnitPrice() * unitsInStock;
					tfmt.hdr("||",
							a.getId(),
							a.getDescription(),
							formatter.fmtPrice(a.getUnitPrice(), a.getCurrency()).toString(),
							Long.toString(unitsInStock),
							formatter.fmtPrice(value, a.getCurrency()).toString());
					return value;
				})
				.reduce(0L, (a, b) -> a + b);
		//
		String inventoryValue = formatter.fmtPrice(totalValue, Currency.EUR).toString();
		tfmt
				.liner("+-+-+-+-+-+")
				.hdr("", "", "Invent", "ory Value:", inventoryValue);
		//
		return tfmt.getFormatter().getBuffer();
	}

	@Override
	public Optional<Article> findById(String id) {	
		return this.articleRepository.findById(id);
	}

	@Override
	public Iterable<Article> findAll() {	
		return this.articleRepository.findAll();
	}

	@Override
	public long count() {
		return this.articleRepository.count();
	}

	@Override
	public Article save(Article article) {
		if (article == null)
			throw new IllegalArgumentException("illegal article: null");
		//
		String id = article.getId();
		if (id == null)
			throw new IllegalArgumentException("illegal article.id: null");
		//
		articleRepository.save(article); // save, make sure to avoid duplicates
		//
		if (!inventory.containsKey(id)) {
			this.inventory.put(id, Integer.valueOf(0));
		}
		return article;
	}

}
