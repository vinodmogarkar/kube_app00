package com.home.eshopee;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.home.eshopee.common.events.DomainEvent;

import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.NESTED_CLASSES;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.assignableTo;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideOutsideOfPackages;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class CleanModulesArchTest {

    @Test
    void sales_catalog_service_has_no_dependency_on_others() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.sales.catalog");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.home.eshopee.."
                ).or(resideInAPackage("com.home.eshopee.sales.catalog.."
                ).or(resideInAPackage("com.home.eshopee.common.."))));
        rule.check(importedClasses);
    }

    @Test
    void sales_catalog_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.sales.catalog");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.home.eshopee.sales.catalog.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.home.eshopee.sales.catalog.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void sales_order_service_has_no_dependencies_on_others_except_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.sales.order");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.home.eshopee.."
                ).or(resideInAPackage("com.home.eshopee.sales.order.."
                ).or(resideInAPackage("com.home.eshopee.common..")
                ).or(assignableTo(DomainEvent.class).or(NESTED_CLASSES))));
        rule.check(importedClasses);
    }

    @Test
    void sales_order_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.sales.order");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.home.eshopee.sales.order.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.home.eshopee.sales.order.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void sales_cart_service_has_no_dependency_on_others() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.sales.cart");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.home.eshopee.."
                ).or(resideInAPackage("com.home.eshopee.sales.cart.."
                ).or(resideInAPackage("com.home.eshopee.common.."))));
        rule.check(importedClasses);
    }

    @Test
    void billing_payment_service_has_no_dependencies_on_others_except_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.billing.payment");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.home.eshopee.."
                ).or(resideInAPackage("com.home.eshopee.billing.payment.."
                ).or(resideInAPackage("com.home.eshopee.common..")
                ).or(assignableTo(DomainEvent.class).or(NESTED_CLASSES))));
        rule.check(importedClasses);
    }

    @Test
    void billing_payment_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.billing.payment");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.home.eshopee.billing.payment.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.home.eshopee.billing.payment.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void shipping_delivery_service_has_no_dependencies_on_others_except_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.shipping.delivery");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.home.eshopee.."
                ).or(resideInAPackage("com.home.eshopee.shipping.delivery.."
                ).or(resideInAPackage("com.home.eshopee.common..")
                ).or(assignableTo(DomainEvent.class).or(NESTED_CLASSES))));
        rule.check(importedClasses);
    }

    @Test
    void shipping_delivery_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.shipping.delivery");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.home.eshopee.shipping.delivery.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.home.eshopee.shipping.delivery.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void shipping_dispatching_service_has_no_dependencies_on_others_except_delivery_and_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.shipping.dispatching");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.home.eshopee.."
                ).or(resideInAPackage("com.home.eshopee.shipping.dispatching.."
                ).or(resideInAPackage("com.home.eshopee.shipping.delivery..")
                ).or(resideInAPackage("com.home.eshopee.common..")
                ).or(assignableTo(DomainEvent.class).or(NESTED_CLASSES))));
        rule.check(importedClasses);
    }

    @Test
    void shipping_dispatching_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.shipping.dispatching");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.home.eshopee.shipping.dispatching.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.home.eshopee.shipping.dispatching.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void warehouse_service_has_no_dependencies_on_others_except_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.warehouse");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.home.eshopee.."
                ).or(resideInAPackage("com.home.eshopee.warehouse.."
                ).or(resideInAPackage("com.home.eshopee.common..")
                ).or(assignableTo(DomainEvent.class).or(NESTED_CLASSES))));
        rule.check(importedClasses);
    }

    @Test
    void warehouse_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.warehouse");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.home.eshopee.warehouse.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.home.eshopee.warehouse.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void catalog_service_has_no_dependencies_on_billing() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.home.eshopee.portal");
        ArchRule rule = classes()
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.home.eshopee.billing..");
        rule.check(importedClasses);
    }

    @Test
    void portal_web_uses_only_its_own_use_cases_and_no_direct_dependencies_on_other_services() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.home.eshopee.sales",
                "com.home.eshopee.warehouse",
                "com.home.eshopee.shipping",
                "com.home.eshopee.billing");
        ArchRule rule = classes()
                .should().onlyHaveDependentClassesThat().resideOutsideOfPackage(
                        "com.home.eshopee.portal.web..");
        rule.check(importedClasses);
    }
}
