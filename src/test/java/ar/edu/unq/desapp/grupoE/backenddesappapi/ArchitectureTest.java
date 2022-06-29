package ar.edu.unq.desapp.grupoE.backenddesappapi;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchitectureTest {

    public static final String PATH = "ar.edu.unq.desapp.grupoE.backenddesappapi";

    @Test
    public void layerChecksAccess() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(PATH);

        ArchRule rule = layeredArchitecture()
                .layer("Controller").definedBy("..webservice..")
                .layer("Service").definedBy("..service..")
                .layer("Persistence").definedBy("..persistence..")
                .layer("Security").definedBy("..security..")
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service", "Security");

        rule.check(importedClasses);
    }

    @Test
    public void classesAreAnnotatedWithServiceShouldBeAnnotateWithTransactional() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(PATH);

        ArchRule rule = classes().that().areAnnotatedWith(Service.class)
                .should().beAnnotatedWith(Transactional.class);

        rule.check(importedClasses);
    }

    @Test
    public void classesAreAnnotatedWithTransactionalShouldBeAnnotateWithService() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(PATH);

        ArchRule rule = classes().that().areAnnotatedWith(Transactional.class)
                .should().beAnnotatedWith(Service.class);

        rule.check(importedClasses);
    }

    @Test
    public void classesInPackagesDTOHaveSimpleNameEndingWithDTO () {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(PATH + ".webservice.DTO");

        ArchRule rule = classes()
                .should().haveSimpleNameEndingWith("DTO");

        rule.check(importedClasses);
    }
}
