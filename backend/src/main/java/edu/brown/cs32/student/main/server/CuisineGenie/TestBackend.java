
// Success Responses

@BeforeEach
public void setup() {
    // Server server = new Server();
    List<List<String>> csv = new ArrayList<>();
    Spark.get("generaterecipes", new Generator());
    Spark.init();
    Spark.awaitInitialization();
}

@Test
public void AllSameCategory() {

}

@Test
public void DifferentCategories() {

}

@Test
public void OneCommonCategory() {

}

@Test
public void TieCategories() {

}

@Test
public void AllSameAreas() {

}

@Test
public void DifferentAreas() {

}


// Failure Responses