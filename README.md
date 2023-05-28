# The new support for testcontainers in Spring Boot 3.1 does not work with native tests

1. Works fine: 

      ```
      # Running tests that use testcontainers for Postgresql
      ./gradlew clean
      ./gradlew test

      # Build jar file and native image and run them with a Postregsql db in Docker 
      docker-compose up -d
      export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost/bp
      export SPRING_DATASOURCE_USERNAME=bp
      export SPRING_DATASOURCE_PASSWORD=bp
   
     ./gradlew build
      java -jar build/libs/tcdemo-0.0.1-SNAPSHOT.jar
      curl localhost:8080/customers

     ./gradlew nativeBuild
      java -jar build/libs/tcdemo-0.0.1-SNAPSHOT.jar
      curl localhost:8080/customers

      docker-compose down
      ```
      
2. Does not work:

    ```
      # Running native tests that use testcontainers for Postgresql
    ./gradlew clean nativeTest
    ```
    
    Results in the error:
    
    ```
    Failures (1):
      JUnit Jupiter:TcdemoApplicationTests
        ClassSource [className = 'com.example.tcdemo.TcdemoApplicationTests', filePosition = null]
        => java.lang.ExceptionInInitializerError
           org.springframework.test.context.TestContextManager.<init>(TestContextManager.java:113)
           org.junit.jupiter.engine.execution.ExtensionValuesStore.lambda$getOrComputeIfAbsent$4(ExtensionValuesStore.java:86)
           org.junit.jupiter.engine.execution.ExtensionValuesStore$MemoizingSupplier.computeValue(ExtensionValuesStore.java:223)
           org.junit.jupiter.engine.execution.ExtensionValuesStore$MemoizingSupplier.get(ExtensionValuesStore.java:211)
           org.junit.jupiter.engine.execution.ExtensionValuesStore$StoredValue.evaluate(ExtensionValuesStore.java:191)
           [...]
           Suppressed: java.lang.NoClassDefFoundError: Could not initialize class org.springframework.test.context.BootstrapUtils
             org.springframework.test.context.TestContextManager.<init>(TestContextManager.java:113)
             org.junit.jupiter.engine.execution.ExtensionValuesStore.lambda$getOrComputeIfAbsent$4(ExtensionValuesStore.java:86)
             org.junit.jupiter.engine.execution.ExtensionValuesStore$MemoizingSupplier.computeValue(ExtensionValuesStore.java:223)
             org.junit.jupiter.engine.execution.ExtensionValuesStore$MemoizingSupplier.get(ExtensionValuesStore.java:211)
             org.junit.jupiter.engine.execution.ExtensionValuesStore$StoredValue.evaluate(ExtensionValuesStore.java:191)
             [...]
         Caused by: java.lang.IllegalStateException: Failed to load class for @org.springframework.test.context.web.WebAppConfiguration
           org.springframework.test.context.BootstrapUtils.loadWebAppConfigurationClass(BootstrapUtils.java:213)
           org.springframework.test.context.BootstrapUtils.<clinit>(BootstrapUtils.java:63)
           [...]
         Caused by: java.lang.ClassNotFoundException: org.springframework.test.context.web.WebAppConfiguration
           java.base@17.0.6/java.lang.Class.forName(DynamicHub.java:1132)
           org.springframework.util.ClassUtils.forName(ClassUtils.java:284)
           org.springframework.test.context.BootstrapUtils.loadWebAppConfigurationClass(BootstrapUtils.java:209)
           [...]
    ```
