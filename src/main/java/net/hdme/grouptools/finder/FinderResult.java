package net.hdme.grouptools.finder;

import net.hdme.grouptools.base.BinaryOperation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FinderResult {

    public final Set<BinaryOperation> tables;

    public FinderResult(Collection<BinaryOperation> tables) {
        this.tables = new HashSet<>(tables);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FinderResult result) {
            return tables.equals(result.tables);
        }
        return false;
    }

    @Override
    public String toString() {
        return tables.toString();
    }

}
