package org.example;

import org.example.models.Category;
import org.example.models.Product;
import org.example.models.ProductImage;
import org.example.utils.FileUtils;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("==== MENU ====");
            System.out.println("1. Add Category");
            System.out.println("2. Edit Category");
            System.out.println("3. Delete Category");
            System.out.println("4. List Categories");
            System.out.println("5. Add Product");
            System.out.println("6. Add Product with image");
            System.out.println("7. Edit Product");
            System.out.println("8. Delete Product");
            System.out.println("9. List Products");
            System.out.println("0. Exit");
            System.out.println("==============");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCategory();
                    break;
                case 2:
                    editCategory();
                    break;
                case 3:
                    deleteCategory();
                    break;
                case 4:
                    getListGategories();
                    break;
                case 5:
                    addProduct();
                    break;
                case 6:
                    addProductWithImage();
                    break;
                case 7:
                    editProduct();
                    break;
                case 8:
                    deleteProduct();
                    break;
                case 9:
                    getListProducts();
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void addProductWithImage() {
        Scanner scanner = new Scanner(System.in);
        var sf = HibernateUtil.getSessionFactory();

        try (Session context = sf.openSession()) {
            context.beginTransaction();
            Product product = new Product();

            System.out.println("Enter product name: ");
            String temp = scanner.nextLine();
            product.setName(temp);

            System.out.println("Enter description: ");
            temp = scanner.nextLine();
            product.setDescription(temp);

            System.out.println("Enter price: ");
            product.setPrice(scanner.nextDouble());

            Category category = new Category();
            System.out.println("Enter category id: ");
            category.setId(scanner.nextInt());

            product.setCategory(category);

            System.out.println("Enter path to the image file: ");
            String imagePath = scanner.next();


            String uniqueFileName = UUID.randomUUID().toString() + ".jpg";
            String destinationPath = "images/" + uniqueFileName;

            FileUtils.copyFile(imagePath, destinationPath);

            ProductImage productImage = new ProductImage();
            productImage.setImage(uniqueFileName);
            product.setProductImage(productImage);

            context.save(product);
            context.getTransaction().commit();
            System.out.println("Product added successfully with image!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void getListGategories() {
        var sf = HibernateUtil.getSessionFactory();
        try(Session context = sf.openSession()) {
            context.beginTransaction();
            List<Category> list = context.createQuery("from Category", Category.class).getResultList();

            for (Category category : list) {
                System.out.println("Category: " + category);
            }

            context.getTransaction().commit();
        }
    }

    private static void addCategory() {
        Scanner scanner = new Scanner(System.in);
        Calendar calendar = Calendar.getInstance();
        var sf = HibernateUtil.getSessionFactory();
        try (Session context = sf.openSession()) {
            context.beginTransaction();
            Category category = new Category();
            System.out.println("Enter category name: ");
            String temp = scanner.nextLine();
            category.setName(temp);
            System.out.println("Enter image: ");
            temp = scanner.nextLine();
            category.setImage(temp);
            category.setCateCreated(calendar.getTime());
            context.save(category);
            context.getTransaction().commit();
        }
    }

    private static void editCategory() {
        Scanner scanner = new Scanner(System.in);
        Calendar calendar = Calendar.getInstance();
        var sf = HibernateUtil.getSessionFactory();
        try (Session context = sf.openSession()) {
            context.beginTransaction();
            System.out.println("Enter category id to edit: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Category categoryToEdit = context.get(Category.class, id);
            if(categoryToEdit != null) {
                System.out.println("Enter new category name: ");
                String newName = scanner.nextLine();
                categoryToEdit.setName(newName);
                System.out.println("Enter new category image: ");
                String newImage = scanner.nextLine();
                categoryToEdit.setImage(newImage);
                categoryToEdit.setCateCreated(calendar.getTime());
                context.update(categoryToEdit);
                System.out.println("Category " + id + " updated successfully");
            } else {
                System.out.println("Category with id: " + id + " not found");
            }
            context.getTransaction().commit();
        }
     }

    private static void deleteCategory() {
        Scanner scanner = new Scanner(System.in);
        var sf = HibernateUtil.getSessionFactory();

        try (Session context = sf.openSession()) {
            context.beginTransaction();
            System.out.println("Enter category id to delete: ");
            int id = scanner.nextInt();
            Category categoryToDelete = context.get(Category.class, id);
            if(categoryToDelete != null) {
                context.delete(categoryToDelete);
                System.out.println("Category " + id + " deleted successfully");
            } else {
                System.out.println("Category with id: " + id + " not found");
            }

            context.getTransaction().commit();
        }
    }

    private static void addProduct() {
        Scanner scanner = new Scanner(System.in);
        var sf = HibernateUtil.getSessionFactory();
        try(Session context = sf.openSession()) {
            context.beginTransaction();
            Product product = new Product();

            System.out.println("Enter product name: ");
            String temp = scanner.nextLine();
            product.setName(temp);

            System.out.println("Enter description: ");
            temp = scanner.nextLine();
            product.setDescription(temp);

            System.out.println("Enter price: ");
            product.setPrice(scanner.nextDouble());

            Category category = new Category();
            System.out.println("Enter category id: ");
            category.setId(scanner.nextInt());

            product.setCategory(category);
            context.save(product);
            context.getTransaction().commit();
        }
    }

    private static void getListProducts() {
        var sf = HibernateUtil.getSessionFactory();
        try(Session context = sf.openSession()) {
            context.beginTransaction();
            List<Product> list = context.createQuery("from Product ", Product.class).getResultList();

            for (Product product : list) {
                System.out.println("Product: " + product);
            }

            context.getTransaction().commit();
        }
    }

    private static void editProduct() {
        Scanner scanner = new Scanner(System.in);
        var sf = HibernateUtil.getSessionFactory();
        try (Session context = sf.openSession()) {
            context.beginTransaction();
            System.out.println("Enter product id to edit: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Product productToEdit = context.get(Product.class, id);
            if(productToEdit != null) {
                System.out.println("Enter new product name: ");
                String newName = scanner.nextLine();
                productToEdit.setName(newName);
                System.out.println("Enter new product description: ");
                String newDesc = scanner.nextLine();
                productToEdit.setDescription(newDesc);
                System.out.println("Enter new product price: ");
                int newPrice = scanner.nextInt();
                productToEdit.setPrice(newPrice);

                context.update(productToEdit);
                System.out.println("Product " + id + " updated successfully");
            }
            else {
                System.out.println("Product with id: " + id + " not found");
            }
            context.getTransaction().commit();
        }
    }

    private static void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        var sf = HibernateUtil.getSessionFactory();

        try (Session context = sf.openSession()) {
            context.beginTransaction();
            System.out.println("Enter product id to delete: ");
            int id = scanner.nextInt();
            Product productToDelete = context.get(Product.class, id);
            if(productToDelete != null) {
                context.delete(productToDelete);
                System.out.println("Product " + id + " deleted successfully");
            } else {
                System.out.println("Product with id: " + id + " not found");
            }

            context.getTransaction().commit();
        }
    }
}