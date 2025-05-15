package com.snack.repositories;

import java.util.List;
import java.util.NoSuchElementException;

import com.snack.entities.Product;
import com.snack.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductRepositoryTest {

    ProductRepository productRepository;
    Product produtoXBurguer;
    Product produtoXSalada;


    @BeforeEach
    void setUp(){
        productRepository = new ProductRepository();
        produtoXBurguer = new Product(1, "x-burguer", 10, "src\\test\\java\\com\\snack\\imagens\\X-burguer.jpeg");
        produtoXSalada = new Product(2, "x-salada", 15, "src\\test\\java\\com\\snack\\imagens\\x-salada.jpeg");

    }
    @Test
    public void deveAdicionarProdutoAoRepositorio(){
        productRepository.append(produtoXBurguer);

        Assertions.assertTrue(productRepository.exists(1));
        Assertions.assertEquals(1, productRepository.getAll().size());
        Assertions.assertEquals(produtoXBurguer, productRepository.getById(1));
    }

    @Test
    void deveRecuperarProdutoPorIdQuandoExistir() {
        productRepository.append(produtoXBurguer);

        Product produtoRecuperado = productRepository.getById(1);


        Assertions.assertNotNull(produtoRecuperado);
           Assertions.assertEquals(produtoXBurguer, produtoRecuperado);
    }

    @Test
    void deveConfirmarExistenciaDeProdutoNoRepositorio() {
        productRepository.append(produtoXBurguer);

        Assertions.assertTrue(productRepository.exists(1));
    }
    @Test
    void deveRemoverProdutoDoRepositorio() {
        productRepository.append(produtoXBurguer);

        Assertions.assertTrue(productRepository.exists(1));

        productRepository.remove(1);

        Assertions.assertFalse(productRepository.exists(1));
        Assertions.assertEquals(0, productRepository.getAll().size());


    }
    @Test
    void deveAtualizarProdutoExistente() {

        Product produtoAtualizado = new Product(1, "x-burguer especial", 12, "nova_imagem.jpg");
        productRepository.append(produtoXBurguer);
        productRepository.update(1, produtoAtualizado);


        Product produtoRecuperado = productRepository.getById(1);

        Assertions.assertEquals(produtoAtualizado.getDescription(), produtoRecuperado.getDescription());
        Assertions.assertEquals(produtoAtualizado.getPrice(), produtoRecuperado.getPrice());
        Assertions.assertEquals(produtoAtualizado.getImage(), produtoRecuperado.getImage());
    }

    @Test
    void deveRecuperarTodosOsProdutosDoRepositorio() {
        productRepository.append(produtoXBurguer);
        productRepository.append(produtoXSalada);
        List<Product> produtosRecuperados = productRepository.getAll();

        Assertions.assertNotNull(produtosRecuperados);
        Assertions.assertEquals(2, produtosRecuperados.size());
        Assertions.assertTrue(produtosRecuperados.contains(produtoXBurguer));
        Assertions.assertTrue(produtosRecuperados.contains(produtoXSalada));
    }

    @Test
    void deveManterRepositorioIntactoAoRemoverProdutoInexistente() {
        productRepository.append(produtoXBurguer);
        productRepository.append(produtoXSalada);

        productRepository.remove(999);

        Assertions.assertEquals(2, productRepository.getAll().size());
    }

    @Test
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {

        Product produtoNaoExistente = new Product(999, "Produto Fantasma", 50, "imagem.jpg");


        Assertions.assertThrows(NoSuchElementException.class, () -> {
            productRepository.update(999, produtoNaoExistente);
        });
    }

    @Test
    void deveRejeitarAdicaoDeProdutosComIdsDuplicados() {

        Product produto1 = new Product(1, "x-burguer", 10, "imagem1.jpg");
        Product produtoDuplicado = new Product(1, "x-salada", 15, "imagem2.jpg");


        productRepository.append(produto1);


        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productRepository.append(produtoDuplicado);
        });


        Assertions.assertEquals(1, productRepository.getAll().size());
    }
    @Test
    void repositorioDeveEstarVazioAoInicializar() {

        List<?> produtosRecuperados = productRepository.getAll();


        Assertions.assertNotNull(produtosRecuperados);
        Assertions.assertTrue(produtosRecuperados.isEmpty());
    }
}
