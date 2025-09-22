import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import praktikum.*;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static praktikum.IngredientType.*;

@RunWith(Parameterized.class)
public class BurgerTest {

    @Mock
    Bun bun;

    @Mock
    Ingredient sauce;

    @Mock
    Ingredient filling;

    Burger burger;
    private final String bunName;
    private final float bunPrice;
    private final String sauceName;
    private final float saucePrice;
    private final String fillingName;
    private final float fillingPrice;
    private final float priceSum;

    public BurgerTest(String bunName, float bunPrice, String sauceName, float saucePrice, String fillingName, float fillingPrice, float priceSum) {
        this.bunName = bunName;
        this.bunPrice = bunPrice;
        this.sauceName = sauceName;
        this.saucePrice = saucePrice;
        this.fillingName = fillingName;
        this.fillingPrice = fillingPrice;
        this.priceSum = priceSum;
    }

    @Before
    public void setUp() {
        burger = new Burger();
        MockitoAnnotations.initMocks(this);
        Mockito.when(bun.getName()).thenReturn(bunName);
        Mockito.when(bun.getPrice()).thenReturn(bunPrice);
        Mockito.when(sauce.getName()).thenReturn(sauceName);
        Mockito.when(sauce.getType()).thenReturn(SAUCE);
        Mockito.when(sauce.getPrice()).thenReturn(saucePrice);
        Mockito.when(filling.getName()).thenReturn(fillingName);
        Mockito.when(filling.getType()).thenReturn(FILLING);
        Mockito.when(filling.getPrice()).thenReturn(fillingPrice);
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1}")
    public static Collection<Object[]> getPrice() {
        return Arrays.asList(new Object[][]{
                {"Black Bun", 100f, "Hot Sauce", 100f, "Cutlet", 100f, 400f},
                {"White Bun", 200f, "Sour Cream", 200f, "Dinosaur", 200f, 800f}
        });
    }

    @Test
    public void setBunsTest() {
        burger.setBuns(bun);
        assertEquals(bun, burger.bun);
    }

    @Test
    public void addIngredientTest() {
        burger.addIngredient(sauce);
        assertTrue(burger.ingredients.contains(sauce));
    }

    @Test
    public void removeIngredientTest() {
        burger.addIngredient(filling);
        burger.removeIngredient(0);
        assertFalse(burger.ingredients.contains(filling));
    }

    @Test
    public void moveIngredientTest() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.moveIngredient(0, 1);
        assertEquals(filling, burger.ingredients.get(0));
    }

    @Test
    public void getPriceTest() {
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        float expectedPrice = burger.getPrice();
        assertEquals(expectedPrice, priceSum, 1);
    }

    @Test
    public void getReceiptTest() {
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