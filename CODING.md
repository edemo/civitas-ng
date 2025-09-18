Yes, the code layout is strange. This is because taking SOLID principles and testability seriously results in this.

The rules:

- Clean code and TDD. The whole point of the code layout is to support that.

There are the following categories:

- Constants
- Entities (data structures)
- Services (executable code)
- Tests 
- Stubs
- Test Data
- Delegate
- Glue and boilerplate

Constants:

- They are interfaces, containing only constants.

Entities:

- Have @Data annotation
- The class is annotated with @Data (from Lombok)
- The class only contains fields. Nothing more. Seriously.
- Fields are annotated with @NonNull
- Each entity has its own package

Services:

- Have @Service Annotation
- The class contains one single functionality. Ideally a small one, not more than 50 LOC.
- There is one method, doing one thing. There are possibly other methods doing the same things with slightly different arguments.
- Every method is called apply, as you have already told what they do in the class name.
- There is a field for each of the called services annotated with @Autowire, and they are called through that. 
- No constants directly in the code except 0, 1, null and "".
- No interfaces defining the functionality (Test cases are used for that), and no need to use getters/setters for entities.
- Services are in the same package than the entity they are most closely relate to (which class would you 


Tests:

- There is one junit5 test class for each service. Their name starts with the service name and end with "Test".
- Each test case have a @DisplayName annotation, describing the test case in a way, which makes sense to the end user when extracted
  to a bullet point in the documentation about the service tested.
- The tests class extends TestBase (which is responsible for inserting services and stubs through DI).
- The test class implements the TestData corresponding to the entity corresponding to the service tested.
- Unit tests have a field for the service tested with @Tested annotation 
- Functional tests have @Tag("functional") in the annotation, and the field uses the @Use annotation
  these tests ideally have an identical pair of unit test and probably will be included in the code due to FTP_TST and FTP_TEE.
- Some testdata (mostly hashes) are too complex to derive in the TestData. They have their own test cases with @Tag("testdata")

Stubs:

- Their name starts with the service name and ends with "Stub".
- They contain one public static T stub(); function, where T is the service class.
- This function returns the stub. 

Test Data:

- Their name is the name of the corresponding entity, ending with "TestData".
- They are interfaces, containing constants. In many cases the building up of these constants is an important part of test cases.
  For example most of the crypto function tests' @DisplayName details the cryptographic algorithm used, and the TestData is the place where the fact that the test actually tests the description can be checked.
- They extend whatever other test data they need to build the constants.

Delegate:

- There are too few of them at the current stage. They are meant to look like either an old style class or a factory for the layers above the code given.
- They should not be used in the same layer of code where they are defined.
- They extend zero or one entity, and all of the normal methods are just delegates to services.
  See https://github.com/projectlombok/lombok/issues/3893 for how they are meant to be look like in the long run.
- The ones extending an entity have a method called of() with the same arguments of the all fields constructor, calling that and DI.fill(this)

Glue and boilerplate:

These should be kept at the minimum, and should not contain any code which is relevant to the functionality of the software 
  (can be called business logic in any sense)
The class is marked with @Boilerplate

Formatting:

Code style is enforced automatically using [palantir-java-format](https://github.com/palantir/palantir-java-format), integrated via [Spotless](https://github.com/diffplug/spotless).  
Additionally, some best practices are checked and applied through Spotless' [Cleanthat](https://github.com/solven-eu/cleanthat) plugin.

- Formatting is automatically verified during the GitHub pipeline.  
- The build will fail if the code is not formatted or violates enforced best practices.
- You can run it manually any time with:

  ```bash
  mvn spotless:apply
