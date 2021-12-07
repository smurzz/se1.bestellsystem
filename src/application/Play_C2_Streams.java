package application;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Play_C2_Streams {
	public static void main(String[] args) {
		Stream<String> names = List.of("Hendricks", "Raymond", "Pena", "Gonzalez", "Nielsen", "Hamilton", "Graham",
				"Gill", "Vance", "Howe", "Ray", "Talley", "Brock", "Hall", "Gomez", "Bernard", "Witt", "Joyner",
				"Rutledge", "Petty", "Strong", "Soto", "Duncan", "Lott", "Case", "Richardson", "Crane", "Cleveland",
				"Casey", "Buckner", "Hardin", "Marquez", "Navarro").stream();

		names
		.sorted()
		.sorted(Comparator.comparingInt(n -> n.length()))
		.forEach(n -> System.out.println(n));

//		names
//		.filter(m -> m.contains("ez"))
//		.toList()
//		.forEach(n -> System.out.println(n + ", "));
	}
}
