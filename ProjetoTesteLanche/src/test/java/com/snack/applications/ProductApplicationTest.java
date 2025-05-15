package com.snack.applications;

import java.io.File;
import java.util.List;
import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;



class ProductApplicationTest {
    ProductApplication productApplication;
    Product produtoXBurguer;
    Product produtoXSalada;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService();

        produtoXBurguer = new Product(2, "x-burguer", 10, "src\\test\\java\\com\\snack\\imagens\\x-burguer.jpg");
        produtoXSalada = new Product(1, "x-salada", 15, "src\\test\\java\\com\\snack\\imagens\\x-salada.jpg");
        productApplication = new ProductApplication(productRepository, productService);
    }


    @Test
    public void deveListarTodosOsProdutos() {
        productApplication.append(produtoXBurguer);
        productApplication.append(produtoXSalada);

        List<Product> produtos = productApplication.getAll();

        Assertions.assertEquals(2, produtos.size());

    }

    @Test
    public void deveObterProdutoPorIdValido() {
        productApplication.append(produtoXBurguer);

        Product productPurchased = productApplication.getById(2);

        Assertions.assertEquals(2, productPurchased.getId());
        Assertions.assertEquals("x-burguer", productPurchased.getDescription());
        Assertions.assertEquals(10, productPurchased.getPrice());
    }

    @Test
    public void deveLancarExcecaoAoObterProdutoPorIdInvalido() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            productApplication.getById(5);
        });
    }

    @Test
    public void deveConfirmarExistenciaDeProdutoPorIdValido() {
        productApplication.append(produtoXSalada);

        Assertions.assertTrue(productApplication.exists(1));
    }

    @Test
    public void deveRetornarFalsoAoVerificarProdutoComIdInvalido() {

        Assertions.assertFalse(productApplication.exists(3));
    }
    @Test
    public void deveAdicionarProdutoESalvarImagem() {
        productApplication.append(produtoXSalada);


        Assertions.assertTrue(new File(produtoXSalada.getImage()).exists());
    }

    @Test
    public void deveRemoverProdutoEDeletarImagem() {
        productApplication.append(produtoXSalada);

        productApplication.remove(1);
        Path path = Paths.get(produtoXSalada.getImage());

        Assertions.assertFalse(productApplication.exists(1));
        Assertions.assertFalse(Files.exists(path));
    }

    @Test
    public void deveLancarExcecaoAoRemoverProdutoComIdInexistente() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            productApplication.remove(5);
        });
    }
    @Test
    public void deveAtualizarProdutoESubstituirImagem() {
        productApplication.append(produtoXSalada);
        String primeiraImagem = produtoXSalada.getImage();

        productApplication.update(1, produtoXBurguer);

        Assertions.assertEquals("x-burguer", productApplication.getById(1).getDescription());
        Assertions.assertEquals(10, productApplication.getById(1).getPrice());

        Path pathChiken = Paths.get(primeiraImagem);
        Assertions.assertTrue(Files.exists(pathChiken));

        Path pathFish = Paths.get(produtoXBurguer.getImage());
        Assertions.assertTrue(Files.exists(pathFish));
    }

}