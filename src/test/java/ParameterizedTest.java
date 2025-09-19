import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static praktikum.IngredientType.*;

@RunWith(Parameterized.class)
public class ParameterizedTest {

    private final Bun bun;
    private final Ingredient sauce;
    private final Ingredient filling;
    private final float price;

    public ParameterizedTest(Bun bun, Ingredient sauce, Ingredient filling, float price) {
        this.bun = bun;
        this.sauce = sauce;
        this.filling = filling;
        this.price = price;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1}")
    public static Collection<Object[]> getPrice() {
        return Arrays.asList(new Object[][]{
                {new Bun("black bun", 100f), new Ingredient(SAUCE,"hot sauce", 100f), new Ingredient(FILLING, "cutlet", 100f), 400},
                {new Bun("white bun", 200f), new Ingredient(SAUCE,"sour cream", 200f), new Ingredient(FILLING, "dinosaur", 200f), 800},
        });
    }

    @Test
    public void getPriceTest() {
        Burger burger = new Burger();
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        float actualPrice = burger.getPrice();
        assertEquals(price, actualPrice, 1);
    }

    @Test
    public void getReceiptTest() {
        Burger burger = new Burger();
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        String actualReceipt = burger.getReceipt();
        String expectedReceipt = "(==== " + bun.getName() + " ====)\r\n" +
                "= " + sauce.getType().toString().toLowerCase() + " " + sauce.getName() + " =\r\n" +
                "= " + filling.getType().toString().toLowerCase() + " " + filling.getName() + " =\r\n" +
                "(==== " + bun.getName() + " ====)\r\n" + "\r\n" + "Price: " + String.format("%f%n", burger.getPrice());
        assertEquals(expectedReceipt, actualReceipt);
    }
}
