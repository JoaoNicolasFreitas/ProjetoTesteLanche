package com.snack.facades;

import com.snack.applications.ProductApplication;
import com.snack.entities.Product;
import com.snack.facade.ProductFacade;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProductFacadeTest {
    ProductFacade productFacade;
    Product produtoXBurguer;
    Product produtoXSalada;

    @BeforeEach
    public void setUp() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService();
        ProductApplication productApplication = new ProductApplication(productRepository, productService);
        productFacade = new ProductFacade(productApplication);
        produtoXBurguer = new Product(10, "x-burguer", 10, "src\\test\\java\\com\\snack\\imagens\\x-burguer.jpg");
        produtoXSalada = new Product(15, "x-salada", 15, "src\\test\\java\\com\\snack\\imagens\\x-salada.jpg");
    }

    @Test
    public void deveRetornarListaCompletaDeProdutos() {



        productFacade.append(produtoXBurguer);
        productFacade.append(produtoXSalada);
        List<Product> produtos = productFacade.getAll();


        Assertions.assertEquals(2, produtos.size());
    }

    @Test
    public void deveRetornarProdutoCorretoPorIdValido() {
        productFacade.append(produtoXBurguer);

        Assertions.assertNotNull(productFacade.getById(10));
        Assertions.assertEquals("x-burguer", productFacade.getById(10).getDescription());
        Assertions.assertEquals(10, productFacade.getById(10).getPrice());
    }
    @Test
    public void deveConfirmarExistenciaDeProduto() {
        productFacade.append(produtoXBurguer);

        Assertions.assertTrue(productFacade.exists(10));
        Assertions.assertFalse(productFacade.exists(5));
    }
    @Test
    public void deveAdicionarNovoProdutoCorretamente() {
        productFacade.append(produtoXBurguer);

        Product produtoExistente = productFacade.getById(10);

        Assertions.assertNotNull(produtoExistente);
        Assertions.assertEquals("x-burguer", produtoExistente.getDescription());
        Assertions.assertEquals(10, produtoExistente.getPrice());
    }
    @Test
    public void deveRemoverProdutoExistente() {
        productFacade.append(produtoXBurguer);

        productFacade.remove(10);

        Assertions.assertFalse(productFacade.exists(10));
        Path path = Paths.get(produtoXBurguer.getImage());
        Assertions.assertFalse(Files.exists(path));
    }
}
