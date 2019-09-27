package com.wcyv90.x.tcc.tx.core.recovery;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class RecoveryJobConfigurationSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {RecoveryJobConfiguration.class.getName()};
    }

}
