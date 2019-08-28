package modelos;

import java.util.ArrayList;

public class ProductoDAO {

	public ProductoDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Producto buscarItem(ArrayList<Producto> listaProductos, String codigo) {
		for (Producto producto : listaProductos) {
			if (producto.getCodigo() == codigo || producto.getCodigo().equals(codigo)) {
				return producto;
			}
		}
		return null;
	}

	public ArrayList<Producto> productosCategoria(ArrayList<Producto> listaProductos, String categoria) {
		ArrayList<Producto> products = new ArrayList<Producto>();
		for (Producto producto : listaProductos) {
			if (producto.getCategoria() == categoria || producto.getCategoria().equals(categoria)) {
				products.add(producto);
			}
		}
		return products;
	}

	public ArrayList<Producto> productosNombre(ArrayList<Producto> listaProductos, String nombre) {
		ArrayList<Producto> products = new ArrayList<Producto>();
		for (Producto producto : listaProductos) {
			if (producto.getNombre().toLowerCase().matches(".*" + nombre.toLowerCase() + ".*")) {
				products.add(producto);
			}
		}
		return products;
	}

	public ArrayList<Producto> listaProductos() {
		ArrayList<Producto> products = new ArrayList<Producto>();
		// Productos categoria dulces
		Producto producto1 = new Producto("A123", "Nutella Ferrero Chocolate Hazelnut Spread 26.5oz (750 g)",
				(float) 8.5, "Dulces", "/img/products/A123.png");
		Producto producto2 = new Producto("A124", "Hershey's Chocolate Syrup 24 oz (680 g)", (float) 4.14, "Dulces",
				"/img/products/A124.png");
		Producto producto3 = new Producto("A125", "Oreo Thins Sandwich Cookies, 10.1 oz (287 g)", (float) 3.96,
				"Dulces", "/img/products/A125.png");
		Producto producto4 = new Producto("A126",
				"M&M's Sharing Size Peanut Butter Milk Chocolate Candy 9.6 oz (272.2 g)", (float) 3.62, "Dulces",
				"/img/products/A126.png");
		Producto producto22 = new Producto("A127", "Chocolates dandy, 18 unidades ", (float) 5.5, "Dulces",
				"/img/products/A127.jpg");

		// Productos categoria Salsas
		Producto producto5 = new Producto("A005", "Barilla Spicy Marinara Salsa 24 oz (680 g)", (float) 4.5, "Salsas",
				"/img/products/A005.png");
		Producto producto6 = new Producto("A006", "Great Value Mayonesa 532ML", (float) 3.6, "Salsas",
				"/img/products/A006.png");
		Producto producto7 = new Producto("A007", "Heinz Salsa BBQ Estilo Texas Bold & Spicy 19.1oz", (float) 2.32,
				"Salsas", "/img/products/A007.png");
		Producto producto8 = new Producto("A008", "Hunt's Salsa Tomate 15.00 oz (425 g)", (float) 1.13, "Salsas",
				"/img/products/A008.png");
		Producto producto9 = new Producto("A009", "Savory Sald Dressing Blue Cheese Aderezo 16 oz (473 ml)",
				(float) 1.45, "Salsas", "/img/products/A009.png");

		// Productos categoria telefonia
		Producto producto10 = new Producto("A010", "Xiaomi Mi 8 Lite", (float) 410, "Telefonía",
				"/img/products/A010.png");
		Producto producto11 = new Producto("A011", "Samsung J7 Star", (float) 190, "Telefonía",
				"/img/products/A011.png");
		Producto producto12 = new Producto("A012", "Huawei Honor 8A", (float) 180, "Telefonía",
				"/img/products/A012.png");
		Producto producto13 = new Producto("A013", "Smooth Snap Mini 2", (float) 20, "Telefonía",
				"/img/products/A013.png");

		// Productos categoria enlatados
		Producto producto14 = new Producto("A014", "Kirkland Salmon Rosado 6 oz (170 g)", (float) 4.15, "Enlatados",
				"/img/products/A014.png");
		Producto producto15 = new Producto("A015", "Bumble Bee Chunk Light Atun Aceite 5oz (142 g)", (float) 1.75,
				"Enlatados", "/img/products/A015.png");
		Producto producto16 = new Producto("A016", "Bulldog Sardinas en Salsa Picante 3.75oz (106 g)", (float) 1.30,
				"Enlatados", "/img/products/A016.png");
		Producto producto17 = new Producto("A017", "Starkist Chunk Light Atun en Aceite 5 oz (142 g)", (float) 1.70,
				"Enlatados", "/img/products/A017.png");

		// Productos categoria reposteria
		Producto producto18 = new Producto("A018", "Krusteaz Mezcla de panqueca Buttermilk Mix 32 oz (907 g)",
				(float) 2.68, "Reposteria", "/img/products/A018.png");
		Producto producto19 = new Producto("A019", "Mezcla Brownie Triple Triple Chunk Supreme 17.8 oz (504 g)",
				(float) 2.64, "Reposteria", "/img/products/A019.png");
		Producto producto20 = new Producto("A020", "Hungry Jack Mezcla Panqueca y Wafles 32 oz (907 g)", (float) 4.5,
				"Reposteria", "/img/products/A020.png");
		Producto producto21 = new Producto("A021",
				"Duncan Hines Signature Mezcla para pastel Confetti 15.25 oz (432 g)", (float) 2.05, "Reposteria",
				"/img/products/A021.png");

		products.add(producto1);
		products.add(producto2);
		products.add(producto3);
		products.add(producto4);
		products.add(producto5);
		products.add(producto6);
		products.add(producto7);
		products.add(producto8);
		products.add(producto9);
		products.add(producto10);
		products.add(producto11);
		products.add(producto12);
		products.add(producto13);
		products.add(producto14);
		products.add(producto15);
		products.add(producto16);
		products.add(producto17);
		products.add(producto18);
		products.add(producto19);
		products.add(producto20);
		products.add(producto21);
		products.add(producto22);
		return products;
	}

}
