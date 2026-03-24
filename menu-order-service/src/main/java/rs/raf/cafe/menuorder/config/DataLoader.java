package rs.raf.cafe.menuorder.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.raf.cafe.menuorder.model.*;
import rs.raf.cafe.menuorder.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final MenuItemRepository menuItemRepository;
    private final IngredientRepository ingredientRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) {
        if (menuItemRepository.count() > 0) {
            return;
        }

        // Sastojci
        Ingredient mleko = ingredientRepository.save(Ingredient.builder()
                .name("Mleko").price(new BigDecimal("50.00")).available(true).build());
        Ingredient secer = ingredientRepository.save(Ingredient.builder()
                .name("Secer").price(new BigDecimal("10.00")).available(true).build());
        Ingredient cokolada = ingredientRepository.save(Ingredient.builder()
                .name("Cokolada").price(new BigDecimal("40.00")).available(true).build());
        Ingredient slag = ingredientRepository.save(Ingredient.builder()
                .name("Slag").price(new BigDecimal("30.00")).available(true).build());
        Ingredient vanila = ingredientRepository.save(Ingredient.builder()
                .name("Vanila").price(new BigDecimal("20.00")).available(true).build());
        Ingredient cimet = ingredientRepository.save(Ingredient.builder()
                .name("Cimet").price(new BigDecimal("15.00")).available(true).build());
        Ingredient med = ingredientRepository.save(Ingredient.builder()
                .name("Med").price(new BigDecimal("35.00")).available(true).build());
        Ingredient limun = ingredientRepository.save(Ingredient.builder()
                .name("Limun").price(new BigDecimal("20.00")).available(true).build());
        Ingredient nana = ingredientRepository.save(Ingredient.builder()
                .name("Nana").price(new BigDecimal("15.00")).available(true).build());
        Ingredient karamel = ingredientRepository.save(Ingredient.builder()
                .name("Karamel").price(new BigDecimal("30.00")).available(true).build());

        // Kafe
        MenuItem espreso = menuItemRepository.save(MenuItem.builder()
                .name("Espreso").description("Klasican italijanski espreso, jak i aromatican")
                .price(new BigDecimal("150.00")).type(MenuItemType.COFFEE).season(Season.ALL).available(true).build());
        MenuItem kapucino = menuItemRepository.save(MenuItem.builder()
                .name("Kapucino").description("Espreso sa penastim mlekom i blagim ukusom")
                .price(new BigDecimal("250.00")).type(MenuItemType.COFFEE).season(Season.ALL).available(true).build());
        MenuItem domacaKafa = menuItemRepository.save(MenuItem.builder()
                .name("Domaca kafa").description("Tradicionalna srpska kafa kuvana u dzezvi")
                .price(new BigDecimal("120.00")).type(MenuItemType.COFFEE).season(Season.ALL).available(true).build());
        MenuItem latte = menuItemRepository.save(MenuItem.builder()
                .name("Caffe Latte").description("Espreso sa dosta toplog mleka i tankim slojem pene")
                .price(new BigDecimal("280.00")).type(MenuItemType.COFFEE).season(Season.ALL).available(true).build());
        MenuItem irskiKapucino = menuItemRepository.save(MenuItem.builder()
                .name("Irski kapucino").description("Kapucino sa karamelom i slagom")
                .price(new BigDecimal("320.00")).type(MenuItemType.COFFEE).season(Season.WINTER).available(true).build());
        MenuItem icedLatte = menuItemRepository.save(MenuItem.builder()
                .name("Ledeni Latte").description("Hladni latte sa ledom, savrseno osvezenje za leto")
                .price(new BigDecimal("300.00")).type(MenuItemType.COFFEE).season(Season.SUMMER).available(true).build());

        // Cajevi
        MenuItem zeleniCaj = menuItemRepository.save(MenuItem.builder()
                .name("Zeleni caj").description("Prirodni zeleni caj bogat antioksidansima")
                .price(new BigDecimal("180.00")).type(MenuItemType.TEA).season(Season.ALL).available(true).build());
        MenuItem camomilla = menuItemRepository.save(MenuItem.builder()
                .name("Kamilica").description("Umirujuci caj od kamilice, idealan za vece")
                .price(new BigDecimal("150.00")).type(MenuItemType.TEA).season(Season.ALL).available(true).build());
        MenuItem planinski = menuItemRepository.save(MenuItem.builder()
                .name("Planinski caj").description("Mesavina planinskog bilja sa medom")
                .price(new BigDecimal("200.00")).type(MenuItemType.TEA).season(Season.WINTER).available(true).build());
        MenuItem ledeniCaj = menuItemRepository.save(MenuItem.builder()
                .name("Ledeni caj sa limunom").description("Osvezavajuci hladni caj sa svezim limunom i nanom")
                .price(new BigDecimal("220.00")).type(MenuItemType.TEA).season(Season.SUMMER).available(true).build());

        // Sokovi
        MenuItem cedjenaPorandza = menuItemRepository.save(MenuItem.builder()
                .name("Cedjena pomorandza").description("Sveze cedjeni sok od pomorandze")
                .price(new BigDecimal("280.00")).type(MenuItemType.JUICE).season(Season.ALL).available(true).build());
        MenuItem jabuka = menuItemRepository.save(MenuItem.builder()
                .name("Sok od jabuke").description("Prirodni sok od domace jabuke")
                .price(new BigDecimal("250.00")).type(MenuItemType.JUICE).season(Season.AUTUMN).available(true).build());
        MenuItem sargarepa = menuItemRepository.save(MenuItem.builder()
                .name("Sok od sargarepe").description("Sveze cedjena sargarepa sa djumbirom")
                .price(new BigDecimal("260.00")).type(MenuItemType.JUICE).season(Season.ALL).available(true).build());

        // Smutiji
        MenuItem bananaSmuzi = menuItemRepository.save(MenuItem.builder()
                .name("Banana smoothie").description("Kremasti smuzi od banane, mleka i meda")
                .price(new BigDecimal("350.00")).type(MenuItemType.SMOOTHIE).season(Season.ALL).available(true).build());
        MenuItem sumskiSmuzi = menuItemRepository.save(MenuItem.builder()
                .name("Sumsko voce smoothie").description("Smuzi od mesavine sumskog voca sa jogurtom")
                .price(new BigDecimal("380.00")).type(MenuItemType.SMOOTHIE).season(Season.SUMMER).available(true).build());

        // Peciva
        MenuItem kroasan = menuItemRepository.save(MenuItem.builder()
                .name("Kroasan sa cokoladom").description("Sveze peceni kroasan punjen belgijskom cokoladom")
                .price(new BigDecimal("200.00")).type(MenuItemType.PASTRY).season(Season.ALL).available(true).build());
        MenuItem burek = menuItemRepository.save(MenuItem.builder()
                .name("Burek sa sirom").description("Tradicionalni srpski burek sa domacem sirom")
                .price(new BigDecimal("250.00")).type(MenuItemType.PASTRY).season(Season.ALL).available(true).build());
        MenuItem kiflice = menuItemRepository.save(MenuItem.builder()
                .name("Kiflice sa maslacem").description("Tople kiflice sa svezim maslacem")
                .price(new BigDecimal("150.00")).type(MenuItemType.PASTRY).season(Season.ALL).available(true).build());

        // Sendvici
        MenuItem klubSendvic = menuItemRepository.save(MenuItem.builder()
                .name("Klub sendvic").description("Pilece belo meso, slanina, salata, paradajz, majonez")
                .price(new BigDecimal("450.00")).type(MenuItemType.SANDWICH).season(Season.ALL).available(true).build());
        MenuItem caprese = menuItemRepository.save(MenuItem.builder()
                .name("Caprese sendvic").description("Mocarela, paradajz, bosiljak, pesto sos")
                .price(new BigDecimal("400.00")).type(MenuItemType.SANDWICH).season(Season.SUMMER).available(true).build());
        MenuItem tunjevina = menuItemRepository.save(MenuItem.builder()
                .name("Sendvic sa tunjevinom").description("Tunjevina, kukuruz, krastavac, dresing")
                .price(new BigDecimal("380.00")).type(MenuItemType.SANDWICH).season(Season.ALL).available(true).build());

        // Deserti
        MenuItem cheesecake = menuItemRepository.save(MenuItem.builder()
                .name("Cheesecake").description("Kremasti cheesecake sa prelivom od sumskog voca")
                .price(new BigDecimal("350.00")).type(MenuItemType.DESSERT).season(Season.ALL).available(true).build());
        MenuItem palacinka = menuItemRepository.save(MenuItem.builder()
                .name("Palacinka sa nutellom").description("Domaca palacinka sa nutellom, bananom i slagom")
                .price(new BigDecimal("300.00")).type(MenuItemType.DESSERT).season(Season.ALL).available(true).build());
        MenuItem tulumba = menuItemRepository.save(MenuItem.builder()
                .name("Tulumbe").description("Tradicionalne tulumbe sa sirupom od secera")
                .price(new BigDecimal("220.00")).type(MenuItemType.DESSERT).season(Season.ALL).available(true).build());
        MenuItem tiramisu = menuItemRepository.save(MenuItem.builder()
                .name("Tiramisu").description("Klasicni italijanski desert sa mascarpone kremom i kafom")
                .price(new BigDecimal("380.00")).type(MenuItemType.DESSERT).season(Season.ALL).available(true).build());

        // Narudzbine
        // Narudzbina 1 - Ana Ivanovic
        Order order1 = orderRepository.save(Order.builder()
                .userEmail("ana.ivanovic@gmail.com")
                .status(OrderStatus.DELIVERED)
                .totalPrice(new BigDecimal("550.00"))
                .createdAt(LocalDateTime.now().minusDays(3))
                .build());
        orderItemRepository.save(OrderItem.builder()
                .order(order1).menuItem(kapucino).quantity(1)
                .customizationNotes("Bez secera").itemPrice(new BigDecimal("250.00")).build());
        orderItemRepository.save(OrderItem.builder()
                .order(order1).menuItem(palacinka).quantity(1)
                .customizationNotes("Dodatni slag").itemPrice(new BigDecimal("300.00")).build());

        // Narudzbina 2 - Stefan Markovic
        Order order2 = orderRepository.save(Order.builder()
                .userEmail("stefan.markovic@gmail.com")
                .status(OrderStatus.DELIVERED)
                .totalPrice(new BigDecimal("820.00"))
                .createdAt(LocalDateTime.now().minusDays(2))
                .build());
        orderItemRepository.save(OrderItem.builder()
                .order(order2).menuItem(domacaKafa).quantity(2)
                .customizationNotes("Sa secerom").itemPrice(new BigDecimal("240.00")).build());
        orderItemRepository.save(OrderItem.builder()
                .order(order2).menuItem(burek).quantity(1)
                .customizationNotes(null).itemPrice(new BigDecimal("250.00")).build());
        orderItemRepository.save(OrderItem.builder()
                .order(order2).menuItem(cheesecake).quantity(1)
                .customizationNotes(null).itemPrice(new BigDecimal("350.00")).build());

        // Narudzbina 3 - Milica Djordjevic
        Order order3 = orderRepository.save(Order.builder()
                .userEmail("milica.djordjevic@gmail.com")
                .status(OrderStatus.PREPARING)
                .totalPrice(new BigDecimal("730.00"))
                .createdAt(LocalDateTime.now().minusHours(1))
                .build());
        orderItemRepository.save(OrderItem.builder()
                .order(order3).menuItem(zeleniCaj).quantity(1)
                .customizationNotes("Sa medom").itemPrice(new BigDecimal("180.00")).build());
        orderItemRepository.save(OrderItem.builder()
                .order(order3).menuItem(kroasan).quantity(1)
                .customizationNotes(null).itemPrice(new BigDecimal("200.00")).build());
        orderItemRepository.save(OrderItem.builder()
                .order(order3).menuItem(tiramisu).quantity(1)
                .customizationNotes(null).itemPrice(new BigDecimal("380.00")).build());

        // Narudzbina 4 - Lazar Nikolic
        Order order4 = orderRepository.save(Order.builder()
                .userEmail("lazar.nikolic@gmail.com")
                .status(OrderStatus.PENDING)
                .totalPrice(new BigDecimal("1080.00"))
                .createdAt(LocalDateTime.now().minusMinutes(15))
                .build());
        orderItemRepository.save(OrderItem.builder()
                .order(order4).menuItem(klubSendvic).quantity(1)
                .customizationNotes("Bez majoneza").itemPrice(new BigDecimal("450.00")).build());
        orderItemRepository.save(OrderItem.builder()
                .order(order4).menuItem(cedjenaPorandza).quantity(1)
                .customizationNotes(null).itemPrice(new BigDecimal("280.00")).build());
        orderItemRepository.save(OrderItem.builder()
                .order(order4).menuItem(tiramisu).quantity(1)
                .customizationNotes(null).itemPrice(new BigDecimal("380.00")).build());

        // Narudzbina 5 - Jovana Stojanovic
        Order order5 = orderRepository.save(Order.builder()
                .userEmail("jovana.stojanovic@gmail.com")
                .status(OrderStatus.READY)
                .totalPrice(new BigDecimal("630.00"))
                .createdAt(LocalDateTime.now().minusMinutes(30))
                .build());
        orderItemRepository.save(OrderItem.builder()
                .order(order5).menuItem(latte).quantity(1)
                .customizationNotes("Sa vanilom").itemPrice(new BigDecimal("280.00")).build());
        orderItemRepository.save(OrderItem.builder()
                .order(order5).menuItem(tulumba).quantity(1)
                .customizationNotes(null).itemPrice(new BigDecimal("220.00")).build());
        orderItemRepository.save(OrderItem.builder()
                .order(order5).menuItem(kiflice).quantity(1)
                .customizationNotes("Tople").itemPrice(new BigDecimal("150.00")).build());

        // Narudzbina 6 - Ana Ivanovic (ponovna)
        Order order6 = orderRepository.save(Order.builder()
                .userEmail("ana.ivanovic@gmail.com")
                .status(OrderStatus.CANCELLED)
                .totalPrice(new BigDecimal("500.00"))
                .createdAt(LocalDateTime.now().minusDays(1))
                .build());
        orderItemRepository.save(OrderItem.builder()
                .order(order6).menuItem(espreso).quantity(2)
                .customizationNotes(null).itemPrice(new BigDecimal("300.00")).build());
        orderItemRepository.save(OrderItem.builder()
                .order(order6).menuItem(kroasan).quantity(1)
                .customizationNotes(null).itemPrice(new BigDecimal("200.00")).build());
    }
}
